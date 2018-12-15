package com.marko.cryptofunctional.coindetails

import androidx.lifecycle.ViewModel
import com.marko.cryptofunctional.injection.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CoinDetailsModule {

	@Binds
	@IntoMap
	@ViewModelKey(CoinDetailsViewModel::class)
	abstract fun coinDetailsViewModel(bind: CoinDetailsViewModel): ViewModel
}