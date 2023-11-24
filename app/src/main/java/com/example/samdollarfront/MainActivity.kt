package com.example.samdollarfront

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import android.Manifest
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.samdollarfront.databinding.ActivityMainBinding
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

        val buttontoGps = findViewById<ImageButton>(R.id.btn_gps)
        buttontoGps.setOnClickListener {

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


        val zoomLevel = 17.0f
        // Add a marker in Sydney and move the camera
        val boong = LatLng(37.602614, 126.869500)
        val startlocation = LatLng(37.5989732, 126.8640908)
        mMap.addMarker(MarkerOptions().position(boong).title("화전역 앞 붕어빵"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startlocation, zoomLevel))

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

                val tostore = findViewById<CardView>(R.id.card_view)
                tostore.setOnClickListener {
                    val intent3 = Intent(this@MainActivity, StoreActivity::class.java)
                    startActivity(intent3)
                }

                val copy = findViewById<ImageButton>(R.id.btn_copy)
                copy.setOnClickListener {
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("계좌번호", owneracc.text.toString())
                    clipboard.setPrimaryClip(clip)

                    Toast.makeText(this@MainActivity, "계좌번호가 클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
                }

                val zzim = findViewById<ImageButton>(R.id.zzimButton)
                zzim.setImageResource(R.drawable.star)
                var isZzimSelected = false

                zzim.setOnClickListener {
                    if (isZzimSelected) {
                        zzim.setImageResource(R.drawable.star)
                        Toast.makeText(this@MainActivity, "찜 목록에서 제외되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        zzim.setImageResource(R.drawable.zzim)
                        Toast.makeText(this@MainActivity, "찜 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    }

                    isZzimSelected = !isZzimSelected
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
