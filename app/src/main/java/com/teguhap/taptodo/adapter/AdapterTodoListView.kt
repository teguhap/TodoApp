package com.teguhap.taptodo.adapter

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.loader.content.Loader
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.teguhap.taptodo.R
import com.teguhap.taptodo.data.TodoList
import java.util.*

class AdapterTodoListView(val list: List<TodoList>) : RecyclerView.Adapter<AdapterTodoListView.HolderViewAdapter>() {

    inner class HolderViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderViewAdapter {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.todo_view_adapter, parent, false)
        return HolderViewAdapter(view)
    }

    fun strikeTrough(
        title: TextView,
        desc: TextView,
        background: ImageView,
        isChecked: Boolean,
        parent: Context
    ) {
        if (isChecked) {
            title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            desc.paintFlags = desc.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            background.setColorFilter(ContextCompat.getColor(parent, R.color.gray))

        } else {
            title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            desc.paintFlags = desc.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            background.setColorFilter(ContextCompat.getColor(parent, R.color.gray).inv())
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onBindViewHolder(holder: HolderViewAdapter, position: Int) {
        holder.itemView.apply {
            val title = findViewById<TextView>(R.id.tvTitleTodo)
            val date = findViewById<TextView>(R.id.tvDateTodo)
            val itemCategory = findViewById<ImageView>(R.id.ivItemCategory)
            val cb = findViewById<CheckBox>(R.id.cbDone)
            title.text = list[position].title.toString()
            date.text = list[position].date.toString()
            itemCategory.setImageResource(list[position].priorityItem)
            cb.isChecked = list[position].isChecked

            strikeTrough(title, date, itemCategory, list[position].isChecked, context)
            cb.setOnCheckedChangeListener { _, isChecked ->
                strikeTrough(title, date, itemCategory, isChecked, context)
            }

            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialogue_alert_todo)
            dialog.setCancelable(true)
            val window = dialog.window
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
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
            val itImageCategory = dialog.findViewById<TextView>(R.id.tvItemCategoryEdit)
            val ivImageCategory = dialog.findViewById<ImageView>(R.id.ivItemEditTodo)


            val clTodo = findViewById<ConstraintLayout>(R.id.cl_todolist)
            clTodo.setOnClickListener {

                //ImageView Category
                when (itImageCategory.text) {
                    "Studie" -> ivImageCategory.setImageResource(R.drawable.item_book)
                    "Shop" -> ivImageCategory.setImageResource(R.drawable.item_belanja)
                    "Food" -> ivImageCategory.setImageResource(R.drawable.item_food)
                    "Sport" -> ivImageCategory.setImageResource(R.drawable.item_sport)
                    "Activity" -> ivImageCategory.setImageResource(R.drawable.item_do)
                    else -> ivImageCategory.setImageResource(R.drawable.item_other)
                }



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



                titleDa.text = list[position].title.toString()
                if (list[position].desc.toString().isBlank()) {
                    descDa.text = "-"
                } else {
                    descDa.text = list[position].desc.toString()
                }
                priorityDa.text = list[position].priority.toString()
                dateDa.text = list[position].date.toString()

                titleEdit.setText(list[position].title.toString())
                if (list[position].desc.toString().isBlank()) {
                    descEdit.setText("-")
                } else {
                    descEdit.setText(list[position].desc.toString())
                }
                //PriorityData
                when (list[position].priority.toString()) {
                    "Normal" -> rgPriority.check(R.id.rbNormalEdit)
                    "Medium" -> rgPriority.check(R.id.rbMediumEdit)
                    else -> rgPriority.check(R.id.rbHighEdit)
                }
                //dateData

                dateEdit.setText(list[position].date.toString())
                itImageCategory.text = list[position].itemCategory.toString()
                when (itImageCategory.text) {
                    "Studie" -> spCategory.setSelection(0)
                    "Shop" -> spCategory.setSelection(1)
                    "Food" -> spCategory.setSelection(2)
                    "Sport" -> spCategory.setSelection(3)
                    "Activity" -> spCategory.setSelection(4)
                    else -> spCategory.setSelection(4)
                }

                dialog.show()

            }//tutup Constraint Layout

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

            //Date EditText
            val calendar = Calendar.getInstance()
            val tanggal = calendar.get(Calendar.DAY_OF_MONTH)
            val bulan = calendar.get(Calendar.MONTH)
            val tahun = calendar.get(Calendar.YEAR)

            dateEdit.setOnClickListener {
                val datePicker = DatePickerDialog(context, { view, year, month, dayOfMonth ->
                    var dateFormated = "" + dayOfMonth
                    var monthFormated = "${month + 1}"
                    if (dayOfMonth < 10) {
                        dateFormated = "0$dayOfMonth"
                    }
                    if (month < 9) {
                        monthFormated = "0${month + 1}"
                    }
                    dateEdit.setText("$dateFormated-$monthFormated-$year")
                }, tahun, bulan, tanggal)
                datePicker.show()

            }


            //Loader itialization
            val loader = ProgressDialog(context)

            //DataBase Initialization
            val mAuth = FirebaseAuth.getInstance()
            val mUser = mAuth.currentUser
            val onlineUserId = mUser?.uid.toString()
            val reference =
                FirebaseDatabase.getInstance("https://todo-app-226de-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .getReference("TodoList").child(onlineUserId)
            val id = list[position].id.toString()


            btnUpdate.setOnClickListener {
                //RgPriority
                val priorityChosed = when (rgPriority.checkedRadioButtonId) {
                    R.id.rbNormalEdit -> "Normal"
                    R.id.rbMediumEdit -> "Medium"
                    else -> "High"
                }


                //Priority Item
                val priorityItem = when (spCategory.selectedItem.toString()) {
                    "Studie" -> {
                        when (priorityChosed) {
                            "Normal" -> R.drawable.ic_book_green
                            "Medium" -> R.drawable.ic_book_blue
                            else -> R.drawable.ic_book_red
                        }
                    }
                    "Shop" -> {
                        when (priorityChosed) {
                            "Normal" -> R.drawable.ic_belanja_green
                            "Medium" -> R.drawable.ic_belanja_blue
                            else -> R.drawable.ic_belanja_red
                        }
                    }
                    "Food" -> {
                        when (priorityChosed) {
                            "Normal" -> R.drawable.ic_food_green
                            "Medium" -> R.drawable.ic_food_blue
                            else -> R.drawable.ic_food_red
                        }
                    }
                    "Sport" -> {
                        when (priorityChosed) {
                            "Normal" -> R.drawable.ic_sport_green
                            "Medium" -> R.drawable.ic_sport_blue
                            else -> R.drawable.ic_sport_red
                        }
                    }
                    "Activity" -> {
                        when (priorityChosed) {
                            "Normal" -> R.drawable.ic_go_green
                            "Medium" -> R.drawable.ic_go_blue
                            else -> R.drawable.ic_go_red
                        }
                    }
                    else -> {
                        when (priorityChosed) {
                            "Normal" -> R.drawable.ic_other_green
                            "Medium" -> R.drawable.ic_other_blue
                            else -> R.drawable.ic_other_red
                        }
                    }
                }

                //Data Updatre
                val titleEditChosed = titleEdit.text.toString()
                var descChosed = descEdit.text.toString()
                val itCategoryChosed = itImageCategory.text.toString()
                var datePicked = dateEdit.text.toString()

                Log.d(
                    "DataUpdate", "($id,$titleEditChosed,$descChosed,$datePicked,$priorityItem," +
                            "$priorityChosed,$itCategoryChosed"
                )

                if (datePicked.isEmpty()) {
                    datePicked = "$tanggal/$bulan/$tahun"
                }
                if (descChosed.isEmpty()) {
                    descChosed = " "
                }

                if (titleEditChosed.isEmpty()) {
                    titleEdit.error = "Title is Required"
                } else {
                    val todoData = TodoList(
                        id,
                        titleEditChosed,
                        descChosed,
                        datePicked,
                        priorityItem,
                        priorityChosed,
                        itCategoryChosed,
                        false
                    )
                    loader.setTitle("Update your todo")
                    loader.setCanceledOnTouchOutside(false)
                    loader.show()

                    reference.child(id).setValue(todoData).addOnCompleteListener(
                        OnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(context, "Update Succesfull", Toast.LENGTH_LONG)
                                    .show()
                                loader.dismiss()
                            } else {
                                val error = it.exception
                                Toast.makeText(context, "Update Failed $error", Toast.LENGTH_LONG)
                                    .show()
                                loader.dismiss()
                            }

                        })
                    dialog.dismiss()
                }
            }//Tutup btnUpdate

            btnDelete.setOnClickListener {
                loader.setTitle("Update your todo")
                loader.setCanceledOnTouchOutside(false)
                loader.show()
                reference.child(id).removeValue().addOnCompleteListener(
                    OnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(context, "Delete Succesfull", Toast.LENGTH_SHORT)
                                .show()
                            loader.dismiss()
                        } else {
                            val error = it.exception.toString()
                            Toast.makeText(
                                context,
                                "Delete Failed :  $error",
                                Toast.LENGTH_LONG
                            ).show()
                            loader.dismiss()
                        }
                    }
                )
                dialog.dismiss()
            }//Tutup btnDelete

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}