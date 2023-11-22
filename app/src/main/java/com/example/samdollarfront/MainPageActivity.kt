package com.example.samdollarfront

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val buttontoOwner = findViewById<ImageButton>(R.id.btn_owner)
        buttontoOwner.setOnClickListener {
            val intent1 = Intent(this, OwnerActivity::class.java)
            startActivity(intent1)
        }

        val buttontoZzim = findViewById<ImageButton>(R.id.btn_zzim)
        buttontoZzim.setOnClickListener {
            val intent2 = Intent(this, ZzimActivity::class.java)
            startActivity(intent2)
        }
    }
}