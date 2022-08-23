package com.borshevskiy.currencyexchangetestapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.borshevskiy.currencyexchangetestapp.R
import com.borshevskiy.currencyexchangetestapp.databinding.CurrencyItemBinding
import com.borshevskiy.currencyexchangetestapp.domain.Currency
import com.borshevskiy.currencyexchangetestapp.presentation.MainViewModel
import javax.inject.Inject

class CurrencyAdapter @Inject constructor(private val mainViewModel: MainViewModel) :
    ListAdapter<Currency, CurrencyAdapter.CurrencyViewHolder>(CurrencyDiffCallback) {

    private var isFavorite = false

    class CurrencyViewHolder(val binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyViewHolder(
            CurrencyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = getItem(position)
        with(holder.binding) {
            currencyName.text = currency.name
            currencyValue.text = currency.value
            if (currency.isFavorite) {
                isFavorite.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_star_clicked))
            } else { isFavorite.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_star_unclicked))
            }
            isFavorite.setOnClickListener {
                this@CurrencyAdapter.isFavorite = !this@CurrencyAdapter.isFavorite
                mainViewModel.insertOrRemoveFromFavorites(Currency(currency.name, currency.value, !this@CurrencyAdapter.isFavorite))
                if (!this@CurrencyAdapter.isFavorite) {
                    isFavorite.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_star_unclicked))
                } else {
                    isFavorite.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_star_clicked))
                }
            }
        }
    }
}