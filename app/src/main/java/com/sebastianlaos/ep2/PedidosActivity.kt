package com.sebastianlaos.ep2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sebastianlaos.ep2.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris
import org.json.JSONArray

class PedidosActivity() : ComponentActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leerServicio()
    }
    private fun leerServicio() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/pedidos.php"
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
    }//Leer Servicio

    private fun llenarLista(response: String?) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()
        for (i in 0 until jsonArray.length()) {
            val idpedido = jsonArray.getJSONObject(i).getString("idpedido")
            val nombres = jsonArray.getJSONObject(i).getString("nombres")
            val fechapedido = jsonArray.getJSONObject(i).getString("fechapedido")
            val usuario = jsonArray.getJSONObject(i).getString("usuario")
            val total = jsonArray.getJSONObject(i).getString("total")
            val map = HashMap<String, String>();
            map.put("idpedido", idpedido)
            map.put("nombres", nombres)
            map.put("fechapedido", fechapedido)
            map.put("usuario", usuario)
            map.put("total", total)
            arrayList.add(map)
        }
        dibujar(arrayList)
    }//Llenar Lista

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
                                        text = stringResource(id = R.string.pedidos),
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
                                    items(items = arrayList, itemContent = { pedido ->
                                        Card(
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color(0, 0, 0)
                                            ),
                                            modifier = Modifier
                                                .padding(all = dimensionResource(id = R.dimen.espacio2))
                                                .fillMaxWidth()
                                                .clickable {
                                                    seleccionarPedido(pedido)
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
                                                    text = pedido["nombres"].toString(),
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = pedido["usuario"].toString(),
                                                    style = MaterialTheme.typography.titleLarge,
                                                    color = Color.Red
                                                )
                                                Text(
                                                    text = "Fecha: " + pedido["fechapedido"].toString(),
                                                    style = MaterialTheme.typography.titleSmall,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = stringResource(id = R.string.total) + String.format(
                                                        "%.2f",
                                                        pedido["total"]!!.toFloat()
                                                    ),
                                                    color = Color.White
                                                )
                                            }//Column
                                        }//Card
                                    })//items
                                }
                            )//LazyColumn
                        })
                    }
                }
            }
        }

    private fun seleccionarPedido(pedido: HashMap<String, String>) {
        val idpedido = pedido["idpedido"]
        val nombres = pedido["nombres"]
        val usuario = pedido["usuario"]
        val fechapedido = pedido["fechapedido"]
        val total = pedido["total"]

        val bundle = Bundle().apply {
            putString("idpedido", idpedido)
            putString("nombres", nombres)
            putString("usuario", usuario)
            putString("fechapedido", fechapedido)
            putString("total", total)
        }
        val intent = Intent(this, PedidosDetallesActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PedidosActivity> {
        override fun createFromParcel(parcel: Parcel): PedidosActivity {
            return PedidosActivity(parcel)
        }

        override fun newArray(size: Int): Array<PedidosActivity?> {
            return arrayOfNulls(size)
        }
    }
}


