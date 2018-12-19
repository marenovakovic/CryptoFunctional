package com.marko.cryptofunctional.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marko.cryptofunctional.R
import com.marko.cryptofunctional.extensions.inflate
import com.marko.domain.entities.CoinId
import com.marko.presentation.entities.Coin
import kotlinx.android.synthetic.main.list_item_coin.view.*

class CoinsAdapter(
	private val onClick: (coinId: CoinId) -> Unit
) : ListAdapter<Coin, CoinsAdapter.CoinHolder>(CoinDiffer) {

	var coins: List<Coin> = emptyList()
		set(value) = submitList(value)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinHolder {
		val view = parent.inflate(R.layout.list_item_coin)
		return CoinHolder(view)
	}

	override fun onBindViewHolder(holder: CoinHolder, position: Int) =
		holder.bind(getItem(position))

	inner class CoinHolder(view: View) : RecyclerView.ViewHolder(view) {

		fun bind(coin: Coin) {
			itemView.setOnClickListener { onClick(coin.id) }

			itemView.listItemCoinName.text = coin.name
		}
	}
}

object CoinDiffer : DiffUtil.ItemCallback<Coin>() {

	override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean = oldItem.id == newItem.id

	override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean = oldItem == newItem
}