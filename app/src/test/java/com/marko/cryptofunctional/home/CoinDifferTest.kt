package com.marko.cryptofunctional.home

import com.marko.cryptofunctional.factory.CoinsFactory
import org.junit.Test

internal class CoinDifferTest {

	@Test
	fun `test CoinDiffer are items the same when items ARE the same`() {
		val coin = CoinsFactory.createCoin(id = 1)
		val otherCoin = CoinsFactory.createCoin(id = 1)

		assert(CoinDiffer.areItemsTheSame(coin, otherCoin))
	}

	@Test
	fun `test CoinDiffer are items the same when items ARE NOT the same`() {
		val coin = CoinsFactory.createCoin(id = 1)
		val otherCoin = CoinsFactory.createCoin(id = 2)

		assert(! CoinDiffer.areItemsTheSame(coin, otherCoin))
	}

	@Test
	fun `test CoinDiffer are contents the same when contents ARE the same`() {
		val coin = CoinsFactory.createCoin()
		val otherCoin = CoinsFactory.createCoin()

		assert(CoinDiffer.areContentsTheSame(coin, otherCoin))
	}

	@Test
	fun `test CoinDiffer are contents the same when contents ARE NOT the same`() {
		val coin = CoinsFactory.createCoin(name = "coin")
		val otherCoin = CoinsFactory.createCoin(name = "other coin")

		assert(! CoinDiffer.areContentsTheSame(coin, otherCoin))
	}
}