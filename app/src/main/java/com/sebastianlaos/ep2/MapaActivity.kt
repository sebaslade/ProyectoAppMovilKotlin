package com.sebastianlaos.ep2

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.sebastianlaos.ep2.ui.theme.EP2Theme

class MapaActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitud = 0.0
    private var longitud = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        geolocalizar()
    }
    private fun geolocalizar() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            pedirPermisos()
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                Toast.makeText(this, "Localizó", Toast.LENGTH_SHORT).show()
                dibujarMapa(location)
            }
    }
    private fun pedirPermisos() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    geolocalizar()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    geolocalizar()
                }
                else -> {
                    dibujarMapa(null)
                }
            }
        }
        // ...
// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    fun dibujarMapa(location: Location?) {
        var latitudInicial = intent.getDoubleExtra("latitud", 0.0)
        var longitudInicial = intent.getDoubleExtra("longitud", 0.0)

        if(location !== null){
            latitudInicial = location.latitude
            longitudInicial = location.longitude
        }
        setContent {
            EP2Theme{
                //var latitud by remember { mutableStateOf(-12.125316628444946)}
                //var longitud by remember { mutableStateOf(-77.0248512662445)}
                var latitud by remember { mutableStateOf(latitudInicial) }
                var longitud by remember { mutableStateOf(longitudInicial) }
                val radioLugares = listOf("Speedy Express","United Packages", "Federal Shipping")
                val ubicacion = LatLng(latitud, longitud)

                var uiSettings by remember { mutableStateOf(MapUiSettings()) }

                val properties by remember {
                    mutableStateOf(
                        MapProperties(
                            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style),
                            mapType = MapType.NORMAL
                        )
                    )
                }

                val (selectedOption, onOptionSelected) = remember {
                    mutableStateOf(radioLugares[1])
                }
                Box (Modifier.fillMaxSize()){
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        properties = properties,
                        uiSettings = uiSettings,
                        cameraPositionState = CameraPositionState((CameraPosition.fromLatLngZoom(ubicacion, 19f)))

                    ) {
                        Marker(
                            state = MarkerState(position = ubicacion),
                        )
                        Circle(
                            center = ubicacion,
                            fillColor = Color(0xFFADD8E6),
                            radius = 100.0,
                        )
                    }
                    Switch(
                        checked = uiSettings.zoomControlsEnabled,
                        onCheckedChange = {
                            uiSettings = uiSettings.copy(zoomControlsEnabled = it)
                        }
                    )
                    Column (
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.End
                    ){
                        radioLugares.forEach {
                            Row (
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.width(200.dp)
                            ){
                                RadioButton(
                                    selected = (it == selectedOption),
                                    onClick = {
                                        onOptionSelected(it)
                                        when(it){
                                            "Speedy Express" ->{
                                                latitud = 43.64608130492877
                                                longitud = -79.39916429271426
                                            }
                                            "United Packages" ->{
                                                latitud = 46.97746670798121
                                                longitud = -123.80882691877218
                                            }
                                            "Federal Shipping" ->{
                                                latitud = 3.0074833553325475
                                                longitud = 101.38179169490756
                                            }
                                        }
                                    }
                                )
                                Text(text = it)
                            }
                        }
                    }
                }
            }
        }//setContent
    }
}
