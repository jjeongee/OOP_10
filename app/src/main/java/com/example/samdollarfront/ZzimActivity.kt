package com.example.samdollarfront

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
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
    var zzimResult = ArrayList<Zzim>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zzim)

        recyclerView = findViewById(R.id.rv_zzimrecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        zzimAdapter = ZzimAdapter(zzimResult)
        fetchFromStore(zzimAdapter)
        fetchFromFirebase(zzimAdapter)
        zzimAdapter.notifyDataSetChanged()

        recyclerView.adapter = zzimAdapter

//        recyclerView.adapter = zzimAdapter //recyclerView에 어댑터 할당


        //main화면으로 되돌아오는 버튼
        val mainbutton = findViewById<ImageButton>(R.id.btn_main)

        mainbutton.setOnClickListener {
            finish()
        }
    }

    private fun fetchFromStore(zzimAdapter: ZzimAdapter) {
        //StoreActivity에서 넘겨준 intent 데이터 받아오는 변수 생성
        val storename = intent.getStringExtra("store_info_name")?:"찜한가게 이름"
        val storeaccount = intent.getStringExtra("store_info_account")?:"찜한가게 계좌"
        val storebank = intent.getStringExtra("store_info_bank")?:"찜한가게 은행"
        val storeowner = intent.getStringExtra("store_info_ownername")?:"찜한가게 사장"
        val zzimfromstore = Zzim(storename,storeaccount,storebank,storeowner)
        database.getReference("Zzim").child(storename).setValue(zzimfromstore)
    }

    private fun fetchFromFirebase(zzimAdapter: ZzimAdapter) {
        val zzimRef = database.getReference()
        zzimRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                zzimResult.clear()
                for(snapshot in datasnapshot.child("Zzim").children){
                    val zzimstorename = snapshot.child("zzimclassstore").getValue(String::class.java)?:""
                    val zzimstoreaccount = snapshot.child("zzimclassaccount").getValue(String::class.java)?:""
                    val zzimstorebank = snapshot.child("zzimclassbank").getValue(String::class.java)?:""
                    val zzimstoreowner = snapshot.child("zzimclassowner").getValue(String::class.java)?:""

                    val zzimclass = Zzim(zzimstorename, zzimstoreaccount,zzimstorebank,zzimstoreowner)
                    zzimResult.add(zzimclass)
                }
                zzimAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}





