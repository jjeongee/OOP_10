package com.example.samdollarfront

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.samdollarfront.databinding.ActivityMineBinding
import com.google.firebase.database.*

class User(val storeName: String = "",val name: String = "", val sale: String = "", val account: String = "", val bank: String = "")
class MineActivity : AppCompatActivity() {

    lateinit var binding: ActivityMineBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        userRef = database.getReference("Store")
            sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        val backButton = findViewById<ImageButton>(R.id.btn_main2)



        val enter = findViewById<Button>(R.id.enter)

        enter.setOnClickListener {
            val storeName = binding.storeNameInput.text.toString()
            val name = binding.nameInput.text.toString()
            val sale = binding.saleInput.text.toString()
            val account = binding.accountInput.text.toString()
            val bank = binding.bankInput.text.toString()
            // SharedPreferences에 account 값을 저장
            saveAccount(account)

            if (storeName.isNotEmpty() && name.isNotEmpty() && sale.isNotEmpty() && account.isNotEmpty() && bank.isNotEmpty()) {
                saveUser(storeName,name, sale, account, bank)
            }
        }

        backButton.setOnClickListener {
            val account = sharedPreferences.getString("account", "")
            val intent = Intent(this,OwnerActivity::class.java)
            intent.putExtra("key", account)
            startActivity(intent)
        }


        // 앱이 다시 실행될 때 저장된 account 값을 불러오고 해당 account에 대한 데이터를 Firebase에서 가져와 화면에 표시
        loadUserData()
    }

    private fun saveUser(storeName: String,name: String, sale: String, account: String, bank: String) {
        val user = User(storeName, name, sale, account, bank)

        // Firebase에 사용자 정보 저장
        val newUserRef = userRef.child(account) // account를 key로 사용
        newUserRef.setValue(user)
    }

    private fun saveAccount(account: String) {
        // SharedPreferences에 account 값을 저장
        val editor = sharedPreferences.edit()
        editor.putString("account", account)
        editor.apply()
    }

    private fun loadUserData() {
        // SharedPreferences에서 저장된 account 값을 불러오기
        val account = sharedPreferences.getString("account", "")

        if (account!!.isNotEmpty()) {
            // Firebase에서 해당 account에 대한 사용자 정보 불러오기
            userRef.child(account).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        // 사용자 정보를 화면에 표시하는 로직 추가
                        binding.storeNameInput.setText(user.storeName)
                        binding.nameInput.setText(user.name)
                        binding.saleInput.setText(user.sale)
                        binding.accountInput.setText(user.account)
                        binding.bankInput.setText(user.bank)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // 실패 시의 처리
                }
            })
        }
    }



}
