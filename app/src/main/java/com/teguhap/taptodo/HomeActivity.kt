package com.teguhap.taptodo

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teguhap.taptodo.adapter.AdapterTodoListView
import com.teguhap.taptodo.data.TodoList
import com.teguhap.taptodo.databinding.ActivityHomeBinding
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    @SuppressLint("InflateParams", "SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.start_utama_gradient)

        //List To_do
        val todoListToday = mutableListOf<TodoList>(
            TodoList("Belanja Sayur Jam 10","22-10-2021",R.drawable.ic_belanja_green,false),
            TodoList("Kerjain tugas Riset Operasi","22-10-2021",R.drawable.ic_book_red,false),
            TodoList("Bikin Jus Apel","22-10-2021",R.drawable.ic_food_green,true),
            TodoList("Bikin Mie buat bekel","22-10-2021",R.drawable.ic_food_red,true),
            TodoList("Login Mobile Legend dapet skin gratis","25-10-2021",R.drawable.ic_other_blue,false)
        )

        val todoListTomorrow = mutableListOf<TodoList>(
            TodoList("Belajar Bareng Dia","23-10-2021",R.drawable.ic_book_green,false),
            TodoList("Webinar Kotlin Dicoding","24-10-2021",R.drawable.ic_book_red,false)
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
        val etTitle =viewBtmSheet.findViewById<EditText>(R.id.etAddTitle)
        val etDesc = viewBtmSheet.findViewById<EditText>(R.id.etAddDesc)
        val etDate = viewBtmSheet.findViewById<EditText>(R.id.etAddDate)
        val priority = viewBtmSheet.findViewById<RadioGroup>(R.id.rgPriority)

        //Home Layout Component
        binding.apply {
            rvTodoToday.adapter = AdapterTodoListView(todoListToday)
            rvTodoToday.layoutManager = LinearLayoutManager(this@HomeActivity)

            rvTodoTomorrow.adapter = AdapterTodoListView(todoListTomorrow)
            rvTodoTomorrow.layoutManager = LinearLayoutManager(this@HomeActivity)



            btnBook.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_book)
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnShop.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_belanja)
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnFood.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_food)
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnSport.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_sport)
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnDo.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_do)
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnOther.setOnClickListener {
                btmSheet.apply {
                    setContentView(viewBtmSheet)
                    ivItemAddTodo.setImageResource(R.drawable.item_other)
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }
        }//Tutuo Binding Home Activity


        etDate.setOnClickListener {
            val tanggal = calendar.get(Calendar.DATE)
            val bulan = calendar.get(Calendar.MONTH)
            val tahun= calendar.get(Calendar.YEAR)
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etDate.setText("$dayOfMonth-$month-$year")
            },tahun,bulan,tanggal)
            datePicker.show()

        }

        btnSubmitAddTodo.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()
            val datePicked = etDate.text.toString()
            Toast.makeText(this,"Title : $title,Desc : $desc",Toast.LENGTH_SHORT).show()
            todoListToday.add(TodoList(title,datePicked,R.drawable.ic_book_red,false))
            btmSheet.dismiss()
        }
    }
}