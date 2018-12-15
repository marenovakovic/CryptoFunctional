package com.marko.cryptofunctional.app

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.marko.cryptofunctional.dispatchers.CoroutineDispatchers
import com.marko.cryptofunctional.dispatchers.TestCoroutineDispatchersInstrumentation
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class TestApplicationBindingModule {

	@Binds
	abstract fun dispatchers(bind: TestCoroutineDispatchersInstrumentation): CoroutineDispatchers
}

@Module(includes = [TestApplicationBindingModule::class])
class TestApplicationModule {

	@Provides
	fun provideContext(): Context = InstrumentationRegistry.getInstrumentation().context
}