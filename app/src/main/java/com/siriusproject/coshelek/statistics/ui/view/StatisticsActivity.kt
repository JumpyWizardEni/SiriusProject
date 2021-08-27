package com.siriusproject.coshelek.statistics.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.siriusproject.coshelek.R
import com.siriusproject.coshelek.databinding.ActivityStatisticsBinding
import com.siriusproject.coshelek.statistics.ui.view.viewmodel.StatisticsViewModel
import com.siriusproject.coshelek.utils.collectWhenStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class StatisticsActivity : AppCompatActivity(R.layout.activity_statistics) {

    private val binding by viewBinding(ActivityStatisticsBinding::bind)
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.catStats.map {
            it.map { ValueDataEntry(it.category, it.amount) }
        }.collectWhenStarted(this) {
            setupCategoryStats(it)
        }
    }

    private fun setupCategoryStats(statistics: List<ValueDataEntry>) {
        val cartesian: Cartesian = AnyChart.column()

        val data: MutableList<DataEntry> = ArrayList()

        data.addAll(statistics)

        val column: Column = cartesian.column(data)

        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("Р{%Value}{groupsSeparator: }")

        cartesian.animation(true)

        cartesian.yScale().minimum(0.0)

        cartesian.yAxis(0).labels().format("P{%Value}{groupsSeparator: }")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title(getString(R.string.categories))
        cartesian.yAxis(0).title(getString(R.string.sum))

        binding.anyChartView.setChart(cartesian)
    }

}