package com.marko.cryptofunctional.injection.activity

import com.marko.cryptofunctional.home.HomeModule
import com.marko.cryptofunctional.home.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

	@ActivityScope
	@ContributesAndroidInjector(modules = [HomeModule::class])
	abstract fun mainActivity(): MainActivity
}