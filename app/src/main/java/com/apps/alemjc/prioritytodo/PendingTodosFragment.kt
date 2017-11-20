package com.apps.alemjc.prioritytodo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apps.alemjc.prioritytodo.content.Todo

/**
 * A fragment representing a list of Items.
 *
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class PendingTodosFragment : Fragment() {
    private var FragmentType = 1
    private var mListener: MyListInteractionListener? = MyListInteractionListener()
    private var todos: ArrayList<Todo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (arguments != null) {
            FragmentType = arguments.getInt(ARG_FRAGMENT_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            val recyclerView = view as RecyclerView
            recyclerView.setLayoutManager(LinearLayoutManager(context))
            recyclerView.adapter = MyItemRecyclerViewAdapter(getContext(), todos, mListener as OnListInteractionListener)
        }
        return view
    }


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListInteractionListener {
        fun addTodo(item: Todo)
        fun removeTodo(id: Long)
        fun increasePriority(id: Long, priority: Int)
        fun getTodo(): ArrayList<Todo>

    }

    class MyListInteractionListener: OnListInteractionListener{
        override fun addTodo(item: Todo) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun removeTodo(id: Long) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun increasePriority(id: Long, priority: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getTodo(): ArrayList<Todo> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    companion object {

        private val ARG_FRAGMENT_TYPE = "fragement-type" // Fragment type
        val PENDING_FRAGMENT_TYPE = 1 // This type will retrieve tasks that are pending
        val DONE_FRAGMENT_TYPE = 2  // this type will retrieve tasks that are done

        fun newInstance(fragmentType: Int): PendingTodosFragment {
            val fragment = PendingTodosFragment()
            val args = Bundle()
            args.putInt(ARG_FRAGMENT_TYPE, fragmentType)
            fragment.arguments = args
            return fragment
        }
    }
}
