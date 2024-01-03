package com.sebastianlaos.ep2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sebastianlaos.ep2.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris
import org.json.JSONArray

class HamburguesaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leerservicio()
    }

    private fun leerservicio() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://laosdelgado.000webhostapp.com/serviciohamburguesas/hamburguesas.php"
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
            val idhamburguesa = jsonArray.getJSONObject(i).getString("idhamburguesa")
            val nomhamburguesa = jsonArray.getJSONObject(i).getString("nomhamburguesa")
            val imghamburguesa = jsonArray.getJSONObject(i).getString("imghamburguesa")
            val tamhamburguesa = jsonArray.getJSONObject(i).getString("tamhamburguesa")
            val precio = jsonArray.getJSONObject(i).getString("precio")
            val map = HashMap<String, String>();
            map.put("idhamburguesa", idhamburguesa)
            map.put("nomhamburguesa", nomhamburguesa)
            map.put("imghamburguesa", imghamburguesa)
            map.put("tamhamburguesa", tamhamburguesa)
            map.put("precio", precio)
            arrayList.add(map)
        }
        dibujar(arrayList)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                                        text = stringResource(id = R.string.title_activity_hamburguesa),
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
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 60.dp),
                                content = {
                                    items(arrayList.size) { pedidos ->
                                        Card(
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color.Black
                                            ),
                                            modifier = Modifier
                                                .padding(all = dimensionResource(id = R.dimen.espacio2))
                                                .height(300.dp)
                                                .shadow(
                                                    elevation = dimensionResource(id = R.dimen.espacio2)
                                                )
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .padding(all = dimensionResource(id = R.dimen.espacio1))
                                                    .fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Box {
                                                    AsyncImage(
                                                        model = "https://laosdelgado.000webhostapp.com/serviciohamburguesas/" + arrayList[pedidos].get(
                                                            "imghamburguesa"
                                                        ).toString(),
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(150.dp)
                                                    )
                                                }
                                                Text(
                                                    text = arrayList[pedidos].get("nomhamburguesa")
                                                        .toString(),
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = arrayList[pedidos].get("tamhamburguesa")
                                                        .toString(),
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = "S/ " + arrayList[pedidos].get("precio")!!
                                                        .toFloat(),
                                                    color = Color.Red
                                                )
                                            }
                                        }
                                    }
                                })
                        }
                    )
                }
            }
        }
    }
}
