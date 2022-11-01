package com.ntduc.swipereveallayout

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.ArrayList

/**
 * Created by Chau Thai on 4/8/16.
 */
class RecyclerDemoActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        setupActionBar()
        setupList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter!!.saveStates(outState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter!!.restoreStates(savedInstanceState)
        }
    }

    private fun setupList() {
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter(this, createList(20))
        recyclerView!!.adapter = adapter
    }

    private fun createList(n: Int): ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 0 until n) {
            list.add("View $i")
        }
        return list
    }

    private fun setupActionBar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            toolbar.setNavigationOnClickListener { finish() }
        }
    }
}