package com.siriusproject.coshelek.wallet_information.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.siriusproject.coshelek.databinding.FragmentCategorySelectionBinding
import com.siriusproject.coshelek.wallet_information.data.model.CategoryUiModel
import com.siriusproject.coshelek.wallet_information.ui.adapters.CategoriesListAdapter
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategorySelectFragment : Fragment() {

    private lateinit var catListAdapter: CategoriesListAdapter
    private lateinit var viewBinding: FragmentCategorySelectionBinding
    private val viewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCategorySelectionBinding.inflate(inflater, container, false)
        viewBinding.catNextBtn.setOnClickListener { viewModel.onCategoryNextButton() }
        initRecyclerView()
        initCategories()
        return viewBinding.root
    }

    private fun initCategories() {
        val categories = mutableListOf<CategoryUiModel>()
        categories.addAll(
            viewModel.getCategories()
                .filter { it.type == viewModel.transactionModel!!.type }
        )
        catListAdapter.setData(categories)
    }

    private fun initRecyclerView() {
        catListAdapter = CategoriesListAdapter(::onCategorySelected)
        viewBinding.categoryList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = catListAdapter
        }
    }

    private fun onCategorySelected(cat: CategoryUiModel) {
        viewModel.transactionModel!!.category = cat
    }

}