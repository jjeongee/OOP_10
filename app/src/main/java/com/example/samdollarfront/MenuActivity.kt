package com.example.samdollarfront

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samdollarfront.databinding.ActivityMenuBinding
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
            // 클릭 시 새로운 메뉴 항목을 추가합니다.
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

        // 저장 완료 메시지 등을 필요에 따라 추가할 수 있습니다.
        // 예: Toast.makeText(this, "메뉴가 저장되었습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun loadDataFromSharedPreferences() {
        val menusJson = sharedPreferences.getString("menus", "")
        // JSON 형태의 문자열을 List<Menu> 객체로 변환
        val menusType = object : TypeToken<List<Menu>>() {}.type
        val loadedMenus = Gson().fromJson<List<Menu>>(menusJson, menusType)

        // 메뉴 리스트를 업데이트
        menus.clear()
        menus.addAll(loadedMenus)

        // 메뉴 어댑터에 데이터 설정

    }
    private fun saveKey(key: String) {     //
        // SharedPreferences에 account 값을 저장
        val editor = sharedPreferences.edit()
        editor.putString("key", key)
        editor.apply()
    }
}




