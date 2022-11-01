package com.ntduc.swipereveallayout

import android.content.Context
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.os.Bundle
import android.util.Log
import android.view.View

/**
 * Created by Chau Thai on 4/12/16.
 */
class GridAdapter(context: Context?, objects: List<String?>?) : ArrayAdapter<String?>(
    context!!, R.layout.row_list, objects!!
) {
    private val mInflater: LayoutInflater
    private val binderHelper: ViewBinderHelper
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_item, parent, false)
            holder = ViewHolder()
            holder.swipeLayout =
                convertView.findViewById<View>(R.id.swipe_layout) as SwipeRevealLayout
            holder.frontView = convertView.findViewById(R.id.front_layout)
            holder.deleteView = convertView.findViewById(R.id.delete_layout)
            holder.textView = convertView.findViewById<View>(R.id.text) as TextView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val item = getItem(position)
        if (item != null) {
            binderHelper.bind(holder.swipeLayout!!, item)
            holder.textView!!.text = item
            holder.deleteView!!.setOnClickListener { remove(item) }
            holder.frontView!!.setOnClickListener {
                val displayText = "$item clicked"
                Toast.makeText(context, displayText, Toast.LENGTH_SHORT).show()
                Log.d("GridAdapter", displayText)
            }
        }
        return convertView!!
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onSaveInstanceState]
     */
    fun saveStates(outState: Bundle?) {
        binderHelper.saveStates(outState)
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onRestoreInstanceState]
     */
    fun restoreStates(inState: Bundle?) {
        binderHelper.restoreStates(inState)
    }

    private inner class ViewHolder {
        var swipeLayout: SwipeRevealLayout? = null
        var frontView: View? = null
        var deleteView: View? = null
        var textView: TextView? = null
    }

    init {
        mInflater = LayoutInflater.from(context)
        binderHelper = ViewBinderHelper()

        // uncomment if you want to open only one row at a time
        // binderHelper.setOpenOnlyOne(true);
    }
}