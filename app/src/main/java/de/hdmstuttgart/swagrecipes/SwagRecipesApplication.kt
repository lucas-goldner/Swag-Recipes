package de.hdmstuttgart.swagrecipes

import android.app.Application
import de.hdmstuttgart.swagrecipes.di.component.ApplicationComponent
import de.hdmstuttgart.swagrecipes.di.component.DaggerApplicationComponent
import de.hdmstuttgart.swagrecipes.di.module.ApplicationModule

class SwagRecipesApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}