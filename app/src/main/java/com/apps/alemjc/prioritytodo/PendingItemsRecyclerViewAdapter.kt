package com.apps.alemjc.prioritytodo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.apps.alemjc.prioritytodo.Interfaces.OnListActionListener
import com.apps.alemjc.prioritytodo.Storage.TodoTableValues
import com.apps.alemjc.prioritytodo.Utils.helpers.ItemTouchHelperAdapter
import com.apps.alemjc.prioritytodo.content.Todo
import com.apps.alemjc.prioritytodo.Utils.buildAndShow
import java.util.*


/**
 * [RecyclerView.Adapter] that can display a [Todo]
 * and makes a call to the specified [OnListActionListener].
 *
 */
class PendingItemsRecyclerViewAdapter(private val ctx: Context, private var values: List<Todo>, val type: Int,
                                      private val mListener: OnListActionListener) : RecyclerView.Adapter<PendingItemsRecyclerViewAdapter.ViewHolder>(),
                                                                                        ItemTouchHelperAdapter,
                                                                                        View.OnTouchListener{

    private val sizes: Map<Long, Float> = mapOf(Pair(1L, 17f), Pair(2L, 18f), Pair(3L, 19f), Pair(4L, 20f))
    private var isTouched = false

    fun getValues(): List<Todo> {
        return values
    }

    fun setValues(values: List<Todo>) {
        this.values = values
    }

    override fun onItemMove(start: Int, end: Int) {

        if (start < end){
            for (i in start until end) {
                val todo:Todo = values[i]

                todo.priority = if (values[i+1].priority > 1)  values[i+1].priority-1 else values[i+1].priority

                Collections.swap(values, i, i+1)

                mListener.update(todo._id, todo.description, todo.priority, todo.status)
            }
        }
        else{
            for (i in start downTo end +1){
                val todo:Todo = values[i]

                todo.priority = if (values[i-1].priority < 4 ) values[i-1].priority+1 else values[i-1].priority

                Collections.swap(values, i, i-1)

                mListener.update(todo._id, todo.description, todo.priority, todo.status)
            }
        }

    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val todoItem: Todo = values[position]
        val actions = holder.actions
        val description = holder.description

        actions.setSelection(todoItem.priority.toInt() - 1)

        description.text = todoItem.description

        if (type != TodosFragment.DONE_FRAGMENT_TYPE) {
            description.textSize = sizes[todoItem.priority] ?: 18f
        }

        actions.onItemSelectedListener = MyOnSelectListener(todoItem._id)

        holder.checkedButton.setOnClickListener {
            mListener.statusUpdate(todoItem, TodoTableValues.DONE_STATUS)
        }

        holder.undoButton.setOnClickListener {
            mListener.statusUpdate(todoItem, TodoTableValues.PENDING_STATUS)
        }

        holder.deleteButton.setOnClickListener {
            ctx.buildAndShow("Are you sure you want to proceed?", "No",
                    "Yes"){ if (!it){ mListener.removeTodo(todoItem._id) } }
        }

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        isTouched = true
        return false
    }

    inner class MyOnSelectListener(val id: Long) : AdapterView.OnItemSelectedListener {


        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            if (!isTouched) return

            val todo = values.find { it._id == this.id } ?: return

            val selectedItem = parent!!.getItemAtPosition(position) as CharSequence

            mListener.update(this.id, todo.description, selectedItem.toString().toLong(), todo.status)
            isTouched = false


        }
    }


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        val description: TextView = mView.findViewById(R.id.description)
        val actions: Spinner = mView.findViewById(R.id.actions)
        val checkedButton: CheckedTextView = mView.findViewById(R.id.checked)
        val undoButton: ImageButton = mView.findViewById(R.id.undo)
        val deleteButton: ImageButton = mView.findViewById(R.id.delete)

        init {
            val arrayAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(ctx, R.array.user_choices, R.layout.support_simple_spinner_dropdown_item)
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            mView.setOnTouchListener(this@PendingItemsRecyclerViewAdapter)
            actions.adapter = arrayAdapter

            if (type == TodosFragment.DONE_FRAGMENT_TYPE) {
                actions.visibility = View.GONE
                checkedButton.visibility = View.GONE
                undoButton.visibility = View.VISIBLE
            }

        }

    }
}
