package com.example.a10minworkout

import android.app.Application
import com.example.a10minworkout.Database.HistoryDatabase

class WorkoutApp : Application (){

    val db by lazy{
        HistoryDatabase.getInstance(this)
    }
}