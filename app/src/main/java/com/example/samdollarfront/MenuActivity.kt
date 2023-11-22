package com.example.samdollarfront

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val ownermainbutton = findViewById<ImageButton>(R.id.btn_ownermain)

        ownermainbutton.setOnClickListener {
            finish()
        }
    }

}