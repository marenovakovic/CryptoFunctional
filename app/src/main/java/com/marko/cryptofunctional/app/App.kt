package com.marko.cryptofunctional.app

import com.marko.cryptofunctional.injection.application.appComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

	override fun applicationInjector(): AndroidInjector<out DaggerApplication> = component

	private val component = appComponent

	override fun onCreate() {
		super.onCreate()

		Timber.plant(Timber.DebugTree())
	}
}