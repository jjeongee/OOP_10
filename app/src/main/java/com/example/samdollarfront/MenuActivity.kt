package com.example.samdollarfront

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samdollarfront.databinding.ActivityMenuBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MenuActivity : AppCompatActivity() {

    val menus = mutableListOf(
        Menu("", "")
    )

    lateinit var key: String

    lateinit var binding: ActivityMenuBinding
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var sharedPreferences: SharedPreferences

    val database = FirebaseDatabase.getInstance()
    val userRef = database.getReference("Store")

    lateinit var menuRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        key = (intent?.getStringExtra("key") as? String) ?: ""
        if (key == "") {
            key = sharedPreferences.getString("key","").toString()
        }
        saveKey(key)
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
            menuAdapter.addMenu("", "")
            val position = menuAdapter.itemCount - 1
            menuAdapter.notifyItemInserted(position)
            binding.recStudents.scrollToPosition(position)
        }

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            moveDataToDatabase(menus)
            saveDataToSharedPreferences(menus)
        }
        loadDataFromSharedPreferences()
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

    private fun saveDataToSharedPreferences(menus: List<Menu>) {
        val editor = sharedPreferences.edit()
        // 메뉴 데이터를 JSON 형태의 문자열로 변환하여 SharedPreferences에 저장
        val menusJson = Gson().toJson(menus)
        editor.putString("menus", menusJson)
        editor.apply()
    }

    private fun loadDataFromSharedPreferences() {
        val menusJson = sharedPreferences.getString("menus", "")
        if (menusJson.isNullOrEmpty()) {
            Toast.makeText(this, "저장된 메뉴가 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        // JSON 형태의 문자열을 List<Menu> 객체로 변환
        val menusType = object : TypeToken<List<Menu>>() {}.type
        val loadedMenus = Gson().fromJson<List<Menu>>(menusJson, menusType)
        menus.clear()
        menus.addAll(loadedMenus)
    }

    private fun saveKey(key: String) {     //
        val editor = sharedPreferences.edit()
        editor.putString("key", key)
        editor.apply()
    }

}
