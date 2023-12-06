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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ZzimActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var zzimAdapter: ZzimAdapter
    val zzimRef = FirebaseDatabase.getInstance().getReference("Zzim")
//    val storeRef = FirebaseDatabase.getInstance().getReference("Store")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zzim)

        recyclerView = findViewById(R.id.rv_zzimrecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //recyclerView를 통해서 찜목록 불러와서 출력해주기
        val zzimResult = ArrayList<Zzim>()
        zzimAdapter = ZzimAdapter(zzimResult)

        //StoreActivity에서 넘겨준 intent 데이터 받아오는 변수 생성
        val storename = intent.getStringExtra("store_info_name")?:"찜한가게 이름"
        val storeaccount = intent.getStringExtra("store_info_account")?:"찜한가게 계좌"
        val storebank = intent.getStringExtra("store_info_bank")?:"찜한가게 은행"
        val storeowner = intent.getStringExtra("store_info_ownername")?:"찜한가게 사장"
        var btnselected = intent.getBooleanExtra("store_like_clicked",false)

        val zzimclass = Zzim(storename,storeaccount,storebank,storeowner)
        zzimResult.add(zzimclass)
        zzimRef.child(storename).setValue(zzimclass)
        //별표 버튼을 누르면 해당 값을 firebase realtime database에 추가하는 코드
        if(btnselected==true){
            zzimRef.child(storename).removeValue()
        }
        //버튼 여부를 알려주는 로그
        Log.d("버튼","$btnselected")

        recyclerView.adapter = zzimAdapter //recyclerView에 어댑터 할당
        //main화면으로 되돌아오는 버튼
        val mainbutton = findViewById<ImageButton>(R.id.btn_main)
        mainbutton.setOnClickListener {
            finish()
        }
    }
    //찜한 가게의 객체를 가게 이름으로 바꾸기
//        zzimKeyName = zzimclass.zzimclassstore
//        Log.d("변경된키값","$zzimKeyName")

    }





