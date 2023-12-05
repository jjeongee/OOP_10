package com.example.samdollarfront

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class StoreActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    val list = ArrayList<DataSnapshot>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        recyclerView = findViewById(R.id.Menu_recy)

        val adapter = StoreMenuAdapter(list)

        database = FirebaseDatabase.getInstance().getReference().child("Store")

        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                list.clear()

                for (storeSnapshot in dataSnapshot.children) {
                    val menuSnapshot = storeSnapshot.child("menu")

                    for (menu in menuSnapshot.children) {
                        list.add(menu)
                    }
                }
                adapter.notifyDataSetChanged()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val mainbutton = findViewById<ImageButton>(R.id.btn_main2)

        mainbutton.setOnClickListener {
            finish()
        }

        var name = intent.getStringExtra("name")
        var account = intent.getStringExtra("account")
        var bank = intent.getStringExtra("bank")
        var ownername = intent.getStringExtra("ownername")

        val storeName = findViewById<TextView>(R.id.store_txt_name)
        storeName.text = name.toString()

        val storeAccount = findViewById<TextView>(R.id.store_txt_account)
        storeAccount.text = account.toString()

        val storeBank = findViewById<TextView>(R.id.store_txt_bank)
        storeBank.text = bank.toString()

        val storeOwnername = findViewById<TextView>(R.id.store_txt_ownername)
        storeOwnername.text = ownername.toString()

        val copy = findViewById<ImageButton>(R.id.btn_copy_store)
        copy.setOnClickListener {
            val clipboard =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("계좌번호", storeAccount.text.toString())
            clipboard.setPrimaryClip(clip)

            Toast.makeText(
                this@StoreActivity,
                "계좌번호가 클립보드에 복사되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}