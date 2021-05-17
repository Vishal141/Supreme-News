package com.example.supremenews.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?,intent: Intent?) {
        val i = Intent(context,NotificationService::class.java)
        context!!.startService(i)
    }
}