package com.optic.FrikiDelivery.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.optic.deliverykotlinudemy.R


enum class ProviderType {
    BASIC,
    GOOGLE,
    FACEBOOK
}



class PrincipalActivity : AppCompatActivity() {

    var prov: TextView? = null
    var name: TextView? = null
    var logOut: ImageButton? = null
    var inicio : ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

      prov = findViewById(R.id.emailTextView)
        name = findViewById(R.id.nameTextView)
        logOut = findViewById(R.id.logOutBtn)
        inicio = findViewById(R.id.inicioBtn)
        //Setup
        val bundle:Bundle? = intent.extras
       val email= bundle?.getString("email")
        val provider =bundle?.getString("provider")

        setup(email?:"",provider?:"")

        //Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
       prefs.putString("email",email)
        prefs.putString("provider", provider)
        prefs.apply()

        inicio?.setOnClickListener{goToInicio()}

    }

    private fun setup(email:String,provider:String){

        title = "Inicio"
        prov?.text = email
        name?.text = provider



        logOut?.setOnClickListener {

            //Borrador de datos
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            Firebase.auth.signOut()
            onBackPressed()
        }

    }

    private fun goToInicio(){
        val i = Intent(this,ProducstActivity::class.java)
        startActivity(i)
    }
}
