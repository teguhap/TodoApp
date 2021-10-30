package com.teguhap.taptodo

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.teguhap.taptodo.adapter.AdapterTodoListView
import com.teguhap.taptodo.data.TodoList
import com.teguhap.taptodo.data.TodoListDatabase
import com.teguhap.taptodo.databinding.ActivityHomeBinding
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mAuth : FirebaseAuth
    @SuppressLint("InflateParams", "SetTextI18n", "SimpleDateFormat",
        "UseCompatLoadingForDrawables"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.start_utama_gradient)

        val loader = ProgressDialog(this)

        //Firebase Database Connect
        mAuth = FirebaseAuth.getInstance()
        val mUser = mAuth.currentUser
        val onlineUserId = mUser?.uid.toString()
        val reference = FirebaseDatabase.getInstance("https://todo-app-226de-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("TodoList").child(onlineUserId)



        //List To_do
        val todoListToday = mutableListOf<TodoList>(
        )

        val todoListTomorrow = mutableListOf<TodoList>(
        )

        //Calendar
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val date = calendar.get(Calendar.DATE)
        val month =  calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
        val year = calendar.get(Calendar.YEAR)
        val days = arrayOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")
        val currentDay = days[calendar.get(Calendar.DAY_OF_WEEK)-1]

        //Calender day,month,year
        binding.apply {
            tvDay.text = currentDay
            tvDate.text = date.toString()
            tvMonthYear.text = " $month $year"
        }

        //Bottomshet Component
        val btmSheet = BottomSheetDialog(this)
        val viewBtmSheet = layoutInflater.inflate(R.layout.add_todo_sheet,null)
        val ivItemAddTodo :  ImageView = viewBtmSheet.findViewById(R.id.ivItemAddTodo)
        val btnSubmitAddTodo = viewBtmSheet.findViewById<AppCompatButton>(R.id.btnAddTodo)
        val tvItemCategory = viewBtmSheet.findViewById<TextView>(R.id.tvItemCategory)
        val etTitle =viewBtmSheet.findViewById<EditText>(R.id.etAddTitle)
        val etDesc = viewBtmSheet.findViewById<EditText>(R.id.etAddDesc)
        val etDate = viewBtmSheet.findViewById<EditText>(R.id.etAddDate)
        val priority = viewBtmSheet.findViewById<RadioGroup>(R.id.rgPriority)
        val adapterToday = AdapterTodoListView(todoListToday)
        //Home Layout Component
        binding.apply {
            rvTodoToday.adapter = adapterToday
            rvTodoTomorrow.adapter = AdapterTodoListView(todoListTomorrow)
            rvTodoToday.layoutManager = LinearLayoutManager(this@HomeActivity)
            rvTodoToday.setHasFixedSize(true)
            rvTodoTomorrow.layoutManager = LinearLayoutManager(this@HomeActivity)
            rvTodoTomorrow.setHasFixedSize(true)

            reference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        Log.d("TodoTest","snapshot exsit")
                        for(userTodoSnapshot in snapshot.children){
                            val userTodo = userTodoSnapshot.getValue(TodoList::class.java)
                            if(!todoListToday.contains(userTodo)){
                                todoListToday.add(userTodo!!)
                            }

                        }

                    }
                    rvTodoToday.adapter = adapterToday
                    rvTodoTomorrow.adapter = AdapterTodoListView(todoListTomorrow)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })



            btnBook.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_book)
                    tvItemCategory.text = "Book"
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnShop.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_belanja)
                    tvItemCategory.text = "Belanja"
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnFood.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_food)
                    tvItemCategory.text = "Food"
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnSport.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_sport)
                    tvItemCategory.text = "Sport"
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnDo.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_do)
                    tvItemCategory.text = "Do"
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnOther.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_other)
                    tvItemCategory.text = "other"
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }
        }//Tutuo Binding Home Activity

        val tanggal = calendar.get(Calendar.DATE)
        val bulan = calendar.get(Calendar.MONTH)
        val tahun= calendar.get(Calendar.YEAR)
        etDate.setOnClickListener {
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etDate.setText("$dayOfMonth-$month-$year")
            },tahun,bulan,tanggal)
            datePicker.show()

        }
        btnSubmitAddTodo.setOnClickListener {
            binding.rvTodoToday.adapter = adapterToday
            binding.rvTodoToday.layoutManager = LinearLayoutManager(this@HomeActivity)

            val title = etTitle.text.toString()
            var desc = etDesc.text.toString()
            var datePicked = etDate.text.toString()
            val id = reference.push().key
            val priorityChosed = when(priority.checkedRadioButtonId){
                R.id.rbNormal -> "Normal"
                R.id.rbMedium -> "Medium"
                else -> "High"
            }
            val imageItem : Int
            val priorityItem = when(tvItemCategory.text){
            "Book"->{
                when(priorityChosed){
                    "Normal" -> R.drawable.ic_book_green
                    "Medium" -> R.drawable.ic_book_blue
                    else -> R.drawable.ic_book_red
                }
            }
            "Belanja" ->{
                when(priorityChosed){
                "Normal" -> R.drawable.ic_belanja_green
                "Medium" -> R.drawable.ic_belanja_blue
                else -> R.drawable.ic_belanja_red
            }
            }
           "Food"-> {
                when(priorityChosed){
                    "Normal" -> R.drawable.ic_food_green
                    "Medium" -> R.drawable.ic_food_blue
                    else -> R.drawable.ic_food_red
                }
            }
            "Sport"-> {
                when(priorityChosed){
                    "Normal" -> R.drawable.ic_sport_green
                    "Medium" -> R.drawable.ic_sport_blue
                    else -> R.drawable.ic_sport_red
                }
            }
           "Do"-> {
                when(priorityChosed){
                    "Normal" -> R.drawable.ic_go_green
                    "Medium" -> R.drawable.ic_go_blue
                    else -> R.drawable.ic_go_red
                }
            }
                else -> {
                    when(priorityChosed){
                        "Normal" -> R.drawable.ic_other_green
                        "Medium" -> R.drawable.ic_other_blue
                        else -> R.drawable.ic_other_red
                    }
                }
            }



            if(datePicked.isEmpty()){
                datePicked = "$tanggal/$bulan/$tahun"
            }
            if(desc.isEmpty()){
                desc = " "
            }

            if(title.isEmpty()){
                etTitle.error = "Title is Required"
            }else{
                val todoData = TodoList(id,title,desc,datePicked,priorityItem,priorityChosed,false)
                loader.setTitle("Adding your todo")
                loader.setCanceledOnTouchOutside(false)
                loader.show()

                if (id != null) {
                    Log.d("TestTodo","Id tidak null")
                    reference.child(id).setValue(todoData).addOnCompleteListener(
                        OnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(this,"Adding Succesfull",Toast.LENGTH_LONG).show()
                                loader.dismiss()
                            }else{
                                val error = it.exception
                                Toast.makeText(this,"Adding Failed $error",Toast.LENGTH_LONG).show()
                                loader.dismiss()
                            }

                        })
                }
                btmSheet.dismiss()
            }
            etTitle.text.clear()
            etDesc.text.clear()
            priority.check(R.id.rbNormal)
            etDate.text = null
        }

    }



}