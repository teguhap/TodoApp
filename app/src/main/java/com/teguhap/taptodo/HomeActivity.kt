package com.teguhap.taptodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teguhap.taptodo.adapter.AdapterTodoListView
import com.teguhap.taptodo.data.TodoList
import com.teguhap.taptodo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.start_utama_gradient)

        val btmSheet = BottomSheetDialog(this)

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

        binding.apply {
            rvTodoToday.adapter = AdapterTodoListView(todoListToday)
            rvTodoToday.layoutManager = LinearLayoutManager(this@HomeActivity)

            rvTodoTomorrow.adapter = AdapterTodoListView(todoListTomorrow)
            rvTodoTomorrow.layoutManager = LinearLayoutManager(this@HomeActivity)

            btnBook.setOnClickListener {
                btmSheet.apply {
                    setContentView(R.layout.add_todo_sheet)
                    setCanceledOnTouchOutside(true)
                    show()
                }
            }

            btnShop.setOnClickListener {

                btmSheet.apply {
                    setContentView(R.layout.add_todo_sheet)
                    setCanceledOnTouchOutside(true)
                    show()
                }

            }


        }
    }
}