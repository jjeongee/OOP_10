package com.example.samdollarfront

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class StoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val mainbutton = findViewById<ImageButton>(R.id.btn_main2)

        mainbutton.setOnClickListener {
            finish()
        }
    }
}