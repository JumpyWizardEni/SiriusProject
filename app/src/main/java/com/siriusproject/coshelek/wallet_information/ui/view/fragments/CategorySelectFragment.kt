package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.categories_info.data.model.CategoryUiModel
import com.siriusproject.coshelek.categories_info.ui.view.AddCategoryActivity
import com.siriusproject.coshelek.databinding.FragmentCategorySelectionBinding
import com.siriusproject.coshelek.utils.LoadResult
import com.siriusproject.coshelek.utils.collectWhenStarted
import com.siriusproject.coshelek.wallet_information.ui.adapters.CategoriesListAdapter
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import com.siriusproject.coshelek.wallet_list.ui.view.view_models.WalletListViewModel.Companion.PREVIOUS_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategorySelectFragment : Fragment(R.layout.fragment_category_selection) {

    private val binding by viewBinding(FragmentCategorySelectionBinding::bind)
    private lateinit var catListAdapter: CategoriesListAdapter
    private val viewModel: TransactionViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.categoriesOpened()

        setHasOptionsMenu(true)
        binding.catToolbar.toolbar.inflateMenu(R.menu.select_category_menu)
        binding.catToolbar.toolbar.menu.findItem(R.id.add_category).setOnMenuItemClickListener {
            it.isEnabled = false
            startActivity(Intent(activity, AddCategoryActivity::class.java))
            true
        }

        initRecyclerView()
        initCategories()

        binding.catNextBtn.setOnClickListener {
            binding.catNextBtn.isEnabled = false
            viewModel.onCategoryReadyPressed(requireArguments().getInt(PREVIOUS_FRAGMENT))
        }

        binding.catNextBtn.isEnabled = false
        binding.catToolbar.toolbar.title = getString(R.string.pick_category)
        binding.catToolbar.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.categoriesOpened()
        }
    }

    private fun initCategories() {
        viewModel.categories.collectWhenStarted(this, { state ->
            when (state) {
                is LoadResult.Success -> {
                    catListAdapter.setData(state.data)
                    binding.swipeRefreshLayout.isRefreshing = false
                    showDataReady()
                }
                is LoadResult.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    showError()
                }
                is LoadResult.NoConnection -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    showNoConnection()
                }
                is LoadResult.Loading -> {
                    showLoading()
                }
            }
        })
    }

    private fun showLoading() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun showNoConnection() {
        //TODO("Not yet implemented")
    }

    private fun showError() {
        binding.catNextBtn.isEnabled = true
        Toast.makeText(requireContext(), getString(R.string.loading_error), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showDataReady() {
        //TODO("Not yet implemented")
    }

    private fun initRecyclerView() {
        catListAdapter = CategoriesListAdapter(::onCategorySelected)
        binding.categoryList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = catListAdapter
        }
    }

    private fun onCategorySelected(cat: CategoryUiModel?, selected: Boolean) {
        binding.catNextBtn.isEnabled = selected
        viewModel.pushCategory(cat)
    }

}
