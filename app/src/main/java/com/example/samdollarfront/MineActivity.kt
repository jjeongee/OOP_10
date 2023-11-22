package com.example.samdollarfront

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine)

        val ownerbutton = findViewById<ImageButton>(R.id.btn_ownermain)

        ownerbutton.setOnClickListener {
            finish()
        }
    }
}