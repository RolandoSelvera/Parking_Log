package com.rolandoselvera.parkinglog.core.base

import android.app.Application
import com.rolandoselvera.parkinglog.data.local.db.CarDatabase
import com.rolandoselvera.parkinglog.data.local.preferences.PreferencesProvider

class App : Application() {
    // Se utiliza el delegado 'lazy' para que crear la instancia database de forma diferida
    // cuando se consulte la referencia por 1a. vez (en lugar de hacerlo cuando se
    // inicie la app). Esta acción creará la base de datos (la base de datos física en el
    // dispositivo) en el primer acceso.
    val database: CarDatabase by lazy { CarDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        PreferencesProvider.init(this)
    }
}