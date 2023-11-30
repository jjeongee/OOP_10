package com.example.samdollarfront

import android.content.Intent
import android.database.DatabaseErrorHandler
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OwnerActivity : AppCompatActivity() {
    //Deposit Activity에서 만든 RecyclerView 초기화 및 연결
    private lateinit var recyclerView: RecyclerView
    private lateinit var depositAdapter: DepositAdapter

    //Firebase의 realtime database와 연결해주는 database 변수 생성
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    //Deposit class의 객체를 ArrayList형태로 firebase에 저장, 불러오는 변수

    val depositResult = ArrayList<Deposit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)
        //recyclerView와 xml의 RecyclerView 설정 연결
        recyclerView = findViewById(R.id.rv_deposit)
        recyclerView.layoutManager= LinearLayoutManager(this)
        val statustext = findViewById<TextView>(R.id.txt_status)
        val startButton = findViewById<Button>(R.id.btn_start)

        //Adapter에 depositResult출력
        depositAdapter= DepositAdapter(depositResult)
        depositAdapter.notifyDataSetChanged()//데이터값이 바뀌면 알려주고 업데이트

        recyclerView.adapter=depositAdapter //recyclerView에 어댑터 할당

        fetchDataFromFirebase(depositAdapter)

        //영업 여부 알려주는 버튼에 기능 부여
        startButton.setOnClickListener {
            if ( startButton.text == "영업 시작") {
                startButton.setText("영업 종료")
                statustext.setText("영업 중")
                statustext.setTextColor(Color.parseColor("#FF0000"))
            }
            else {
                startButton.setText("영업 시작")
                statustext.setText("영업 종료")
                statustext.setTextColor(Color.parseColor("#000000"))
            }
        }
        val buttontoMenu = findViewById<ImageButton>(R.id.btn_menu)
        buttontoMenu.setOnClickListener {
            val intent1 = Intent(this, MenuActivity::class.java)
            startActivity(intent1)
        }

        val buttontoMine = findViewById<ImageButton>(R.id.btn_mine)
        buttontoMine.setOnClickListener {
            val intent2 = Intent(this, MineActivity::class.java)
            startActivity(intent2)
        }

    }

    private fun fetchDataFromFirebase(depositAdapter: DepositAdapter) {
        //Database에서 값을 읽어오기
        val depositRef = database.getReference()
        depositRef.addListenerForSingleValueEvent(object : ValueEventListener {
            //data를 받아오는데 성공한 경우
            override fun onDataChange(datasnapshot: DataSnapshot){
                depositResult.clear()
                //snapshot을 이용해서 datasnapshot의 children 객체를 가져옴
                for (snapshot in datasnapshot.children){
                    val username=snapshot.child("username").getValue(String::class.java)?:""
                    val bankname=snapshot.child("bankname").getValue(String::class.java)?:""
                    val inputmoney=snapshot.child("inputmoney").getValue(Int::class.java)?:0


                    //deposit class를 생성해서 받아옴
                    val deposit= Deposit(username, bankname, inputmoney)
                    depositResult.add(deposit)
                }
                depositAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}