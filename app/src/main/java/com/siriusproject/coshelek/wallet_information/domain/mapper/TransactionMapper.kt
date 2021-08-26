package com.siriusproject.coshelek.wallet_information.domain.mapper

import com.siriusproject.coshelek.categories_info.domain.mappers.CategoriesMapper
import com.siriusproject.coshelek.wallet_information.data.model.TransactionRemoteModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel

class TransactionMapper {

    fun map(model: TransactionRemoteModel): TransactionUiModel {
        return TransactionUiModel(
            model.id, CategoriesMapper().invoke(listOf(model.category))[0],
            when (model.type) {
                "INCOME" -> TransactionType.Income
                else -> TransactionType.Expense
            },
            model.amount,
            model.currency,
            model.date
        )
    }

}