package com.example.samdollarfront

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class ZzimActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var zzimAdapter: ZzimAdapter
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()

    val zzimRef = FirebaseDatabase.getInstance().getReference("Zzim")
    var zzimResult = ArrayList<Zzim>()
    //StoreActivity에서 넘겨준 intent 데이터 받아오는 변수 생성
    val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val storename = result.data?.getStringExtra("store_info_name")?:"찜한가게 이름"
            val storeaccount = intent.getStringExtra("store_info_account")?:"찜한가게 계좌"
            val storebank = intent.getStringExtra("store_info_bank")?:"찜한가게 은행"
            val storeowner = intent.getStringExtra("store_info_ownername")?:"찜한가게 사장"

            Toast.makeText(this@ZzimActivity, "찜목록에 저장됨", Toast.LENGTH_SHORT).show()

        }
    }
//    fun updateStore(){
//        val storename = intent.getStringExtra("store_info_name")?:"찜한가게 이름"
//        val storeaccount = intent.getStringExtra("store_info_account")?:"찜한가게 계좌"
//        val storebank = intent.getStringExtra("store_info_bank")?:"찜한가게 은행"
//        val storeowner = intent.getStringExtra("store_info_ownername")?:"찜한가게 사장"
//
//        val zzimclass = Zzim(storename,storeaccount,storebank,storeowner)
//        zzimRef.child(storename).setValue(zzimclass)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zzim)
        recyclerView = findViewById(R.id.rv_zzimrecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        zzimAdapter = ZzimAdapter(zzimResult)
        fetchDataFromFirebase(zzimAdapter)
//        updateStore()
//        recyclerView.adapter = zzimAdapter //recyclerView에 어댑터 할당
        //main화면으로 되돌아오는 버튼
        val mainbutton = findViewById<ImageButton>(R.id.btn_main)
        mainbutton.setOnClickListener {
            finish()
        }
    }
    private fun fetchDataFromFirebase(zzimAdapter: ZzimAdapter) {
        val readzzimRef = database.getReference()
        readzzimRef.addListenerForSingleValueEvent(object : ValueEventListener{
            //data를 받아오는데 성공한 경우
            override fun onDataChange(datasnapshot: DataSnapshot){
                zzimResult.clear()
                //snapshot을 이용해서 datasnapshot의 children 객체를 가져옴
                for (snapshot in datasnapshot.child("Zzim").children){
                    zzimAdapter.notifyDataSetChanged()
                    val storeName=snapshot.child("zzimclassstore").getValue(String::class.java)?:""
                    val storeBank=snapshot.child("zzimclassbank").getValue(String::class.java)?:""
                    val storeAccount=snapshot.child("zzimclassaccount").getValue(String::class.java)?:""
                    val storeOwner = snapshot.child("zzimclassowner").getValue(String::class.java)?:""

                    //Zzim class를 생성해서 받아옴
                    val zzim= Zzim(storeName,storeBank,storeAccount,storeOwner)
                    zzimResult.add(zzim)

                }

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        recyclerView.adapter = zzimAdapter //recyclerView에 어댑터 할당
    }

    }





