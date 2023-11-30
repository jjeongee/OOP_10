package com.example.samdollarfront

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView

class OwnerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)
        val statustext = findViewById<TextView>(R.id.txt_status)
        val startButton = findViewById<Button>(R.id.btn_start)
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

<<<<<<< Updated upstream
=======
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
>>>>>>> Stashed changes

}