package com.siriusproject.coshelek

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siriusproject.coshelek.data.model.CategoryUiModel
import com.siriusproject.coshelek.data.model.TransactionType


class CategorySelectFragment: Fragment() {

    private val categories: MutableList<CategoryUiModel> = mutableListOf()
    private var showCatType: TransactionType = TransactionType.Income
    private lateinit var catListAdapter: CategoriesListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonNext: Button
    private lateinit var onNextPressed: ()->Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_category_selection, container, false)
        buttonNext = rootView.findViewById(R.id.cat_next_btn)
        buttonNext.setOnClickListener { onNextPressed() }
        initType()
        initRecyclerView(rootView)
        initCategories()
        return rootView
    }

    companion object {
        private const val CATEGORY_TYPE_ARG = "cat_type"
        fun newInstance(catType: TransactionType, onNextPressed: ()->Unit): CategorySelectFragment {
            val fragment = CategorySelectFragment()
            val arguments = Bundle()
            arguments.putSerializable(CATEGORY_TYPE_ARG, catType)
            fragment.arguments = arguments
            fragment.onNextPressed = onNextPressed
            return fragment
        }
    }

    private fun initType(){
        val args = requireArguments()
        showCatType = args.getSerializable(CATEGORY_TYPE_ARG) as TransactionType
    }

    private fun initCategories(){
        val loadedCats = mutableListOf<CategoryUiModel>()
        loadedCats.add(
            CategoryUiModel(
                id=0,
                name = "Зарплата",
                type = TransactionType.Income,
                picture = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cat_multivalue_cards)!!,
                color = Color.GREEN)
        )
        loadedCats.add(
            CategoryUiModel(
                id=0,
                name = "Подработка",
                type = TransactionType.Income,
                picture = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cat_multivalue_cards)!!,
                color = Color.GREEN)
        )
        loadedCats.add(
            CategoryUiModel(
                id=0,
                name = "Подарок",
                type = TransactionType.Income,
                picture = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cat_other_gift)!!,
                color = Color.GREEN)
        )
        loadedCats.add(
            CategoryUiModel(
                id=0,
                name = "Капитализация",
                type = TransactionType.Income,
                picture = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cat_other_percent)!!,
                color = Color.GREEN)
        )
        categories.addAll(loadedCats.filter { it.type ==  showCatType })
        catListAdapter.setData(categories)
    }

    private fun initRecyclerView(rootView: View){
        catListAdapter = CategoriesListAdapter(::onCategorySelected).apply {
            setHasStableIds(true)
        }
        recyclerView = rootView.findViewById(R.id.category_list)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = catListAdapter
        }
    }

    private fun onCategorySelected(cat: CategoryUiModel){
        Toast.makeText(requireContext(), "${cat.name} selected", Toast.LENGTH_SHORT).show()
    }

}