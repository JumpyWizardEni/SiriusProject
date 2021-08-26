package com.siriusproject.coshelek.categories_info.ui.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.categories_info.data.model.CategoryIcon
import com.siriusproject.coshelek.categories_info.domain.mappers.CategoriesMapper
import com.siriusproject.coshelek.categories_info.ui.adapters.CategoriesIconListAdapter
import com.siriusproject.coshelek.categories_info.ui.view.view_models.CategoryAddViewModel
import com.siriusproject.coshelek.databinding.FragmentCategoryAddBinding
import com.siriusproject.coshelek.utils.collectWhenStarted
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import kotlinx.coroutines.flow.filterNotNull

class CategoryAddFragment : Fragment(R.layout.fragment_category_add) {

    private val binding by viewBinding(FragmentCategoryAddBinding::bind)
    private val viewModel: CategoryAddViewModel by activityViewModels()
    private lateinit var iconsAdapter: CategoriesIconListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setOnClickListeners()
        subscribeOnViewModel()
    }

    private fun subscribeOnViewModel() {
        viewModel.colorState.filterNotNull().collectWhenStarted(this) {
            setColor(it)
        }
        viewModel.iconState.filterNotNull().collectWhenStarted(this) {
            //iconsAdapter.setCurrentIcon(it)
        }
        viewModel.categoryCreateState.filterNotNull().collectWhenStarted(this) {
            binding.createCategoryButton.isEnabled = it
        }
        viewModel.nameState.filterNotNull().collectWhenStarted(this) {
            binding.categoryTitleText.text = it
        }
        viewModel.typeState.filterNotNull().collectWhenStarted(this) {
            binding.categoryTypeText.text = when (it) {
                TransactionType.Income -> resources.getString(R.string.income)
                TransactionType.Expense -> resources.getString(R.string.outcome)
            }
        }
    }

    private fun setOnClickListeners() {
        binding.createCategoryButton.setOnClickListener {
            viewModel.onCategoryCreatePressed { activity?.onBackPressed() }
        }
        binding.createCategoryButton.isEnabled = false
        binding.toolbarLayout.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        binding.categoryTitleLayout.setOnClickListener {
            viewModel.onCategoryTitleButton()
        }
        binding.categoryTypeLayout.setOnClickListener {
            viewModel.onCategoryTypeButton()
        }
        viewModel.pushCategoryColor(ContextCompat.getColor(requireContext(), R.color.blue))
        binding.categoryColor.setOnClickListener {
            pickColor()
        }
    }

    private fun initRecyclerView() {
        iconsAdapter = CategoriesIconListAdapter(::onCategoryIconSelected)
        binding.categoriesIconList.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 5, LinearLayoutManager.VERTICAL, false)
            adapter = iconsAdapter
        }
        iconsAdapter.setIcons(getIcons())
    }

    private fun getIcons() =
        CategoriesMapper
            .pictureIDbyNameMap
            .keys
            .map { CategoryIcon(CategoriesMapper.pictureIDbyNameMap[it]!!, it) }

    private fun onCategoryIconSelected(icon: CategoryIcon) {
        viewModel.pushCategoryIcon(icon)
    }

    private fun setColor(color: Int) {
        binding.categoryColor.setTextColor(color)
        iconsAdapter.updateIconsColor(color)
    }

    private fun pickColor() {
        MaterialColorPickerDialog
            .Builder(requireContext())
            .setTitle(resources.getString(R.string.select_color))
            .setColorShape(ColorShape.CIRCLE)
            .setColorSwatch(ColorSwatch._300)
            .setDefaultColor(R.color.blue)
            .setColorListener { color, _ ->
                viewModel.pushCategoryColor(color)
            }
            .show()
    }


}