package com.example.samdollarfront

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samdollarfront.databinding.ActivityMenuBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MenuActivity : AppCompatActivity() {

    val menus = mutableListOf(
        Menu("", "")
    )

    lateinit var key: String

    lateinit var binding: ActivityMenuBinding
    private lateinit var menuAdapter: MenuAdapter


    val database = FirebaseDatabase.getInstance()
    val userRef = database.getReference("Store")

    lateinit var menuRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        key = (intent?.getStringExtra("key") as? String) ?: ""
        menuRef = userRef.child(key).child("menu")


        menuAdapter = MenuAdapter(menus)

        binding.recStudents.layoutManager = LinearLayoutManager(this)
        binding.recStudents.adapter = MenuAdapter(menus)

        val backButton = findViewById<ImageButton>(R.id.btn_main2)

        backButton.setOnClickListener {
            finish()
        }

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


