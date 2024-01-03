package com.sebastianlaos.ep2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sebastianlaos.ep2.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris
import com.sebastianlaos.ep2.utils.Total
import org.json.JSONArray

class DetallePedidoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val idpedido = bundle!!.getString("idpedido")
        leerServicio(idpedido)
    }

    private fun leerServicio(idpedido: String?) {
        val queue = Volley.newRequestQueue(this)
        val url = Total.RutaURL + "pedidosdetalle.php?idpedido=" + idpedido
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("DATOS", response)
                //dibujar(response)
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
            val idpedido = jsonArray.getJSONObject(i).getString("idpedido")
            val idproducto = jsonArray.getJSONObject(i).getString("idproducto")
            val precio = jsonArray.getJSONObject(i).getString("precio")
            val cantidad = jsonArray.getJSONObject(i).getString("cantidad")
            val nombre = jsonArray.getJSONObject(i).getString("nombre")
            val detalle = jsonArray.getJSONObject(i).getString("detalle")
            val imagenchica = jsonArray.getJSONObject(i).getString("imagenchica")
            val map = HashMap<String, String>();
            map.put("idpedido", idpedido)
            map.put("idproducto", idproducto)
            map.put("precio", precio)
            map.put("cantidad", cantidad)
            map.put("nombre", nombre)
            map.put("detalle", detalle)
            map.put("imagenchica", imagenchica)
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
                                        text = stringResource(id = R.string.pedidosdetalle),
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
                                columns = GridCells.Fixed(3),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 60.dp),
                                content = {
                                    items(arrayList.size) { pedidos ->
                                        val precio = arrayList[pedidos].get("precio")!!.toFloat()
                                        Card(
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color.Black
                                            ),
                                            modifier = Modifier
                                                .padding(all = dimensionResource(id = R.dimen.espacio2))
                                                .height(220.dp)
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
                                                        model = Total.RutaURL + arrayList[pedidos].get(
                                                            "imagenchica"
                                                        ).toString(),
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(50.dp)
                                                    )
                                                }
                                                Text(
                                                    text = arrayList[pedidos].get("nombre")
                                                        .toString(),
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White,
                                                    style = MaterialTheme.typography.labelSmall
                                                )
                                                Text(
                                                    text = "Cantidad: " + arrayList[pedidos].get("cantidad")
                                                        .toString(),
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White,
                                                    style = MaterialTheme.typography.labelSmall
                                                )
                                                Text(
                                                    text = "S/ " + String.format("%.2f", precio),
                                                    color = Color.Red,
                                                    style = MaterialTheme.typography.labelSmall
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}