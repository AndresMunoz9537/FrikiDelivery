package com.optic.FrikiDelivery.activities

import android.content.Context
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.optic.deliverykotlinudemy.R

class MainActivity : AppCompatActivity() { //OnReady


    private val GOOGLE_SING_IN = 100
    var imageViewGoToRegister: Button? = null
    var editTextEmail : EditText? = null
    var editTextPassword : EditText? = null
    var buttonLogin : Button? = null
    var layout: LinearLayout? = null
    var googleBotton : Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewGoToRegister = findViewById(R.id.imageview_go_to_register)
        editTextEmail = findViewById(R.id.edittext_email)
        editTextPassword = findViewById(R.id.edittext_password)
        buttonLogin = findViewById(R.id.btn_login)
        layout = findViewById(R.id.authLayout)
        googleBotton = findViewById(R.id.buttonGoogle)

        imageViewGoToRegister?.setOnClickListener { goToRegister() }
        buttonLogin?.setOnClickListener { login() }
        session()
    }

    override fun onStart() {
        super.onStart()

        layout?.visibility = View.VISIBLE
    }

    private fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if(email != null && provider != null){
            layout?.visibility = View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }

        googleBotton?.setOnClickListener {

            //Configuracion
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SING_IN)
        }
    }

    private fun login() {
        val email = editTextEmail?.text.toString() // null pinter exception
        val password = editTextPassword?.text.toString()

        if(isValidForm(email,password)){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    showHome(it.result?.user?.email?: "", ProviderType.BASIC)
                } else {
                    showAlert()
                }
            }
            Toast.makeText(this, "El formulario es valido", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this, "No es valido", Toast.LENGTH_SHORT).show()}



        Toast.makeText(this, "El email es: $email", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "El password es: $password", Toast.LENGTH_LONG).show()

        Log.d("MainActivity", "el email es: $email")
        Log.d("MainActivity", "el password es: $password")



    }



    fun String.isEmailValid():Boolean{
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    private fun isValidForm(email: String, password:String): Boolean{
        if (email.isBlank())
        {
            return false}
        if(!email.isEmailValid()){
            return false
        }
        if (password.isBlank()){
            return false
        }
        return true
        }


    private fun goToRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("se ha producido un error autenticando al usuario")
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent(this,PrincipalActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SING_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {

                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)

                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {

                        if (it.isSuccessful){
                            showHome(account.email?: "" ,ProviderType.GOOGLE)
                        } else {showAlert()}
                    }

                }
            }
            catch (e: ApiException){
                showAlert()
            }

        }
    }
}