package com.marko.cache.coins

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.marko.cache.database.CryptoDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
abstract class DatabaseTest {

	@get:Rule
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	private val context = InstrumentationRegistry.getInstrumentation().context

	private lateinit var database: CryptoDatabase

	protected val coinsDao: CoinsDao
		get() = database.coinsDao()

	@Before
	fun setUp() {
		database = Room.inMemoryDatabaseBuilder(context, CryptoDatabase::class.java)
			.allowMainThreadQueries()
			.build()
	}

	@After
	fun cleanUp() = database.close()
}