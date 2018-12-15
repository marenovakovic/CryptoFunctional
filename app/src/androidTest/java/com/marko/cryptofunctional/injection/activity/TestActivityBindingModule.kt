package com.marko.cryptofunctional.injection.activity

import com.marko.cryptofunctional.home.MainActivity
import com.marko.cryptofunctional.home.TestHomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestActivityBindingModule {

	@ActivityScope
	@ContributesAndroidInjector(modules = [TestHomeModule::class])
	abstract fun mainActivity(): MainActivity
}