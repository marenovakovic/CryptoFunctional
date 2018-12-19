package com.marko.cache.coins

import com.marko.cache.factory.CacheCoinFactory

val coinCacheOne = CacheCoinFactory.createCoinCache(id = 1)

val coinCacheTwo = CacheCoinFactory.createCoinCache(id = 2)

val coinCaches = listOf(coinCacheOne, coinCacheTwo)