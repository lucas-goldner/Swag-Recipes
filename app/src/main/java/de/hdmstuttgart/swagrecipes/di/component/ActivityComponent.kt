package de.hdmstuttgart.swagrecipes.di.component

import dagger.Component
import de.hdmstuttgart.swagrecipes.di.ActivityScope
import de.hdmstuttgart.swagrecipes.di.module.ActivityModule
import de.hdmstuttgart.swagrecipes.ui.BrowseActivity

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: BrowseActivity)

}