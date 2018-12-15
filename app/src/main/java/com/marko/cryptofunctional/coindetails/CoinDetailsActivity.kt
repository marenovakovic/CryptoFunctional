package com.marko.cryptofunctional.coindetails

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.marko.cryptofunctional.R
import com.marko.cryptofunctional.base.BaseActivity
import com.marko.cryptofunctional.entities.Coin
import com.marko.cryptofunctional.entities.CoinId
import com.marko.cryptofunctional.event.EventObserver
import com.marko.cryptofunctional.extensions.beVisibleIf
import com.marko.cryptofunctional.extensions.observeNonNull
import com.marko.cryptofunctional.extensions.toastShort
import com.marko.cryptofunctional.injection.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_coin_details.*
import timber.log.Timber
import javax.inject.Inject

/**
 * [Activity] responsible for showing [Coin] details
 */
class CoinDetailsActivity : BaseActivity() {

	companion object {
		const val EXTRA_COIN_ID = "coin_id"
	}

	@Inject
	lateinit var factory: ViewModelFactory

	private val viewModel: CoinDetailsViewModel by lazy(LazyThreadSafetyMode.NONE) {
		ViewModelProviders.of(this, factory).get(CoinDetailsViewModel::class.java)
	}

	private val coinId: CoinId by lazy(LazyThreadSafetyMode.NONE) {
		intent.getIntExtra(EXTRA_COIN_ID, - 1)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_coin_details)

		viewModel.fetch(coinId = coinId)
		viewModel.loading.observe(this, EventObserver(coinDetailsProgressBar::beVisibleIf))
		viewModel.coinDetails.observeNonNull(this, ::showDetails)
		viewModel.error.observeNonNull(this, ::showError)
	}

	private fun showDetails(coin: Coin) = with(coin) {
		coinDetailsId.text = id.toString()
		coinDetailsName.text = name
		coinDetailsSymbol.text = symbol
	}

	private fun showError(throwable: Throwable) = Timber.e(throwable)
}
