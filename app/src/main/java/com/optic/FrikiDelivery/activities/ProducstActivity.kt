package com.optic.FrikiDelivery.activities

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.optic.deliverykotlinudemy.R
import com.google.android.material.navigation.NavigationView

class ProducstActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toogle: ActionBarDrawerToggle
    //lateinit var menu: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producst)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

       // menu = findViewById(R.id.menuDrop)
        drawer = findViewById(R.id.drawer_layout)

        //menu.setOnClickListener { this.menu}
        toogle = ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer.addDrawerListener(toogle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.perfil -> Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
            R.id.metodos -> Toast.makeText(this, "Metodos", Toast.LENGTH_SHORT).show()
            R.id.direcciones -> Toast.makeText(this, "Direcciones", Toast.LENGTH_SHORT).show()
            R.id.historial -> Toast.makeText(this, "Historial", Toast.LENGTH_SHORT).show()
            R.id.cupones -> Toast.makeText(this, "Cupones", Toast.LENGTH_SHORT).show()
            R.id.notificaciones -> Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show()
            R.id.politicas -> Toast.makeText(this, "Politicas", Toast.LENGTH_SHORT).show()
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.popup_menu, popup.menu)
        popup.show()
    }


}