package com.anilari.order

import android.app.Application
import android.content.Context
import com.anilari.order.di.dataModule
import com.anilari.order.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppController: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppController)
            modules(dataModule)
            modules(viewModelModule)
        }
        INSTANCE = this
    }
    companion object{
        private var INSTANCE: AppController? = null

        @JvmStatic
        fun getInstance() : Context {
            return INSTANCE as AppController
        }
    }

    //Merge Conflict
}