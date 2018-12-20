package com.marko.cryptofunctional.injection.activity

import com.marko.cryptofunctional.coindetails.CoinDetailsActivity
import com.marko.cryptofunctional.coindetails.CoinDetailsModule
import com.marko.cryptofunctional.home.HomeModule
import com.marko.cryptofunctional.home.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

	@ActivityScope
	@ContributesAndroidInjector(modules = [HomeModule::class])
	abstract fun mainActivity(): MainActivity

	@ActivityScope
	@ContributesAndroidInjector(modules = [CoinDetailsModule::class])
	abstract fun coinDetailsActivity(): CoinDetailsActivity
}