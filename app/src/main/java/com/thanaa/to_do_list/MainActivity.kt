package com.thanaa.to_do_list

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.thanaa.to_do_list.fragment.list.OnDatePass

import java.util.*

class MainActivity : AppCompatActivity(), OnDatePass {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.thanaa.to_do_list"
    private val description = "Notification"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.navHostFragment))
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDatePass(date: Date, title: String) {
        //Notification
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var intent = Intent(this, LauncherActivity::class.java)
        val pending =
            PendingIntent.getActivities(this, 0, arrayOf(intent), PendingIntent.FLAG_UPDATE_CURRENT)
        var currentTime = System.currentTimeMillis() - 1000 * 60 * 60 * 24
        val current = Date(currentTime)
        if (date.before(current)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel =
                    NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)

                builder =
                    Notification.Builder(this, channelId).setContentTitle("Complete Your Tasks")
                        .setContentText("You have to $title")
                        .setSmallIcon(R.drawable.notification)
                        .setLargeIcon(
                            BitmapFactory.decodeResource(
                                this.resources,
                                R.drawable.ic_launcher_background
                            )
                        )
                        .setContentIntent(pending)

            } else {
                builder = Notification.Builder(this)
                    .setContentText("You have to $title")
                    .setSmallIcon(R.drawable.notification)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources,
                            R.drawable.ic_launcher_background
                        )
                    )
                    .setContentIntent(pending)
            }
            notificationManager.notify(1234, builder.build())
        }

    }

}