package com.ntduc.swipereveallayout

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.ArrayList

/**
 * Created by Chau Thai on 4/8/16.
 */
class RecyclerAdapter(private val mContext: Context, dataSet: ArrayList<String>?) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var mDataSet: ArrayList<String>? = ArrayList()
    private val mInflater: LayoutInflater
    private val binderHelper = ViewBinderHelper()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.row_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mDataSet != null && 0 <= position && position < mDataSet!!.size) {
            val data = mDataSet!![position]

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            binderHelper.bind(holder.swipeLayout, data)

            // Bind your data here
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return if (mDataSet == null) 0 else mDataSet!!.size
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val swipeLayout: SwipeRevealLayout
        private val frontLayout: View
        private val deleteLayout: View
        private val textView: TextView
        fun bind(data: String) {
            deleteLayout.setOnClickListener {
                mDataSet?.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
            textView.text = data
            frontLayout.setOnClickListener {
                val displayText = "$data clicked"
                Toast.makeText(mContext, displayText, Toast.LENGTH_SHORT).show()
                Log.d("RecyclerAdapter", displayText)
            }
        }

        init {
            swipeLayout = itemView.findViewById<View>(R.id.swipe_layout) as SwipeRevealLayout
            frontLayout = itemView.findViewById(R.id.front_layout)
            deleteLayout = itemView.findViewById(R.id.delete_layout)
            textView = itemView.findViewById<View>(R.id.text) as TextView
        }
    }

    init {
        mDataSet = dataSet
        mInflater = LayoutInflater.from(mContext)

        // uncomment if you want to open only one row at a time
        // binderHelper.setOpenOnlyOne(true);
    }
}