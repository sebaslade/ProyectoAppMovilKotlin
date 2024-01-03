package com.sebastianlaos.ep2.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sebastianlaos.ep2.R
import com.sebastianlaos.ep2.datos.DatosCaja
import com.sebastianlaos.ep2.pages.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris

class CajaPrincipalActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leerDatos()
    }

    private fun leerDatos() {
        val arrayList = ArrayList<HashMap<String, String>>()
        val datosCaja = DatosCaja(this)
        val cursor: Cursor = datosCaja.movimientosSelect(datosCaja)
        if (cursor.moveToFirst()) {
            do {
                val idmovimiento = cursor.getString(cursor.getColumnIndexOrThrow("idmovimiento"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val monto = cursor.getString(cursor.getColumnIndexOrThrow("monto"))
                val tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                val map = HashMap<String, String>();
                map.put("idmovimiento", idmovimiento)
                map.put("fecha", fecha)
                map.put("descripcion", descripcion)
                map.put("monto", monto)
                map.put("tipo", tipo)
                arrayList.add(map)
            } while (cursor.moveToNext())
            dibujar(arrayList)
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        val datosCaja = DatosCaja(this)
        val cursor: Cursor = datosCaja.movimientosTotal(datosCaja)
        val totalIngresos = datosCaja.totalIngresos(datosCaja)
        val totalGastos = datosCaja.totalGastos(datosCaja)
        setContent {
            EP2Theme {
                Surface(
                    color = MaterialTheme.colorScheme.background, // Establece el color de fondo en gris
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.title_activity_caja),
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
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Column(modifier = Modifier.padding(top = 60.dp)) {
                                    if (cursor.moveToFirst()) {
                                        val totalGeneral = totalIngresos - totalGastos
                                        Text(
                                            text = "Total: S/%.2f".format(totalGeneral),
                                            style = TextStyle(
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold,
                                            ),
                                            modifier = Modifier.padding(top = 8.dp).padding(start = 16.dp)
                                        )

                                        // Caja de texto para mostrar el total de ingresos
                                        Text(
                                            text = "Ingresos: S/%.2f".format(totalIngresos),
                                            style = TextStyle(
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Blue // Color azul para ingresos
                                            ),
                                            modifier = Modifier.padding(top = 8.dp).padding(start = 16.dp)
                                        )

                                        // Caja de texto para mostrar el total de gastos
                                        Text(
                                            text = "Gastos: S/%.2f".format(totalGastos),
                                            style = TextStyle(
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Red // Color rojo para gastos
                                            ),
                                            modifier = Modifier.padding(top = 8.dp).padding(start = 16.dp)
                                        )
                                    }

                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        content = {
                                            items(items = arrayList, itemContent = {
                                                Column(
                                                    modifier = Modifier
                                                        .padding(all = dimensionResource(id = R.dimen.espacio2))
                                                        .border(
                                                            width = 1.dp,
                                                            color = Color.Gray,
                                                            shape = RectangleShape
                                                        )
                                                        .padding(all = dimensionResource(id = R.dimen.espacio))
                                                        .fillMaxWidth()
                                                ) {
                                                    Text(text = it.get("idmovimiento").toString())
                                                    Text(text = it.get("fecha").toString())
                                                    Text(text = it.get("descripcion").toString())
                                                    Text(
                                                        text = "S/" + it.get("monto")
                                                            .toString() + ".00"
                                                    )
                                                    Text(text = it.get("tipo").toString())
                                                }
                                            })//items
                                        })
                                }
                                FloatingActionButton(
                                    onClick = {
                                        startActivity(
                                            Intent(
                                                this@CajaPrincipalActivity,
                                                CajaInsertActivity::class.java
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .padding(all = 20.dp)
                                        .align(Alignment.BottomEnd),
                                ) {
                                    Icon(
                                        Icons.Filled.Add,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier
                                            .background(Color.Red)
                                            .size(56.dp)
                                    )
                                }
                            }
                        })
                }
            }
        }
    }
}