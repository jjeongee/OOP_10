package com.example.samdollarfront

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView

import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.samdollarfront.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.Marker

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val boong = LatLng(37.602614, 126.869500)
        mMap.addMarker(MarkerOptions().position(boong).title("화전역 앞 붕어빵"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(boong))

        val cardView = findViewById<CardView>(R.id.card_view)

        googleMap!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {

                cardView.visibility = View.VISIBLE
                var storename = findViewById<TextView>(R.id.store_name)
                var ownerbank = findViewById<TextView>(R.id.owner_bank)
                var owneracc = findViewById<TextView>(R.id.owner_account)
                var ownername = findViewById<TextView>(R.id.owner_name)
                var storestatus = findViewById<TextView>(R.id.store_status)
                storename.text = "화전역 앞 붕어빵"
                ownerbank.text = "카카오뱅크"
                owneracc.text = "3333-20-1234"
                ownername.text = "민초붕"
                storestatus.text = "영업 중"

                val copy = findViewById<ImageButton>(R.id.btn_copy)
                copy.setOnClickListener {
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("계좌번호", owneracc.text.toString())
                    clipboard.setPrimaryClip(clip)

                    Toast.makeText(this@MainActivity, "계좌번호가 클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
                }
                return false
            }
        })
        googleMap!!.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(latLng: LatLng) {
                cardView.visibility = View.GONE
            }
        })
    }

}