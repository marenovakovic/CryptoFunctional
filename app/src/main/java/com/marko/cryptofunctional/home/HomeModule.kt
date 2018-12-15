package com.marko.cryptofunctional.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marko.cryptofunctional.injection.viewmodel.ViewModelFactory
import com.marko.cryptofunctional.injection.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

	@Binds
	@IntoMap
	@ViewModelKey(CoinsViewModel::class)
	abstract fun coinsViewModel(bind: CoinsViewModel): ViewModel

	@Binds
	abstract fun coinsViewModelFactory(bind: ViewModelFactory): ViewModelProvider.Factory
}