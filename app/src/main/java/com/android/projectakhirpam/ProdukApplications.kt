package com.android.projectakhirpam

import android.app.Application
import com.android.projectakhirpam.dependenciesinjection.AppContainer
import com.android.projectakhirpam.dependenciesinjection.ApplicationsContainer

class ProdukApplications : Application() {
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = ApplicationsContainer()
    }
}