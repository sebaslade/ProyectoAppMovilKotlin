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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sebastianlaos.ep2.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris
import com.sebastianlaos.ep2.utils.Total
import org.json.JSONArray

class DetalleEnviosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val idempresaenvio = bundle!!.getString("idempresaenvio")
        leerServicio(idempresaenvio)
    }
    private fun leerServicio(idempresaenvio: String?) {
        val queue = Volley.newRequestQueue(this)
        val url = Total.RutaURL + "pedidosenvio.php?idempresaenvio=" + idempresaenvio
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
    }//leerServicio

    private fun llenarLista(response: String?) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()
        for (i in 0 until jsonArray.length()) {
            val idpedido = jsonArray.getJSONObject(i).getString("idpedido")
            val idempleado = jsonArray.getJSONObject(i).getString("idempleado")
            val fechapedido = jsonArray.getJSONObject(i).getString("fechapedido")
            val fechaentrega = jsonArray.getJSONObject(i).getString("fechaentrega")
            val fechaenvio = jsonArray.getJSONObject(i).getString("fechaenvio")
            val idempresaenvio = jsonArray.getJSONObject(i).getString("idempresaenvio")
            val cargo = jsonArray.getJSONObject(i).getString("cargo")
            val destinatario = jsonArray.getJSONObject(i).getString("destinatario")
            val direcciondestinatario = jsonArray.getJSONObject(i).getString("direcciondestinatario")
            val ciudaddestinatario = jsonArray.getJSONObject(i).getString("ciudaddestinatario")
            val regiondestinatario = jsonArray.getJSONObject(i).getString("regiondestinatario")
            val codigopostaldestinatario = jsonArray.getJSONObject(i).getString("codigopostaldestinatario")
            val paisdestinatario = jsonArray.getJSONObject(i).getString("paisdestinatario")
            val idcliente = jsonArray.getJSONObject(i).getString("idcliente")
            val empleado = jsonArray.getJSONObject(i).getString("empleado")
            val cliente = jsonArray.getJSONObject(i).getString("cliente")
            val contacto = jsonArray.getJSONObject(i).getString("contacto")
            val map = HashMap<String, String>();
            map.put("idpedido", idpedido)
            map.put("idempleado", idempleado)
            map.put("fechapedido", fechapedido)
            map.put("fechaentrega", fechaentrega)
            map.put("fechaenvio", fechaenvio)
            map.put("idempresaenvio", idempresaenvio)
            map.put("cargo", cargo)
            map.put("destinatario", destinatario)
            map.put("direcciondestinatario", direcciondestinatario)
            map.put("ciudaddestinatario", ciudaddestinatario)
            map.put("regiondestinatario", regiondestinatario)
            map.put("codigopostaldestinatario", codigopostaldestinatario)
            map.put("paisdestinatario", paisdestinatario)
            map.put("idcliente", idcliente)
            map.put("empleado", empleado)
            map.put("idempresaenvio", idempresaenvio)
            map.put("cliente", cliente)
            map.put("contacto", contacto)
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
                                    items(items = arrayList, itemContent = { empresa ->
                                        Card(
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color(0, 0, 0)
                                            ),
                                            modifier = Modifier
                                                .padding(all = dimensionResource(id = R.dimen.espacio2))
                                                .fillMaxWidth()
                                                .clickable {
                                                    seleccionarProducto(empresa.get("idpedido").toString())
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
                                                    text = "Destinatario: " + empresa["destinatario"].toString(),
                                                    style = MaterialTheme.typography.headlineSmall,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = "Cliente: " + empresa["cliente"].toString(),
                                                    style = MaterialTheme.typography.titleLarge,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = "Contacto: " + empresa["contacto"].toString(),
                                                    style = MaterialTheme.typography.titleLarge,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = "Fecha de envio: " + empresa["fechaenvio"].toString(),
                                                    style = MaterialTheme.typography.titleLarge,
                                                    color = Color.Red
                                                )
                                                Text(
                                                    text = "Fecha de entrega: " + empresa["fechaentrega"].toString(),
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

    private fun seleccionarProducto(idpedido: String) {
        val bundle = Bundle().apply {
            putString("idpedido", idpedido)
        }
        val intent = Intent(this, DetallePedidoActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}