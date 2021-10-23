package com.teguhap.taptodo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teguhap.taptodo.adapter.AdapterTodoListView
import com.teguhap.taptodo.data.TodoList
import com.teguhap.taptodo.databinding.ActivityHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    @SuppressLint("InflateParams", "SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.start_utama_gradient)


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
        val month_date = SimpleDateFormat("MMM")
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val date = calendar.get(Calendar.DATE)
        val day = calendar.getDisplayName(Calendar.DAY_OF_MONTH,Calendar.SHORT,Locale.getDefault())
        val month =  calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
        val year = calendar.get(Calendar.YEAR)

        val days = arrayOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")

        val currentDay = days[calendar.get(Calendar.DAY_OF_WEEK)-1];

        //Calender day,month,year
        binding.apply {
            tvDay.text = currentDay
            tvDate.text = date.toString()
            tvMonthYear.text = " $month $year"
        }


        val btmSheet = BottomSheetDialog(this)
        val viewBtmSheet = layoutInflater.inflate(R.layout.add_todo_sheet,null)
        val ivItemAddTodo :  ImageView = viewBtmSheet.findViewById(R.id.ivItemAddTodo)

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


        }
    }
}