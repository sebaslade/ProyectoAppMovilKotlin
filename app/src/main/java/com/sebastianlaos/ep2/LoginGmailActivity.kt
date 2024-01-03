package com.sebastianlaos.ep2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sebastianlaos.ep2.ui.theme.EP2Theme

class LoginGmailActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        setContent {
            EP2Theme {
                Button(onClick = {
                    iniciarSesion.launch(googleSignInClient.signInIntent)
                }) {
                    Text(text = "Iniciar sesión Google")
                }
            }
        }
    }

    var iniciarSesion =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,EscritorioGmailActivity::class.java))
            }
        }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            startActivity(Intent(this,EscritorioActivity::class.java))
        }
    }
}
