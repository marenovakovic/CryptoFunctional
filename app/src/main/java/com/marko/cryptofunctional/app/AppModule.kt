package com.marko.cryptofunctional.app

import android.content.Context
import androidx.room.Room
import arrow.effects.DeferredK
import arrow.effects.ForDeferredK
import arrow.effects.deferredk.async.async
import arrow.effects.typeclasses.Async
import com.marko.cache.coins.CoinsDao
import com.marko.cache.database.CryptoDatabase
import com.marko.data.coins.CoinRepository
import com.marko.domain.dispatchers.CoroutineDispatchers
import com.marko.domain.dispatchers.CoroutineDispatchersImpl
import com.marko.remote.coins.CoinsService
import com.marko.remote.coins.coinsService
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

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

	@Singleton
	@Provides
	fun provideDatabase(context: Context): CryptoDatabase =
		Room.databaseBuilder(context, CryptoDatabase::class.java, "crypto")
			.build()

	@Singleton
	@Provides
	fun provideCoinsDao(cryptoDatabase: CryptoDatabase): CoinsDao = cryptoDatabase.coinsDao()

	@Singleton
	@Provides
	fun provideCoinsRepository(
		coinsService: CoinsService,
		coinsDao: CoinsDao
	): CoinRepository<ForDeferredK> =
		object : CoinRepository<ForDeferredK>, Async<ForDeferredK> by DeferredK.async() {

			override val coroutineContext: CoroutineContext
				get() = Dispatchers.IO
			override val service: CoinsService
				get() = coinsService
			override val coinsDao: CoinsDao
				get() = coinsDao
		}
}