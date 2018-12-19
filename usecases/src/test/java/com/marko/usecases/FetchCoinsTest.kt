package com.marko.usecases

import arrow.effects.unsafeRunSync
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Test

internal class FetchCoinsTest {

	@Test
	fun `does use case calls repository`() {
		FetchCoins(
			context = Dispatchers.Unconfined,
			coinsService = mockk(),
			coinsDao = mockk()
		)
	}
}