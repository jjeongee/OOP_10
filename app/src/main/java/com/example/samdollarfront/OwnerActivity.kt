package com.example.samdollarfront

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button

class OwnerActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)

        val startButton = findViewById<Button>(R.id.btn_start)
        startButton.setOnClickListener {
            if ( startButton.text == "영업 시작")
                startButton.setText("영업 종료")
            else
                startButton.setText("영업 시작")
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


}