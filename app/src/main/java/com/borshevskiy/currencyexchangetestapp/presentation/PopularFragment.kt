package com.borshevskiy.currencyexchangetestapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.borshevskiy.currencyexchangetestapp.databinding.FragmentPopularBinding
import com.borshevskiy.currencyexchangetestapp.presentation.adapter.CurrencyAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { CurrencyAdapter(mainViewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        binding.rvCurrencyList.adapter = mAdapter
        mainViewModel.allCurrenciesList.observe(viewLifecycleOwner) {
            Log.d("TEST", it.toString())
            mAdapter.submitList(it)
        }
//        binding.popularScreen.findNavController().navigate(R.id.action_popular_screen_to_favorites_screen)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}