package com.sebastianlaos.ep2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sebastianlaos.ep2.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris
import org.json.JSONArray

class PedidosDetallesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras //Para recuperar los datos que recibe este
        //activity con la clase Bundle
        val idpedido = bundle!!.getString("idpedido")
        //this.nombre = bundle.getString("nombre").toString()
        //this.detallle = bundle.getString("detallle").toString()
        Log.d("DATOS", idpedido.toString())
        leerServicio(idpedido);
    }

    private fun leerServicio(idpedido: String?) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/pedidosdetalle.php?idpedido=" + idpedido
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
    }//leerServicio

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
    }//llenarLista

    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            EP2Theme {
                Surface(
                    color = Gris, // Establece el color de fondo en gris
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column {
                        Text(
                            text = stringResource(id = R.string.pedidosdetalle),
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.Black,
                            modifier = Modifier.offset(x = 16.dp)
                        )
                        LazyRow{
                            items(arrayList.size) { posicion ->
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Black
                                    ),
                                    modifier = Modifier
                                        .padding(all = dimensionResource(id = R.dimen.espacio2))
                                        .height(600.dp)
                                        .width(350.dp)
                                        .shadow(
                                            elevation = dimensionResource(id = R.dimen.espacio2)
                                        )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box {
                                            if(arrayList[posicion].get("imagenchica").toString() == "null"){
                                                Image(painter = painterResource(id = R.drawable.nofoto), contentDescription = null,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(400.dp))
                                            }else {
                                                AsyncImage(
                                                    model = "https://servicios.campus.pe/" + arrayList[posicion].get(
                                                        "imagenchica"
                                                    ).toString(),
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(400.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "Pedido: " + arrayList[posicion].get("idpedido").toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.Red
                                        )
                                        Text(
                                            text = arrayList[posicion].get("nombre").toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "Precio: " + arrayList[posicion].get("precio").toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "Cantidad: " + arrayList[posicion].get("cantidad").toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "Detalle: " + arrayList[posicion].get("detalle").toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                }
            }
        }
    }
}}