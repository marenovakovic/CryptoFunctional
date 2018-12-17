package com.marko.cryptofunctional.app

import android.content.Context
import com.marko.cryptofunctional.data.CoinsService
import com.marko.cryptofunctional.data.coinsService
import com.marko.cryptofunctional.dispatchers.CoroutineDispatchers
import com.marko.cryptofunctional.dispatchers.CoroutineDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppBindingModule {

	@Singleton
	@Binds
	abstract fun dispatchers(bind: CoroutineDispatchersImpl): CoroutineDispatchers
}

@Module(includes = [AppBindingModule::class])
class AppModule(private val context: Context) {

	@Provides
	fun provideContext(): Context = context

	@Singleton
	@Provides
	fun provideCoinsService(): CoinsService = coinsService
}