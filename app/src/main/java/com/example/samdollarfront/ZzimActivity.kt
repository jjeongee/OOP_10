package com.example.samdollarfront

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samdollarfront.R.id.rv_zzimrecycler
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ZzimActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var zzimAdapter: ZzimAdapter

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var zzimResult = ArrayList<Zzim>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zzim)

        //MainActivity에서 넘겨준 intent 데이터 받아오는 변수 생성
        val storename = intent.getStringExtra("name")?:""
        val storeaccount = intent.getStringExtra("account")?:""
        val storebank = intent.getStringExtra("bank")?:""
        val storeowner = intent.getStringExtra("ownername")?:""

        val zzimclass = Zzim(storename,storeaccount,storebank,storeowner)
        zzimResult.add(zzimclass)

        recyclerView = findViewById(R.id.rv_zzimrecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        zzimAdapter = ZzimAdapter(zzimResult)

        recyclerView.adapter = zzimAdapter //recyclerView에 어댑터 할당
        Log.w("찜목록추가완료",zzimResult[0].zzimstore)


        //main화면으로 되돌아오는 버튼
        val mainbutton = findViewById<ImageButton>(R.id.btn_main)

        mainbutton.setOnClickListener {
            finish()
        }
    }

    }





