package com.teguhap.taptodo.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Paint
import android.view.*
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teguhap.taptodo.R
import com.teguhap.taptodo.data.TodoList

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
            title.text = list[position].title.toString()
            date.text = list[position].date.toString()
            itemCategory.setImageResource(list[position].priorityItem)
            cb.isChecked = list[position].isChecked

            strikeTrough(title,date,itemCategory,list[position].isChecked,context)
            cb.setOnCheckedChangeListener{_,isChecked ->
                strikeTrough(title,date,itemCategory,isChecked,context)
            }


            val clTodo = findViewById<ConstraintLayout>(R.id.cl_todolist)
            clTodo.setOnClickListener {
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.dialogue_alert_todo)
                dialog.setCancelable(true)
                val window = dialog.window
                window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                val btnEdit = dialog.findViewById<Button>(R.id.btnEditDa)
                val btnBack = dialog.findViewById<Button>(R.id.btnBackDa)
                val btnDelete = dialog.findViewById<Button>(R.id.btnDeleteDa)
                val btnUpdate = dialog.findViewById<Button>(R.id.btnUpdateDa)
                val titleDa = dialog.findViewById<TextView>(R.id.titleDa)
                val descDa = dialog.findViewById<TextView>(R.id.descDa)
                val dateDa = dialog.findViewById<TextView>(R.id.dateDa)
                val priorityDa = dialog.findViewById<TextView>(R.id.tvPriorityDa)
                val titleEdit = dialog.findViewById<EditText>(R.id.etTitleEdit)
                val descEdit = dialog.findViewById<EditText>(R.id.etDescEdit)
                val dateEdit = dialog.findViewById<EditText>(R.id.etDateEdit)
                val tvCategory = dialog.findViewById<TextView>(R.id.tvCategoryEdit)
                val rgPriority = dialog.findViewById<RadioGroup>(R.id.rgPriorityEdit)
                val spCategory = dialog.findViewById<Spinner>(R.id.spCategoryEdit)

                dialog.show()
                btnEdit.setOnClickListener {
                    titleDa.visibility = View.GONE
                    descDa.visibility = View.GONE
                    dateDa.visibility = View.GONE
                    priorityDa.visibility = View.GONE
                    titleEdit.visibility = View.VISIBLE
                    descEdit.visibility = View.VISIBLE
                    dateEdit.visibility = View.VISIBLE
                    rgPriority.visibility = View.VISIBLE
                    tvCategory.visibility = View.VISIBLE
                    spCategory.visibility = View.VISIBLE
                    btnEdit.visibility = View.GONE
                    btnDelete.visibility = View.GONE
                    btnBack.visibility = View.VISIBLE
                    btnUpdate.visibility = View.VISIBLE
                }

                btnBack.setOnClickListener {
                    titleDa.visibility = View.VISIBLE
                    descDa.visibility = View.VISIBLE
                    dateDa.visibility = View.VISIBLE
                    priorityDa.visibility = View.VISIBLE
                    titleEdit.visibility = View.GONE
                    descEdit.visibility = View.GONE
                    dateEdit.visibility = View.GONE
                    rgPriority.visibility = View.GONE
                    tvCategory.visibility = View.GONE
                    spCategory.visibility = View.GONE
                    btnEdit.visibility = View.VISIBLE
                    btnDelete.visibility = View.VISIBLE
                    btnBack.visibility = View.GONE
                    btnUpdate.visibility = View.GONE
                }
            }


        }

    }

    override fun getItemCount(): Int {
     return list.size
    }
}