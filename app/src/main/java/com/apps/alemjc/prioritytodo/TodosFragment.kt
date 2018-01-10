package com.apps.alemjc.prioritytodo


import android.app.DialogFragment
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apps.alemjc.prioritytodo.Interfaces.OnListActionListener
import com.apps.alemjc.prioritytodo.Interfaces.OnListIteractionListener
import com.apps.alemjc.prioritytodo.Storage.SQLTodoStorage
import com.apps.alemjc.prioritytodo.Storage.TodoTableValues
import com.apps.alemjc.prioritytodo.Utils.helpers.ItemTouchHelperAdapter
import com.apps.alemjc.prioritytodo.Utils.helpers.SimpleItemTouchHelperCallback
import com.apps.alemjc.prioritytodo.content.Todo

/**
 * A fragment representing a list of Items.
 * This list of items could be pending to do tasks or done tasks.
 * This item also calls its main activity in order to update order instances of itself
 * that would contain order lists of tasks, for example if this fragment displays pending tasks
 * then it would call the activity to updating other fragments containing done tasks.
 *
 */
class TodosFragment : Fragment(), CreateDialogFragment.OnDialogInteractionListener, OnListActionListener {
    private var fragmentType = PENDING_FRAGMENT_TYPE
    private var todos: ArrayList<Todo> = ArrayList()
    private var recycler: RecyclerView? = null
    private var actionListListener: OnListIteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            fragmentType = arguments.getInt(ARG_FRAGMENT_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_item_list, container, false)
        val addButton: FloatingActionButton = view.findViewById<FloatingActionButton>(R.id.add)
        recycler = view.findViewById(R.id.list)

        recycler!!.adapter = PendingItemsRecyclerViewAdapter(context, todos, fragmentType, this)
        recycler!!.layoutManager = LinearLayoutManager(context)

        addButton.setOnClickListener { addTodo() }

        if (fragmentType == TodosFragment.DONE_FRAGMENT_TYPE) {
            addButton.visibility = View.INVISIBLE
        }

        val touchCallback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(recycler!!.adapter as ItemTouchHelperAdapter)
        val itemTouchHelper = ItemTouchHelper(touchCallback)
        itemTouchHelper.attachToRecyclerView(recycler)

        return view
    }

    override fun onResume() {
        super.onResume()
        updateTodos()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnListIteractionListener) {
            actionListListener = context
        } else {
            RuntimeException("Activity class must implement OnListActionListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        actionListListener = null
    }

    override fun onCancel() {

    }

    fun addTodo() {
        val dialog = CreateDialogFragment() as DialogFragment
        dialog.show(childFragmentManager, "addDialog")
    }

    fun getTodos(): ArrayList<Todo> {

        val storage = SQLTodoStorage.getInstance(context)

        when (fragmentType) {
            PENDING_FRAGMENT_TYPE -> {
                val returnList: ArrayList<Todo> = storage.getPendingTasks()

                recycler!!.adapter.notifyDataSetChanged()

                return returnList
            }
            DONE_FRAGMENT_TYPE -> {
                val returnList: ArrayList<Todo> = storage.getDoneTasks()

                recycler!!.adapter.notifyDataSetChanged()

                return returnList
            }

            else -> {
                return arrayListOf()
            }
        }

    }

    override fun updateTodos() {
        todos = getTodos()
        val adapter = recycler!!.adapter as PendingItemsRecyclerViewAdapter
        adapter.setValues(todos)
        recycler!!.adapter.notifyDataSetChanged()
    }

    override fun removeTodo(id: Long) {
        val storage = SQLTodoStorage.getInstance(context)
        val returnVal = storage.remove(id)
        val mAdapter: PendingItemsRecyclerViewAdapter = recycler!!.adapter as PendingItemsRecyclerViewAdapter
        val adapterList: ArrayList<Todo> = mAdapter.getValues() as ArrayList<Todo>
        var indexToRemove: Int = -1

        adapterList.forEachIndexed { index, todo ->
            if (todo._id == id) {
                indexToRemove = index
            }
        }

        if (indexToRemove >= 0) {
            adapterList.removeAt(indexToRemove)
            mAdapter.notifyItemRemoved(indexToRemove)
        }

    }

    override fun update(id: Long, description: String, priority: Long, status: String) {
        val storage = SQLTodoStorage.getInstance(context)
        storage.update(id, description, priority, status)

        updateTodos()

    }

    override fun statusUpdate(todoItem: Todo, newStatus: String) {
        val storage = SQLTodoStorage.getInstance(context)
        storage.update(todoItem._id, todoItem.description, todoItem.priority, newStatus)

        if (todoItem.status == TodoTableValues.PENDING_STATUS && newStatus == TodoTableValues.DONE_STATUS) {
            actionListListener!!.updateDoneTasksView()
        } else {
            actionListListener!!.updatePendingTasksView()
        }

        updateTodos()
    }

    override fun onItemCreated(item: Todo) {
        val storage = SQLTodoStorage.getInstance(context)
        val returnVal = storage.create(item.description, item.priority)
        updateTodos()
        Log.d("onItemCreated", "this is the return value $returnVal")
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
