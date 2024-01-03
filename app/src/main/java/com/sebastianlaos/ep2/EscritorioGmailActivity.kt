package com.sebastianlaos.ep2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EscritorioGmailActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val account = GoogleSignIn.getLastSignedInAccount(this)
        setContent {
            Column {
                Text(text = account?.displayName.toString())
                Text(text = account?.familyName.toString())
                Text(text = account?.givenName.toString())
                Text(text = account?.email.toString())
                Text(text = account?.photoUrl.toString())
                AsyncImage(
                    model = account?.photoUrl.toString(),
                    contentDescription = null
                )
                Button(onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build()
                    googleSignInClient = GoogleSignIn.getClient(this@EscritorioGmailActivity, gso)
                    googleSignInClient.signOut()
                    startActivity(Intent(this@EscritorioGmailActivity,LoginGmailActivity::class.java))
                }) {
                    Text(text = "Cerrar sesión")
                }
            }

        }
    }
}
