package com.apps.alemjc.prioritytodo

import android.app.DialogFragment
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.apps.alemjc.prioritytodo.Storage.SQLTodoStorage
import com.apps.alemjc.prioritytodo.content.Todo

/**
 * A fragment representing a list of Items.
 *
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class TodosFragment : Fragment(), CreateDialogFragment.OnDialogInteractionListener {
    private var fragmentType = PENDING_FRAGMENT_TYPE
    private var mListener: MyListInteractionListener? = MyListInteractionListener()
    private var todos: ArrayList<Todo> = ArrayList()
    private val storage:SQLTodoStorage = SQLTodoStorage.getInstance(context)
    private var recycler:RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (arguments != null) {
            fragmentType = arguments.getInt(ARG_FRAGMENT_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_item_list, container, false)
        val addButton = view.findViewById<Button>(R.id.add)
        recycler = view.findViewById(R.id.list)

        val context = view.context

        recycler!!.layoutManager = LinearLayoutManager(context)
        recycler!!.adapter = MyItemRecyclerViewAdapter(getContext(), todos, mListener as OnListInteractionListener)

        addButton.setOnClickListener { addTodo() }

        return view
    }

    override fun onResume() {
        super.onResume()

        todos = getTodos()
    }


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onItemCreated(item: Todo) {
        val returnVal = storage.create(item.description, item.priority)

        println("this is the return value $returnVal")
    }

    override fun onCancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addTodo() {
        val dialog: DialogFragment = CreateDialogFragment() as DialogFragment

        dialog.show(activity.fragmentManager, "addDialog")
    }

    fun getTodos(): ArrayList<Todo> {

        when(fragmentType){
            PENDING_FRAGMENT_TYPE->{
                val returnList:ArrayList<Todo> = storage.getPendingTasks()

                println("all pending todos: $returnList")

                return returnList
            }
            DONE_FRAGMENT_TYPE->{
                val returnList:ArrayList<Todo> = storage.getDoneTasks()

                println("all done todos: $returnList")

                return returnList
            }

            else->{
                return arrayListOf<Todo>()
            }
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     */
    interface OnListInteractionListener {
        fun removeTodo(id: Long)
        fun update(id: Long, description: String, priority: Int, status: String)
    }

    inner class MyListInteractionListener: OnListInteractionListener{

        override fun removeTodo(id: Long) {
            val returnVal = storage.remove(id)

            println("this is the return value: $returnVal")
        }

        override fun update(id: Long, description: String, priority: Int, status: String) {
            val returnVal = storage.update(id,  description, priority, status)

            println("This is the return value: $returnVal")
        }

    }

    companion object {

        private val ARG_FRAGMENT_TYPE = "fragement-type" // Fragment type
        val PENDING_FRAGMENT_TYPE = 1 // This type will retrieve tasks that are pending
        val DONE_FRAGMENT_TYPE = 2  // this type will retrieve tasks that are done

        fun newInstance(fragmentType: Int): TodosFragment {
            val fragment = TodosFragment()
            val args = Bundle()
            args.putInt(ARG_FRAGMENT_TYPE, fragmentType)
            fragment.arguments = args
            return fragment
        }
    }
}
