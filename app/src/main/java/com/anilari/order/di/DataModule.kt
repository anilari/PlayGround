package com.anilari.order.di

import com.anilari.order.data.DummyDataSource
import com.anilari.order.data.Repository
import com.anilari.order.model.DataBase
import org.koin.dsl.module

val dataModule = module {

    single { DataBase.getInstance() }
    factory { DummyDataSource() }
    single { Repository(get(), get()) }

}