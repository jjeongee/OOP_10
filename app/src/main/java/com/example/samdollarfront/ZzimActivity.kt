package com.example.samdollarfront

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ZzimActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zzim)

        val mainbutton = findViewById<ImageButton>(R.id.btn_main)

        mainbutton.setOnClickListener {
            finish()
        }
    }


}