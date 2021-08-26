package com.siriusproject.coshelek.categories_info.ui.view.fragments

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.siriusproject.coshelek.utils.LoadingState
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
        viewModel.colorState.collectWhenStarted(this) {
            Log.d(javaClass.name, "color recieved: $it")
            if (it == null)
                viewModel.pushCategoryColor(ContextCompat.getColor(requireContext(), R.color.blue))
            else
                setColor(it)
        }
        viewModel.iconState.filterNotNull().collectWhenStarted(this) {
            Log.d(javaClass.name, "icon recieved: ${it.id}")
            iconsAdapter.setCurrentIcon(it)
        }
        viewModel.categoryCreateState.filterNotNull().collectWhenStarted(this) {
            Log.d(javaClass.name, "ADDING ENABLED: $it")
            binding.createCategoryButton.isEnabled = it
        }
        viewModel.nameState.filterNotNull().collectWhenStarted(this) {
            Log.d(javaClass.name, "name recieved: $it")
            binding.categoryTitleText.text = it
        }
        viewModel.typeState.filterNotNull().collectWhenStarted(this) {
            Log.d(javaClass.name, "type recieved: $it")
            binding.categoryTypeText.text = when (it) {
                TransactionType.Income -> resources.getString(R.string.income)
                TransactionType.Expense -> resources.getString(R.string.outcome)
            }
        }
        viewModel.addCategoryState.collectWhenStarted(this) {
            when (it) {
                LoadingState.NoConnection, LoadingState.UnexpectedError -> {
                    binding.createCategoryButton.isEnabled = true
                    Toast.makeText(context, getString(R.string.try_again), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setOnClickListeners() {
        with(binding) {
            createCategoryButton.setOnClickListener {
                createCategoryButton.isEnabled = false
                viewModel.onCategoryCreatePressed { activity?.onBackPressed() }
            }
            createCategoryButton.isEnabled = false
            toolbarLayout.toolbar.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            categoryTitleLayout.setOnClickListener {
                viewModel.onCategoryTitleButton()
            }
            categoryTypeLayout.setOnClickListener {
                viewModel.onCategoryTypeButton()
            }
            categoryColor.setOnClickListener {
                pickColor()
            }
        }
    }

    private fun initRecyclerView() {
        iconsAdapter = CategoriesIconListAdapter(::onCategoryIconSelected)
        binding.categoriesIconList.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 7, LinearLayoutManager.VERTICAL, false)
            adapter = iconsAdapter
        }
        iconsAdapter.setIcons(getIcons())
        binding.categoriesIconList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val offset = requireContext().getResources()
                    .getDimensionPixelOffset(R.dimen.half_normal_margin)
                outRect.top = offset
                outRect.bottom = offset
            }
        })
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