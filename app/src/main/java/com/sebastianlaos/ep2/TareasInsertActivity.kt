package com.sebastianlaos.ep2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sebastianlaos.ep2.datos.DatosTarea
import com.sebastianlaos.ep2.pages.CajaPrincipalActivity
import com.sebastianlaos.ep2.pages.ui.theme.Gris

class TareasInsertActivity : ComponentActivity() {
    var estadoChecked: Boolean = false
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var titulo by remember { mutableStateOf("") }
            var descripcion by remember { mutableStateOf("") }
            var categoria by remember { mutableStateOf("") }
            var prioridad by remember { mutableStateOf("") }
            var estadoTarea by remember { mutableStateOf(TareaEstado.INGRESO) }
            com.sebastianlaos.ep2.pages.ui.theme.EP2Theme {
                Surface(
                    color = Gris, // Establece el color de fondo en gris
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(modifier = Modifier.padding(all = 32.dp)) {
                        TextField(value = titulo,
                            label = { Text(text = "Titulo") },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                titulo = it
                            })
                        TextField(value = descripcion,
                            label = { Text(text = "Descripción") },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                descripcion = it
                            })
                        TextField(value = categoria,
                            label = { Text(text = "Categoria") },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                categoria = it
                            })
                        TextField(value = prioridad,
                            label = { Text(text = "Prioridad") },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                prioridad = it
                            })
                        Spacer(modifier = Modifier.size(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Estado tarea ", color = Color.Black)
                            Switch(
                                checked = estadoTarea == TareaEstado.INGRESO,
                                onCheckedChange = {
                                    estadoTarea =
                                        if (it) TareaEstado.INGRESO else TareaEstado.GASTO
                                }
                            )
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        Button(onClick = {
                            guardarDatos(titulo, descripcion, categoria, prioridad, estadoTarea)
                        }) {
                            Text(text = "Guardar", color = Color.White)
                        }
                    }
                }
            }
        }
    }
    private fun guardarDatos(titulo: String, descripcion: String, categoria: String, prioridad: String, estadoTarea: TareaEstado) {
        val datosTarea = DatosTarea(this)
        val autonumerico =
            datosTarea.registrarTareas(datosTarea, titulo, descripcion, categoria, prioridad, estadoTarea.valor)
        Toast.makeText(this, "id: " + autonumerico, Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(
                this,
                TaskPruebaActivity::class.java
            )
        )

    }
}
enum class TareaEstado(val valor: Int) {
    INGRESO(1),
    GASTO(-1)
}