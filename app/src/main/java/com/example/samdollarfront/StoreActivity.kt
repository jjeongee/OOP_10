package com.example.samdollarfront

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    var isZzimSelected = false
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        val zzimbtn = findViewById<ImageButton>(R.id.zzimButton)

            recyclerView = findViewById(R.id.Menu_recy)
            val list = ArrayList<DataSnapshot>()

            val adapter = StoreMenuAdapter(list)

            val database = FirebaseDatabase.getInstance().getReference().child("Store")

            database.addValueEventListener(object : ValueEventListener {
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

            val name = intent.getStringExtra("store_info_name")
            val account = intent.getStringExtra("store_info_account")
            val bank = intent.getStringExtra("store_info_bank")
            val ownername = intent.getStringExtra("store_info_ownername")

            val storeName = findViewById<TextView>(R.id.store_txt_name)
            storeName.text = name.toString()

            val storeAccount = findViewById<TextView>(R.id.store_txt_account)
            storeAccount.text = account.toString()

            val storeBank = findViewById<TextView>(R.id.store_txt_bank)
            storeBank.text = bank.toString()

            val storeOwnername = findViewById<TextView>(R.id.store_txt_ownername)
            storeOwnername.text = ownername.toString()

            zzimbtn.setOnClickListener {

                if (isZzimSelected) {
                    //찜목록에서 삭제하는 코드 추가
                    zzimbtn.setImageResource(R.drawable.star)
                    Toast.makeText(this@StoreActivity, "찜 목록에서 제외되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    zzimbtn.setImageResource(R.drawable.zzim)
                    //storeActivity에서 별을 누르면 해당 가게 정보가 찜 목록에 바로 출력
                    //나중에 어댑터에 저장해두는걸로 바꿔야함
                    val storeinfointent = Intent(this@StoreActivity, ZzimAdapter::class.java)
                    storeinfointent.putExtra("store_info_name", "$name")
                    storeinfointent.putExtra("store_info_account", "$account")
                    storeinfointent.putExtra("store_info_bank", "$bank")
                    storeinfointent.putExtra("store_info_ownername", "$ownername")
                    //버튼을 눌렀다는 사실을 찜액티비티에 전달
                    storeinfointent.putExtra("store_like_clicked",isZzimSelected)
                    startActivity(storeinfointent)
                    Toast.makeText(this@StoreActivity, "찜 목록에 추가되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
                //버튼 선택여부 반전
                isZzimSelected = !isZzimSelected
            }


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
