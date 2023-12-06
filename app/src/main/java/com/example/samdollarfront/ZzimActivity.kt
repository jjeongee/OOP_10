package com.example.samdollarfront

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ZzimActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var zzimAdapter: ZzimAdapter

    val zzimRef = FirebaseDatabase.getInstance().getReference("Zzim")
    var zzimResult = ArrayList<Zzim>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zzim)

        recyclerView = findViewById(R.id.rv_zzimrecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        zzimAdapter = ZzimAdapter(zzimResult)

        //StoreActivity에서 넘겨준 intent 데이터 받아오는 변수 생성
        val storename = intent.getStringExtra("store_info_name")?:"찜한가게 이름"
        val storeaccount = intent.getStringExtra("store_info_account")?:"찜한가게 계좌"
        val storebank = intent.getStringExtra("store_info_bank")?:"찜한가게 은행"
        val storeowner = intent.getStringExtra("store_info_ownername")?:"찜한가게 사장"

        val zzimclass = Zzim(storename,storeaccount,storebank,storeowner)
        zzimResult.add(zzimclass)
        zzimRef.child(storename).setValue(zzimclass)
        recyclerView.adapter = zzimAdapter //recyclerView에 어댑터 할당


        //main화면으로 되돌아오는 버튼
        val mainbutton = findViewById<ImageButton>(R.id.btn_main)

        mainbutton.setOnClickListener {
            finish()
        }
    }

    }





