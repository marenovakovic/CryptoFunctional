package com.marko.cryptofunctional.injection.application

import com.marko.cryptofunctional.app.App
import com.marko.cryptofunctional.app.AppModule
import com.marko.cryptofunctional.injection.activity.ActivityBindingModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
	modules = [
		AndroidInjectionModule::class,
		AndroidSupportInjectionModule::class,
		AppModule::class,
		ActivityBindingModule::class
	]
)
interface ApplicationComponent : AndroidInjector<App>

val App.appComponent: ApplicationComponent
	get() = DaggerApplicationComponent.builder()
		.appModule(AppModule(this))
		.build()