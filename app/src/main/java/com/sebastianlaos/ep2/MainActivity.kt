package com.sebastianlaos.ep2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sebastianlaos.ep2.pages.CajaPrincipalActivity
import com.sebastianlaos.ep2.ui.theme.EP2Theme
import com.sebastianlaos.ep2.ui.theme.Gris

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EP2Theme{
                Surface(
                    color = Gris, // Establece el color de fondo en gris
                    modifier = Modifier.fillMaxSize()
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensionResource(id = R.dimen.espacio))
                            .padding(horizontal = 16.dp)
                    ) {
                        Icon(
                            Icons.Filled.Send,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            PrincipalActivity::class.java
                                        )
                                    )
                                }
                                .size(40.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            PerfilActivity::class.java
                                        )
                                    )
                                }
                                .size(40.dp),
                            tint = Color.Black
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo2),
                            contentDescription = stringResource(id = R.string.logo),
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(15.dp))
                                .height(250.dp)
                                .width(250.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    PedidosActivity::class.java
                                )
                            )
                        }) {
                            Text(text = stringResource(id = R.string.pedidos),
                                color = Color.White)
                        }
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    CajaPrincipalActivity::class.java
                                )
                            )
                        }) {
                            Text(text = stringResource(id = R.string.title_activity_caja),
                                color = Color.White)
                        }
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    EnviosActivity::class.java
                                )
                            )
                        }) {
                            Text(text = stringResource(id = R.string.title_activity_caja_envios),
                                color = Color.White)
                        }
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    HamburguesaActivity::class.java
                                )
                            )
                        }) {
                            Text(text = stringResource(id = R.string.title_activity_hamburguesa),
                                color = Color.White)
                        }
                    }
                }

            }
        }
    }
}
