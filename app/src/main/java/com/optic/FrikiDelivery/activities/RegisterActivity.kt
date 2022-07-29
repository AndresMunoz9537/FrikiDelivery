package com.optic.FrikiDelivery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.optic.deliverykotlinudemy.R

class RegisterActivity : AppCompatActivity() {

    var imageViewGoToLogin: ImageView? = null
    var editTextName: EditText? = null
    var editTextLastName: EditText? = null
    var editTexEmail: EditText? = null
    var editTextPhone: EditText? = null
    var editTextPassword: EditText? = null
    var editTextConfirmPassword: EditText? = null
    var buttonRegister: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        imageViewGoToLogin = findViewById(R.id.imageview_go_to_login)
        editTextName = findViewById(R.id.edittext_name)
        editTexEmail = findViewById(R.id.edittext_email)
        editTextPassword = findViewById(R.id.edittext_password)
        editTextConfirmPassword = findViewById(R.id.edittext_confirm_password)
        buttonRegister = findViewById(R.id.btn_register)

        imageViewGoToLogin?.setOnClickListener { goToLogin() }
        buttonRegister?.setOnClickListener { register() }

      //Setup
        //setup()


    }

    /*private fun setup(){
        title = "Autenticacion"
        buttonRegister?.setOnClickListener {
            if (editTexEmail?.text.toString().isNotEmpty()  && editTextPassword?.text.toString().isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTexEmail?.text.toString(),editTextPassword?.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){} else{} }
            } }
    }*/



    private fun register(){

        val TAG = "RegisterActivity"
        val name = editTextName?.text.toString()
        val lastName = editTextLastName?.text.toString()
        val email = editTexEmail?.text.toString()
        val phone = editTextPhone?.text.toString()
        val password = editTextPassword?.text.toString()
        val confirmPassword = editTextConfirmPassword?.text.toString()

        if(isValidForm(name = name, lastName = lastName, email = email, phone = phone, password = password, confirmPassword = confirmPassword)){
            Toast.makeText(this, "El formulario es valido", Toast.LENGTH_SHORT).show()
         FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener {
         if (it.isSuccessful){
          showHome(it.result?.user?.email?: "", ProviderType.BASIC)
         } else {
          showAlert()
         }
         }

        }


        Log.d(TAG, "el name es: $name")
        Log.d("RegisterActivity", "el lastname es: $lastName")
        Log.d("RegisterActivity", "el email es: $email")
        Log.d("RegisterActivity", "el phone es: $phone")
        Log.d("RegisterActivity", "el password es: $password")
        Log.d("RegisterActivity", "el confirm password es: $confirmPassword")
    }

    fun String.isEmailValid():Boolean{
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    private fun isValidForm(
        email: String,
        name:String,
        lastName:String,
        phone:String,
        password:String,
        confirmPassword:String
    ): Boolean{
        if (name.isBlank()) {
            Toast.makeText(this, "Debes ingrsar el nombre", Toast.LENGTH_SHORT).show()
            return false }
        if (lastName.isBlank()) {
            Toast.makeText(this, "debes ingresar el last name", Toast.LENGTH_SHORT).show()
            return false}
        if (phone.isBlank()) {
            Toast.makeText(this, "debes ingresar el telefono", Toast.LENGTH_SHORT).show()
            return false}
        if (email.isBlank()) {
            Toast.makeText(this, "debes ingresar el email", Toast.LENGTH_SHORT).show()
            return false}
        if(!email.isEmailValid()){
            Toast.makeText(this, "debes ingresar el email correctamente", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isBlank()){
            Toast.makeText(this, "debes ingresar el password", Toast.LENGTH_SHORT).show()
            return false
        }
        if (confirmPassword.isBlank())
        { Toast.makeText(this, "debes ingresar el confirm password", Toast.LENGTH_SHORT).show()
            return false}
        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun goToLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("se ha producido un error autenticando al usuario")
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType){
       val homeIntent = Intent(this, PrincipalActivity::class.java).apply {
           putExtra("email", email)
           putExtra("provider", provider.name)
       }
        startActivity(homeIntent)
    }

}