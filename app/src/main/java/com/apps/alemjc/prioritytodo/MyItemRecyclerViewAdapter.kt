package com.apps.alemjc.prioritytodo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

import com.apps.alemjc.prioritytodo.TodosFragment.OnListInteractionListener
import com.apps.alemjc.prioritytodo.content.Todo

/**
 * [RecyclerView.Adapter] that can display a [Todo]
 * and makes a call to the specified [OnListInteractionListener].
 *
 */
 class MyItemRecyclerViewAdapter(private val ctx: Context, private val mValues:List<Todo>, private val mListener:OnListInteractionListener):RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

 override fun getItemCount():Int {
  return mValues.size
 }

 override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder {
  val view = LayoutInflater.from(parent.context)
  .inflate(R.layout.fragment_item, parent, false)
  return ViewHolder(view)
 }

 override fun onBindViewHolder(holder:ViewHolder, position:Int) {
  val todoItem:Todo = mValues[position]
  val actions = holder.actions
  val description = holder.description.toString()

  actions.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
   override fun onNothingSelected(p0: AdapterView<*>?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
   }

   override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, id: Long) {
    val selectedItem = parent!!.getItemAtPosition(pos) as CharSequence

    if(selectedItem == "Delete"){
     mListener.removeTodo(todoItem._id)
    }
    else{
     mListener.update(todoItem._id, description, Integer.parseInt(selectedItem.toString()), todoItem.status)
    }
   }
  }
 }




inner class ViewHolder(mView:View):RecyclerView.ViewHolder(mView) {
  val description:TextView = mView.findViewById(R.id.description)
  val actions:Spinner = mView.findViewById(R.id.actions)

  init{
    val arrayAdapter:ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(ctx, R.array.user_choices, R.layout.support_simple_spinner_dropdown_item)
    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
    actions.adapter = arrayAdapter
  }

 }
}
