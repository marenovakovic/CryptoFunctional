package com.marko.cryptofunctional.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marko.presentation.coins.CoinsViewModel
import com.marko.presentation.injection.ViewModelFactory
import com.marko.presentation.injection.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TestHomeModule {

	@Binds
	@IntoMap
	@ViewModelKey(CoinsViewModel::class)
	abstract fun coinsViewModel(bind: CoinsViewModel): ViewModel

	@Binds
	abstract fun coinsViewModelFactory(bind: ViewModelFactory): ViewModelProvider.Factory
}