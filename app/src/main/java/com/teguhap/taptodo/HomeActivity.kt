package com.teguhap.taptodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teguhap.taptodo.adapter.AdapterTodoListView
import com.teguhap.taptodo.data.TodoList
import com.teguhap.taptodo.databinding.ActivityHomeBinding
import com.teguhap.taptodo.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.start_utama_gradient)

        val todoList = mutableListOf<TodoList>(
            TodoList("Belanja Sayur Jam 10","sayur bayem,sayur asem",R.drawable.ic_belanja_green,false),
            TodoList("Kerjain tugas Riset Operasi","12345678901234567890",R.drawable.ic_book_red,false),
            TodoList("Bikin Jus Apel","afaafkjafkajfajafafjaf",R.drawable.ic_food_green,true),
            TodoList("Bikin Mie buat bekel"," ",R.drawable.ic_food_red,true),
            TodoList("Login Mobile Legend dapet skin gratis","Jam 15.00",R.drawable.ic_other_blue,false)
        )

        binding.apply {
            rvTodoToday.adapter = AdapterTodoListView(todoList)
            rvTodoToday.layoutManager = LinearLayoutManager(this@HomeActivity)


        }
    }
}