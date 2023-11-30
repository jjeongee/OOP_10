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
import android.content.pm.PackageManager.PERMISSION_GRANTED
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    val PERM_FLAG = 99

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
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            setupdateLocationListener()
        }

        if (isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERM_FLAG)
        }

    }

    fun isPermitted() : Boolean {
        for(perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun startProcess() {
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
                if (marker.title == "내 위치") {
                    cardView.visibility = View.GONE
                } else {
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

    // -- 내 위치를 가져오는 코드
    lateinit var fusedLocationClient:FusedLocationProviderClient
    lateinit var locationCallback:LocationCallback

    // 좌표계를 주기적으로 갱신해주는 리스너
    @SuppressLint("MissingPermission")  // 문법 검사기
    fun setupdateLocationListener() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            //interval = 10000
            // gps와 네트워크를 다 사용해서 10초에 한번씩 좌표값을 가져옴
        }

        locationCallback = object : LocationCallback () {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    for ((i, location) in it.locations.withIndex()) {  // 튜플로 사용
                        Log.d("로케이션", "$i ${location.latitude}, ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }

        // location 요청 함수 호출 (locationRequest, locationCallback)
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    fun setLastLocation(location: Location) {
        val myLocation = LatLng(location.latitude, location.longitude)
        val marker = MarkerOptions()
            .position(myLocation)
            .title("내 위치")
        val cameraOption = CameraPosition.Builder()
            .target(myLocation)
            .zoom(17f)
            .build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)

        //mMap.clear()
        mMap.addMarker(marker)
        mMap.moveCamera(camera)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERM_FLAG -> {
                var check = true
                for (grant in grantResults) {
                    if(grant != PERMISSION_GRANTED) {
                        check = false
                        break
                    }
                }
                if (check) {
                    startProcess()
                } else {
                    Toast.makeText(this@MainActivity, "권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG)
                    finish()
                }
            }
        }
    }
}
