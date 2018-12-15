package com.marko.cryptofunctional.app

import com.marko.cryptofunctional.injection.application.testComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class TestApp : DaggerApplication() {

	override fun applicationInjector(): AndroidInjector<out DaggerApplication> = component

	private val component = testComponent
}