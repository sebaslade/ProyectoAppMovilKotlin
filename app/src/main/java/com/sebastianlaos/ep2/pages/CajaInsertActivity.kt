package com.sebastianlaos.ep2.pages

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
import com.sebastianlaos.ep2.datos.DatosCaja
import com.sebastianlaos.ep2.pages.ui.theme.EP2Theme
import com.sebastianlaos.ep2.pages.ui.theme.Gris

class CajaInsertActivity : ComponentActivity() {
    var estadoChecked: Boolean = false
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var descripcion by remember { mutableStateOf("") }
            var monto by remember { mutableStateOf("") }
            var tipoMovimiento by remember { mutableStateOf(MovimientoTipo.INGRESO) }
            EP2Theme {
                Surface(
                    color = Gris, // Establece el color de fondo en gris
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(modifier = Modifier.padding(all = 32.dp)) {
                        TextField(value = descripcion,
                            label = { Text(text = "Descripción") },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                descripcion = it
                            })
                        Spacer(modifier = Modifier.size(16.dp))
                        TextField(value = monto,
                            label = { Text(text = "Monto") },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                monto = it
                            })
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Tipo movimiento ", color = Color.Black)
                            Switch(
                                checked = tipoMovimiento == MovimientoTipo.INGRESO,
                                onCheckedChange = {
                                    tipoMovimiento = if (it) MovimientoTipo.INGRESO else MovimientoTipo.GASTO
                                }
                            )
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        Button(onClick = {
                            guardarDatos(descripcion, monto, tipoMovimiento)
                        }) {
                            Text(text = "Guardar", color = Color.White)
                        }
                    }
                }
            }
        }
    }

    private fun guardarDatos(descripcion: String, monto: String, tipoMovimiento: MovimientoTipo) {
        val datosCaja = DatosCaja(this)
        val autonumerico =
            datosCaja.registrarMovimientos(datosCaja, descripcion, monto.toFloat(), tipoMovimiento.valor)
        Toast.makeText(this, "id: " + autonumerico, Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(
                this,
                CajaPrincipalActivity::class.java
            )
        )

    }
}
enum class MovimientoTipo(val valor: Int) {
    INGRESO(1),
    GASTO(-1)
}