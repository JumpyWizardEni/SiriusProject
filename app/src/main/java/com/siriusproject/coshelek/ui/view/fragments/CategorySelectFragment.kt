package com.siriusproject.coshelek

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.data.model.CategoryUiModel
import com.siriusproject.coshelek.data.model.TransactionType
import com.siriusproject.coshelek.ui.view.TransactionViewModel


class CategorySelectFragment: Fragment() {

    private val categories: MutableList<CategoryUiModel> = mutableListOf()
    private var showCatType: TransactionType = TransactionType.Income
    private lateinit var catListAdapter: CategoriesListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonNext: Button

    private val viewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_category_selection, container, false)
        buttonNext = rootView.findViewById(R.id.cat_next_btn)
        buttonNext.setOnClickListener { viewModel.onCategoryNextButton() }
        initType()
        initRecyclerView(rootView)
        initCategories()
        return rootView
    }

    private fun initType(){
        showCatType = viewModel.transactionModel!!.type
    }

    private fun initCategories(){
        categories.addAll(
            viewModel.getCategories()
                .map { CategoryUiModel(
                    id = it.id,
                    name = it.name,
                    type = if(it.type==1) TransactionType.Income else TransactionType.Expence,
                    picture = Drawable.createFromPath(it.picture)!!,
                    color = it.color) }
                .filter { it.type ==  showCatType }
        )
        catListAdapter.setData(categories)
    }

    private fun initRecyclerView(rootView: View){
        catListAdapter = CategoriesListAdapter(::onCategorySelected)
        recyclerView = rootView.findViewById(R.id.category_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = catListAdapter
        }
    }

    private fun onCategorySelected(cat: CategoryUiModel){
        viewModel.transactionModel!!.category = cat
    }

}