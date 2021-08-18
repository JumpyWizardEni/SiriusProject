package com.siriusproject.coshelek

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

enum class CategoryType{ INCOME, OUTCOME }
data class CategoryModel(val id: Int, val name: String, val type: CategoryType, val picture: Drawable, val color: Int)

class CategorySelectFragment(): Fragment() {

    private val categories: MutableList<CategoryModel> = mutableListOf()
    var selectedCat = 0
        private set
    var showCatType: CategoryType = CategoryType.INCOME
        private set
    private lateinit var catListAdapter: CategoriesListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.category_selection, container, false)
        initType()
        initCategories()
        initRecyclerView(rootView)
        return rootView
    }

    companion object {
        private const val CATEGORY_TYPE_ARG = "cat_type"
        fun newInstance(isIncome: Boolean): CategorySelectFragment {
            val fragment = CategorySelectFragment()
            val arguments = Bundle()
            arguments.putBoolean(CATEGORY_TYPE_ARG, isIncome)
            fragment.arguments = arguments
            return fragment
        }
    }

    private fun initType(){
        val args = requireArguments()
        showCatType = if(args.getBoolean(CATEGORY_TYPE_ARG, true))
            CategoryType.INCOME else CategoryType.OUTCOME
    }

    private fun initCategories(){
        categories.add(CategoryModel(
            id=0,
            name = "Зарплата",
            type = CategoryType.INCOME,
            picture = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cat_multivalue_cards)!!,
            color = Color.GREEN))
        categories.add(CategoryModel(
            id=0,
            name = "Зарплата",
            type = CategoryType.INCOME,
            picture = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cat_multivalue_cards)!!,
            color = Color.GREEN))
        categories.add(CategoryModel(
            id=0,
            name = "Зарплата",
            type = CategoryType.INCOME,
            picture = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cat_multivalue_cards)!!,
            color = Color.GREEN))
        categories.add(CategoryModel(
            id=0,
            name = "Зарплата",
            type = CategoryType.INCOME,
            picture = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cat_multivalue_cards)!!,
            color = Color.GREEN))
    }

    private fun initRecyclerView(rootView: View){
        catListAdapter = CategoriesListAdapter().apply {
            setHasStableIds(true)
        }
        recyclerView = rootView.findViewById(R.id.category_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = catListAdapter
        }
        catListAdapter.setData(categories)
    }

}