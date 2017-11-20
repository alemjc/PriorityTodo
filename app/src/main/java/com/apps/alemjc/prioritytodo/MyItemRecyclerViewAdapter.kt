package com.apps.alemjc.prioritytodo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.apps.alemjc.prioritytodo.PendingTodosFragment.OnListInteractionListener
import com.apps.alemjc.prioritytodo.content.Todo

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * Todo: Replace the implementation with code for your data type.
 */
 class MyItemRecyclerViewAdapter(private val mValues:List<Todo>, private val mListener:OnListInteractionListener?):RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

 override fun getItemCount():Int {
  return mValues.size
 }

 override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder {
  val view = LayoutInflater.from(parent.getContext())
  .inflate(R.layout.fragment_item, parent, false)
  return ViewHolder(view)
 }

 override fun onBindViewHolder(holder:ViewHolder, position:Int) {


  holder.mView.setOnClickListener{

  }
}

inner class ViewHolder( val mView:View):RecyclerView.ViewHolder(mView) {


  init{

  }

 }
}
