package com.marko.cryptofunctional.coindetails

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.marko.cryptofunctional.R
import com.marko.cryptofunctional.base.BaseActivity
import com.marko.cryptofunctional.extensions.loadImage
import com.marko.cryptofunctional.extensions.observeNonNull
import com.marko.domain.entities.CoinId
import com.marko.presentation.coindetails.CoinDetailsViewModel
import com.marko.presentation.entities.Coin
import com.marko.presentation.injection.ViewModelFactory
import kotlinx.android.synthetic.main.activity_coin_details.*
import timber.log.Timber
import javax.inject.Inject

/**
 * [Activity] responsible for presenting [Coin] details
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

		viewModel.fetch(coinId)
		viewModel.coinDetails.observeNonNull(this, ::showDetails)
		viewModel.error.observeNonNull(this, ::handleError)
	}

	private fun showDetails(coin: Coin) = with(coin) {
		coinDetailsName.text = name
		coinDetailsSymbol.text = symbol
		coinDetailsLogo.loadImage(logo)
		coinDetailsUSDPrice.text = getString(R.string.usd_price, price.toString())
		coinDetailsBTCPrice.text = getString(R.string.btc_price, priceInBTC.toString())
		coinDetailsMaxSupply.text = getString(R.string.max_supply, maxSupply.toString())
		coinDetailsInExistance.text = getString(R.string.in_existence, inExistenceSupply.toString())
		coinDetailsCirculating.text = getString(R.string.circulating, circulatingSupply.toString())
		coinDetailsTradedInLast24h.text = getString(R.string.traded_in_last_24h, tradedIn24h.toString())
	}

	private fun handleError(throwable: Throwable) = Timber.e(throwable)
}
