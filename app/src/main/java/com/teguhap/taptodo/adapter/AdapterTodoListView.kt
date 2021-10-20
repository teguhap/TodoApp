package com.teguhap.taptodo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teguhap.taptodo.R
import com.teguhap.taptodo.data.TodoList

class AdapterTodoListView(val list: List<TodoList>) : RecyclerView.Adapter<AdapterTodoListView.HolderViewAdapter>() {

    inner class HolderViewAdapter(itemView :  View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderViewAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_view_adapter,parent,false)
        return HolderViewAdapter(view)
    }

    override fun onBindViewHolder(holder: HolderViewAdapter, position: Int) {
        holder.itemView.apply {
            val title = findViewById<TextView>(R.id.tvTitleTodo)
            val description = findViewById<TextView>(R.id.tvDescTodo)
            val itemCategory =  findViewById<ImageView>(R.id.ivItemCategory)
            val isChecked = findViewById<CheckBox>(R.id.cbDone)


        }

    }

    override fun getItemCount(): Int {
     return list.size
    }
}