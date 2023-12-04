package com.example.samdollarfront

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samdollarfront.databinding.ActivityMainBinding
import com.example.samdollarfront.databinding.ActivityMenuBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MenuActivity : AppCompatActivity() {

    val menus = mutableListOf(
        Menu("", "")
    )

    val key = "123"

    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("user")
    private val menuRef = userRef.child(key)

    lateinit var binding: ActivityMenuBinding
    private lateinit var menuAdapter: MenuAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuAdapter = MenuAdapter(menus)

        binding.recStudents.layoutManager = LinearLayoutManager(this)
        binding.recStudents.adapter = MenuAdapter(menus)

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            // 클릭 시 새로운 메뉴 항목을 추가합니다.
            menuAdapter.addMenu("", "")
            val position = menuAdapter.itemCount - 1
            menuAdapter.notifyItemInserted(position)
            binding.recStudents.scrollToPosition(position)
        }

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            moveDataToDatabase(menus)
        }
    }

    fun moveDataToDatabase(menus: List<Menu>) {
        menuRef.removeValue()
        for (menu in menus) {
            val menuKey = menuRef.push().key
            menuKey?.let {
                menuRef.child(it).setValue(menu)
            }
        }
    }
}


