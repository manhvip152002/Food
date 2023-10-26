package com.example.food

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.rememberNavController
import com.example.food.navigation.NavGraph
import com.example.food.ui.theme.FoodTheme
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback

class MainActivity : ComponentActivity(), OnMapReadyCallback {
    private var mGoogleMap:GoogleMap?=null
//    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
//                    MapScreen()
                }
            }
        }
//        mapView = rememberMapViewWithLifecycle()
    }

    @Composable
    fun MapScreen() {
        val context = LocalContext.current

        val mapView = rememberMapViewWithLifecycle()
        mapView.getMapAsync { googleMap ->
            // Khởi tạo GoogleMap
            MapsInitializer.initialize(context)
            // Tùy chỉnh cài đặt GoogleMap ở đây (nếu cần)
        }

        // Chỉ định kích thước và vị trí cho MapView
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    fun rememberMapViewWithLifecycle(): MapView {
        val context = LocalContext.current
        return remember {
            MapView(context).apply {
                id = View.generateViewId() // Tạo ID duy nhất cho MapView
            }
        }
    }
//
//    override fun onResume() {
//        super.onResume()
//        mapView.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mapView.onPause()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        mapView.onSaveInstanceState(outState)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mapView.onDestroy()
//    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
    }
}




