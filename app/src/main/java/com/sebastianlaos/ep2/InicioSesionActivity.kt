package com.sebastianlaos.ep2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sebastianlaos.ep2.ui.theme.DarkGrey
import com.sebastianlaos.ep2.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris
import com.sebastianlaos.ep2.utils.Total
import com.sebastianlaos.ep2.utils.UserStore
import kotlinx.coroutines.launch
import org.json.JSONArray

class InicioSesionActivity : ComponentActivity() {
    var estadoChecked: Boolean = false

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var usuario by remember { mutableStateOf("") }
            var clave by remember { mutableStateOf("") }
            var guardarSesion by remember { mutableStateOf(false) }

            var isValidUsuario by remember { mutableStateOf(true) }

            EP2Theme {
                Surface(
                    color = Gris, // Establece el color de fondo en gris
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(modifier = Modifier.padding(all = 32.dp)) {
                        TextField(value = usuario,
                            label = { Text(text = "Nombre usuario") },
                            modifier = Modifier.fillMaxWidth(),
                            onValueChange = {
                                usuario = it
                                isValidUsuario = it.isNotEmpty()
                            })
                        Spacer(modifier = Modifier.size(16.dp))
                        TextField(value = clave,
                            label = { Text(text = "Contraseña") },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            onValueChange = {
                                clave = it
                            })
                        Spacer(modifier = Modifier.size(16.dp))
                        Button(onClick = {
                            if (usuario == "") isValidUsuario = false
                            leerServicioIniciarSesion(usuario, clave)
                        }) {
                            Text(text = "Iniciar sesión", color = Color.White)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Desea guardar la sesión", color = Color.Black)
                            Checkbox(checked = guardarSesion, onCheckedChange = {
                                guardarSesion = it
                                estadoChecked = it
                            })
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        if (!isValidUsuario) {
                            Text(text = "Debe ingresar el nombre del usuario", color = Color.Red)
                        }
                    }
                }
            }
        }
    }

    private fun leerServicioIniciarSesion(usuario: String, clave: String) {
        val queue = Volley.newRequestQueue(this)
        val url = Total.RutaURL + "iniciarsesion.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("DATOS", response)
                verificarInicioSesion(response)
            },
            {
                Log.d("DATOSERROR", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params.put("usuario", usuario)
                params.put("clave", clave)
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun verificarInicioSesion(response: String) {
        when (response) {
            "-1" -> Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show()
            "-2" -> Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show()
            else -> {
                Total.usuarioActivo = JSONArray(response).getJSONObject(0)
                Toast.makeText(
                    this,
                    "Hola " + Total.usuarioActivo.getString("nombres"),
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, PerfilActivity::class.java))
                verificarGuardarSesion(response)
            }
            //anton 005023
        }
    }

    private fun verificarGuardarSesion(response: String) {
        if (estadoChecked) {
            val userStore = UserStore(this)
            lifecycleScope.launch {
                userStore.setDatosUsuario(response)
            }
        }
    }
}