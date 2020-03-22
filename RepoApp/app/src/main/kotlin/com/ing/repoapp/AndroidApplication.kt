package com.ing.repoapp

import android.app.Application
import com.ing.repoapp.core.di.ApplicationComponent
import com.ing.repoapp.core.di.ApplicationModule
import com.ing.repoapp.core.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary
import io.realm.Realm
import io.realm.RealmConfiguration

class AndroidApplication : Application() {


    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
        this.initializeLeakDetection()
        this.initializeRealmDb()
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }

    private fun initializeRealmDb() {
        Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
    }


}
