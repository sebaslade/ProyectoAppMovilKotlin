package com.sebastianlaos.ep2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sebastianlaos.ep2.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris

class PrincipalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EP2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = Gris, // Establece el color de fondo en gris
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@PrincipalActivity,
                                    EnviosActivity2::class.java
                                )
                            )
                        }) {
                            Text(text = stringResource(id = R.string.title_activity_caja_envios),
                                color = Color.White)
                        }
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@PrincipalActivity,
                                    TaskPruebaActivity::class.java
                                )
                            )
                        }) {
                            Text(text = stringResource(id = R.string.title_activity_tareas),
                                color = Color.White)
                        }
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@PrincipalActivity,
                                    LoginGmailActivity::class.java
                                )
                            )
                        }) {
                            Text(text = "Iniciar sesión Google",
                                color = Color.White)
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    startActivity(
                                        Intent(
                                            this@PrincipalActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                }
                                .size(40.dp),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}
