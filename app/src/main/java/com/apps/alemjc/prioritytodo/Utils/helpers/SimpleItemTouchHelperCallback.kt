package com.apps.alemjc.prioritytodo.Utils.helpers

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by Jean Carlos Henriquez on 12/9/17.
 */
class SimpleItemTouchHelperCallback(val adapter: ItemTouchHelperAdapter): ItemTouchHelper.Callback() {

    companion object {
        val ALPHA_FULL = 1.0f
    }

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val drawFlags: Int = ItemTouchHelper.DOWN or ItemTouchHelper.UP
        val swipeFlags = 0

        return makeMovementFlags(drawFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView?, source: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
       return if (source == null || target == null) {
           false
       }

        else if (source.itemViewType != target.itemViewType) {
           false
        }
        else {
            adapter.onItemMove(source.adapterPosition, target.adapterPosition)

            true
        }
    }

    override fun isLongPressDragEnabled(): Boolean = true

    override fun isItemViewSwipeEnabled(): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}