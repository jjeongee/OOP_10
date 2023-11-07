package com.example.samdollar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.samdollar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        fun replaceFragment(frag: Fragment) {
            supportFragmentManager.beginTransaction().run {
                replace(binding.frmFrag.id, frag)
                commit()
            }
        }

        binding.run {
            btnMap.setOnClickListener {
                replace(MapFragment())
            }
        }

        setContentView(binding.root)

    }

}