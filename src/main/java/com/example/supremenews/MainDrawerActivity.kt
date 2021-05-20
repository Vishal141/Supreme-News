package com.example.supremenews

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.supremenews.notification.NotificationReceiver
import com.example.supremenews.ui.downloaded.Downloaded
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main_drawer.*
import java.util.*

class MainDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_sports, R.id.nav_politics,R.id.nav_india,
                R.id.nav_bollywood,R.id.nav_corona,R.id.nav_cricket,R.id.nav_whatsapp,
                R.id.nav_world
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.itemIconTintList = null

        val handler = Handler()
        handler.postDelayed({
            findViewById<CircleImageView>(R.id.downloaded_icon).setOnClickListener { v -> gotoDownloaded(v!!) }
            findViewById<ImageView>(R.id.web_redirect).setOnClickListener{v -> redirectToWeb()}
        },5000)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun gotoDownloaded(view: View){
        startActivity(Intent(applicationContext,Downloaded::class.java))
    }

    private fun redirectToWeb(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://supremenews.in"))
        startActivity(intent)
    }

    override fun onStop() {
        setAlarm()
        super.onStop()
    }

    private fun setAlarm() {
        val intent = Intent(this,NotificationReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(this,0,intent,0)
        val alarmManager:AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval:Long = 15*60*1000
        val calendar = Calendar.getInstance()
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,interval,pIntent)
    }
}