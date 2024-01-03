package com.sebastianlaos.ep2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sebastianlaos.ep2.pages.CajaInsertActivity
import com.sebastianlaos.ep2.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris
import com.sebastianlaos.ep2.utils.Total
import org.json.JSONArray

class EnviosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leerServicio()
    }

    private fun leerServicio() {
        val queue = Volley.newRequestQueue(this)
        val url = Total.RutaURL + "envios.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("DATOS", response)
                llenarLista(response)
            },
            {
                Log.d("DATOSERROR", it.message.toString())
            })
        queue.add(stringRequest)
    }

    private fun llenarLista(response: String?) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()
        for (i in 0 until jsonArray.length()) {
            val idempresaenvio = jsonArray.getJSONObject(i).getString("idempresaenvio")
            val nombre = jsonArray.getJSONObject(i).getString("nombre")
            val telefono = jsonArray.getJSONObject(i).getString("telefono")
            val latitud = jsonArray.getJSONObject(i).getString("latitud")
            val longitud = jsonArray.getJSONObject(i).getString("longitud")
            val map = HashMap<String, String>();
            map.put("idempresaenvio", idempresaenvio)
            map.put("nombre", nombre)
            map.put("telefono", telefono)
            map.put("latitud", latitud)
            map.put("longitud", longitud)
            arrayList.add(map)
        }
        dibujar(arrayList)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            EP2Theme {
                Surface(
                    color = Gris, // Establece el color de fondo en gris
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.title_activity_caja_envios),
                                        color = Color.Black
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        finish()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            tint = Color.Black,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        }, content = {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 60.dp),
                                content = {
                                    items(items = arrayList, itemContent = { envio ->
                                        Card(
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color(0, 0, 0)
                                            ),
                                            modifier = Modifier
                                                .padding(all = dimensionResource(id = R.dimen.espacio2))
                                                .fillMaxWidth()
                                                .clickable {
                                                    startActivity(Intent(this@EnviosActivity, MapaActivity::class.java).apply {
                                                        putExtra("latitud", envio["latitud"].toString().toDouble())
                                                        putExtra("longitud", envio["longitud"].toString().toDouble())
                                                    })
                                                }
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(
                                                    all = dimensionResource(
                                                        id = R.dimen.espacio
                                                    )
                                                )
                                            ) {
                                                Text(
                                                    text = envio["nombre"].toString(),
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = envio["telefono"].toString(),
                                                    style = MaterialTheme.typography.titleLarge,
                                                    color = Color.Red
                                                )
                                            }//Column
                                        }//Card
                                    })//items
                                }
                            )//LazyColumn
                        }
                    )
                }
            }
        }
    }
}