package com.teguhap.taptodo.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.teguhap.taptodo.HomeActivity
import com.teguhap.taptodo.R
import com.teguhap.taptodo.data.TodoList
import com.teguhap.taptodo.databinding.ActivityHomeBinding.inflate
import java.util.zip.Inflater

class AdapterTodoListView(val list: List<TodoList>) : RecyclerView.Adapter<AdapterTodoListView.HolderViewAdapter>() {

    inner class HolderViewAdapter(itemView :  View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderViewAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_view_adapter,parent,false)
        return HolderViewAdapter(view)
    }

    fun strikeTrough(title : TextView,desc : TextView,background : ImageView,isChecked: Boolean,parent: Context){
        if(isChecked){
            title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            desc.paintFlags = desc.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            background.setColorFilter(ContextCompat.getColor(parent,R.color.gray))

        }else{
            title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            desc.paintFlags = desc.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            background.setColorFilter(ContextCompat.getColor(parent,R.color.gray).inv())
        }
    }

    @SuppressLint("InflateParams")
    override fun onBindViewHolder(holder: HolderViewAdapter, position: Int) {
        holder.itemView.apply {
            val title = findViewById<TextView>(R.id.tvTitleTodo)
            val date = findViewById<TextView>(R.id.tvDateTodo)
            val itemCategory =  findViewById<ImageView>(R.id.ivItemCategory)
            val cb = findViewById<CheckBox>(R.id.cbDone)
            val clTodo = findViewById<ConstraintLayout>(R.id.cl_todolist)
            title.text = list[position].title.toString()
            date.text = list[position].date.toString()
            itemCategory.setImageResource(list[position].priorityItem)
            cb.isChecked = list[position].isChecked

            strikeTrough(title,date,itemCategory,list[position].isChecked,context)
            cb.setOnCheckedChangeListener{_,isChecked ->
                strikeTrough(title,date,itemCategory,isChecked,context)
            }


        }

    }

    override fun getItemCount(): Int {
     return list.size
    }
}