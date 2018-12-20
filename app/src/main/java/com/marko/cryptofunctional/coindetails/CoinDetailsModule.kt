package com.marko.cryptofunctional.coindetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marko.presentation.coindetails.CoinDetailsViewModel
import com.marko.presentation.injection.ViewModelFactory
import com.marko.presentation.injection.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CoinDetailsModule {

	@Binds
	@IntoMap
	@ViewModelKey(CoinDetailsViewModel::class)
	abstract fun coinDetailsViewModel(bind: CoinDetailsViewModel): ViewModel

	@Binds
	abstract fun coinDetailsViewModelFactory(bind: ViewModelFactory): ViewModelProvider.Factory
}