package com.marko.cryptofunctional.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.marko.cryptofunctional.R
import com.marko.cryptofunctional.base.BaseActivity
import com.marko.cryptofunctional.coindetails.CoinDetailsActivity
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.event.EventObserver
import com.marko.cryptofunctional.extensions.beVisibleIf
import com.marko.cryptofunctional.extensions.observeNonNull
import com.marko.cryptofunctional.extensions.startActivity
import com.marko.cryptofunctional.extensions.toastShort
import com.marko.cryptofunctional.injection.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

/**
 * [Activity] responsible for showing [Coin] [List]
 */
class MainActivity : BaseActivity() {

	@Inject
	lateinit var factory: ViewModelFactory

	private val viewModel: CoinsViewModel by lazy(LazyThreadSafetyMode.NONE) {
		ViewModelProviders.of(this, factory).get(CoinsViewModel::class.java)
	}

	private val coinsAdapter: CoinsAdapter by lazy(LazyThreadSafetyMode.NONE) {
		CoinsAdapter { startActivity<CoinDetailsActivity>(CoinDetailsActivity.EXTRA_COIN_ID to it) }
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		viewModel.fetch()
		viewModel.loading.observe(this, EventObserver(coinsProgressBar::beVisibleIf))
		viewModel.coins.observeNonNull(this, ::showCoins)
		viewModel.error.observeNonNull(this, ::showError)

		coinsRecyclerView.adapter = coinsAdapter
		coinsRecyclerView.layoutManager = LinearLayoutManager(this)
			.apply { isItemPrefetchEnabled = true }
		coinsRecyclerView.setHasFixedSize(true)
	}

	private fun showCoins(coins: List<Coin>) {
		coinsAdapter.coins = coins
	}

	private fun showError(throwable: Throwable) = Timber.e(throwable)
}