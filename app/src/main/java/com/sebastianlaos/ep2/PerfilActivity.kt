package com.sebastianlaos.ep2

import android.content.Intent
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.sebastianlaos.ep2.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris
import com.sebastianlaos.ep2.utils.Total
import com.sebastianlaos.ep2.utils.UserStore
import kotlinx.coroutines.launch

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EP2Theme {
                Surface(
                    color = Gris, // Establece el color de fondo en gris
                    modifier = Modifier.fillMaxSize()
                ) {
                    var mostrarVentanaAlerta by remember { mutableStateOf(false) }
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = Total.usuarioActivo.getString("nombres"),
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 36.sp),
                            color = Color.Black
                        )
                        Text(text = Total.usuarioActivo.getString("cargo"), color = Color.Black)
                        Text(text = Total.usuarioActivo.getString("empresa"), color = Color.Black)
                        Spacer(modifier = Modifier.size(16.dp))
                        Button(onClick = {
                            mostrarVentanaAlerta = true
                        }) {
                            Text(text = "Cerrar sesión", color = Color.White)
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        Icon(Icons.Filled.Home,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    startActivity(
                                        Intent(
                                            this@PerfilActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                }
                                .size(40.dp),
                            tint = Color.Black
                        )
                    }
                    if (mostrarVentanaAlerta) {
                        AlertDialog(
                            icon = { Icon(Icons.Filled.Warning, tint = Color.Black, contentDescription = null) },
                            title = { Text(text = "Cerrar Sesión", color = Color.Black) },
                            text = { Text(text = "¿Está seguro de cerrar sesión?", color = Color.Black) },
                            onDismissRequest = { /*TODO*/ },
                            confirmButton = {
                                Button(onClick = {
                                    mostrarVentanaAlerta = false
                                    cerrarSesion()
                                }) {
                                    Text(text = "Si", color = Color.White)
                                }
                            },
                            dismissButton = {
                                Button(onClick = { mostrarVentanaAlerta = false }) {
                                    Text(text = "No", color =  Color.White)
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun cerrarSesion() {
        val userStore = UserStore(this)
        lifecycleScope.launch {
            userStore.setDatosUsuario("")
        }
        startActivity(Intent(this, SplashActivity::class.java))
    }
}