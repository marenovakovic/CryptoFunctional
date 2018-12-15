package com.marko.cryptofunctional.injection.application

import com.marko.cryptofunctional.app.TestApp
import com.marko.cryptofunctional.app.TestApplicationModule
import com.marko.cryptofunctional.injection.activity.TestActivityBindingModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@Component(
	modules = [
		AndroidInjectionModule::class,
		AndroidSupportInjectionModule::class,
		TestApplicationModule::class,
		TestActivityBindingModule::class
	]
)
interface TestApplicationComponent : ApplicationComponent

val TestApp.testComponent: TestApplicationComponent
	get() = DaggerTestApplicationComponent.create()