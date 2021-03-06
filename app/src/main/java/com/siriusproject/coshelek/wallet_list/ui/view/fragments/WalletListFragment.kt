package com.siriusproject.coshelek.wallet_list.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.CurrencyContainerBinding
import com.siriusproject.coshelek.databinding.FragmentWalletListBinding
import com.siriusproject.coshelek.statistics.ui.view.StatisticsActivity
import com.siriusproject.coshelek.utils.CurrencyFormatter
import com.siriusproject.coshelek.utils.LoadingState
import com.siriusproject.coshelek.utils.collectWhenStarted
import com.siriusproject.coshelek.wallet_information.ui.view.MainScreenActivity
import com.siriusproject.coshelek.wallet_list.data.model.CurrencyModel
import com.siriusproject.coshelek.wallet_list.ui.adapters.WalletListAdapter
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WalletListFragment : Fragment(R.layout.fragment_wallet_list) {

    companion object {
        const val WALLET_ID = "wallet_id"
        const val WALLET_NAME = "wallet_name"
    }

    private val binding: FragmentWalletListBinding by viewBinding(FragmentWalletListBinding::bind)
    private val walletsViewModel: WalletListViewModel by viewModels()
    private lateinit var adapter: WalletListAdapter

    @Inject
    lateinit var formatter: CurrencyFormatter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deleteClickLambda: (Int) -> Unit = {
            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)
                .setMessage(resources.getString(R.string.delete_message))
                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.delete)) { dialog, which ->
                    walletsViewModel.deleteWallet(it)
                }
                .show()
        }
        val onWalletClickLambda: (Int, String) -> Unit = { id, name ->
            startWalletInfoActivity(id, name)
        }
        val onEditClickLambda: (Int) -> Unit = {
            walletsViewModel.onEditWalletPressed(it)
        }
        val onVisibilityChanged: (Boolean, Int) -> Unit = { visibility, id ->
            walletsViewModel.onVisibilityPressed(visibility, id)
        }
        adapter = WalletListAdapter(
            deleteClickLambda,
            onWalletClickLambda,
            onEditClickLambda,
            onVisibilityChanged
        )
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        Log.d(javaClass.name, "Fragment created")

        recycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )

        with(walletsViewModel) {
            wallets.collectWhenStarted(viewLifecycleOwner, { it ->
                adapter.setItems(it)
                showRecordsText(adapter.isEmpty)
            })

            mainBalance.collectWhenStarted(viewLifecycleOwner, {
                binding.balance.text = formatter.formatBigDecimal(it, "RUB")
            })

            income.collectWhenStarted(viewLifecycleOwner, {
                binding.incomeAmount.text = formatter.formatBigDecimal(it, "RUB")
            })

            expense.collectWhenStarted(viewLifecycleOwner, {
                binding.expenseAmount.text = formatter.formatBigDecimal(it, "RUB")
            })

            loadingState.collectWhenStarted(viewLifecycleOwner, {
                showState(it)
            })
            currencies.collectWhenStarted(viewLifecycleOwner, {
                with(binding) {
                    if (!it.isEmpty()) {
                        setCurrency(usd, it[0])
                        setCurrency(eur, it[1])
                    }
                }
            })
        }


        binding.addWallet.setOnClickListener {
            Log.d(javaClass.name, "Button pressed")
            walletsViewModel.onCreateWalletPressed()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadNewData()
        }

        binding.buttonOpenStatistics.setOnClickListener {
            startActivity(Intent(requireContext(), StatisticsActivity::class.java))
        }

        loadNewData()

    }

    private fun loadNewData() {
        walletsViewModel.fetchWallets()
        walletsViewModel.getCurrencies()
    }

    private fun setCurrency(binding: CurrencyContainerBinding, model: CurrencyModel) {
        with(binding) {
            amount.text = model.rate.toPlainString()
            currency.text = model.name
            val icon = when (model.dynamic) {
                "UP" -> R.drawable.ic_green_arrow
                "DOWN" -> R.drawable.ic_red_arrow
                else -> R.drawable.ic_currency_not_changed
            }
            amount.setCompoundDrawablesWithIntrinsicBounds(
                0, 0,
                icon, 0
            )
        }
    }

    override fun onResume() {
        super.onResume()
        loadNewData()
    }

    private fun showState(loadingState: LoadingState) {
        when (loadingState) {
            LoadingState.Loading -> {
                with(binding) {
                    noWalletsYet.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = true
                    recyclerView.visibility = View.GONE
                    currencyContainer.visibility = View.GONE
                    noInternetHeader.noInternet.visibility = View.GONE
                    somethingWrongHeader.somethingWrong.visibility = View.GONE
                }
            }
            LoadingState.NoConnection -> {
                with(binding) {
                    swipeRefreshLayout.isRefreshing = false
                    noWalletsYet.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    noInternetHeader.noInternet.visibility = View.VISIBLE
                    somethingWrongHeader.somethingWrong.visibility = View.GONE
                    currencyContainer.visibility = View.GONE
                }
            }
            LoadingState.UnexpectedError -> {
                with(binding) {
                    swipeRefreshLayout.isRefreshing = false
                    noWalletsYet.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    noInternetHeader.noInternet.visibility = View.VISIBLE
                    somethingWrongHeader.somethingWrong.visibility = View.VISIBLE
                }

            }
            LoadingState.Ready -> {
                with(binding) {
                    swipeRefreshLayout.isRefreshing = false
                    noWalletsYet.isVisible = adapter.isEmpty
                    recyclerView.visibility = View.VISIBLE
                    noInternetHeader.noInternet.visibility = View.GONE
                }
            }
        }
    }

    private fun startWalletInfoActivity(walletId: Int, walletName: String) {
        val intent = Intent(activity, MainScreenActivity::class.java)
        intent.putExtra(WALLET_ID, walletId)
        intent.putExtra(WALLET_NAME, walletName)
        startActivity(intent)
    }

    private fun showRecordsText(show: Boolean) {
        binding.noWalletsYet.visibility = if (show) View.VISIBLE else View.GONE
    }
}
