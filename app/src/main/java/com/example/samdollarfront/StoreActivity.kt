package com.example.samdollarfront

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class StoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val mainbutton = findViewById<ImageButton>(R.id.btn_main2)

        mainbutton.setOnClickListener {
            finish()
        }

        var name = intent.getStringExtra("name")
        var account = intent.getStringExtra("account")
        var bank = intent.getStringExtra("bank")
        var ownername = intent.getStringExtra("ownername")

        val storeName = findViewById<TextView>(R.id.store_txt_name)
        storeName.text = name.toString()

        val storeAccount = findViewById<TextView>(R.id.store_txt_account)
        storeAccount.text = account.toString()

        val storeBank = findViewById<TextView>(R.id.store_txt_bank)
        storeBank.text = bank.toString()

        val storeOwnername = findViewById<TextView>(R.id.store_txt_ownername)
        storeOwnername.text = ownername.toString()

        val copy = findViewById<ImageButton>(R.id.btn_copy_store)
        copy.setOnClickListener {
            val clipboard =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("계좌번호", storeAccount.text.toString())
            clipboard.setPrimaryClip(clip)

            Toast.makeText(
                this@StoreActivity,
                "계좌번호가 클립보드에 복사되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}