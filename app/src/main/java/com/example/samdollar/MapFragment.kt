package com.example.samdollar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.daum.mf.map.api.MapView
import com.example.samdollar.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMapBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val mapView = MapView(context)
            binding.mapView.addView(mapView)

        return binding.root
    }
}