package com.sebastianlaos.ep2

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
import com.sebastianlaos.ep2.datos.DatosCaja
import com.sebastianlaos.ep2.datos.DatosTarea
import com.sebastianlaos.ep2.pages.CajaInsertActivity
import com.sebastianlaos.ep2.ui.theme.EP2Theme

class TareasActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leerDatos()
    }
    private fun leerDatos() {
        val arrayList = ArrayList<HashMap<String, String>>()
        val datosTarea = DatosTarea(this)
        val cursor: Cursor = datosTarea.tareasSelect(datosTarea)
        if (cursor.moveToFirst()) {
            do {
                val idtarea = cursor.getString(cursor.getColumnIndexOrThrow("idtarea"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))
                val prioridad = cursor.getString(cursor.getColumnIndexOrThrow("prioridad"))
                val estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"))
                val map = HashMap<String, String>();
                map.put("idtarea", idtarea)
                map.put("fecha", fecha)
                map.put("titulo", titulo)
                map.put("descripcion", descripcion)
                map.put("categoria", categoria)
                map.put("prioridad", prioridad)
                map.put("estado", estado)
                arrayList.add(map)
            } while (cursor.moveToNext())
            dibujar(arrayList)
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
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
                                        text = stringResource(id = R.string.title_activity_tareas),
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
                                                    Text(text = it.get("idtarea").toString())
                                                    Text(text = it.get("fecha").toString())
                                                    Text(text = it.get("titulo").toString())
                                                    Text(text = it.get("descripcion").toString())
                                                    Text(text = it.get("categoria").toString())
                                                    Text(text = it.get("prioridad").toString())
                                                    Text(text = it.get("estado").toString())
                                                }
                                            })//items
                                        })
                                }
                                FloatingActionButton(
                                    onClick = {
                                        startActivity(
                                            Intent(
                                                this@TareasActivity,
                                                TareasInsertActivity::class.java
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
                        }
                    )
                }
            }
        }
    }
}
