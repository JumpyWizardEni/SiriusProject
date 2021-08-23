package com.siriusproject.coshelek.wallet_information.domain.mapper

import com.siriusproject.coshelek.wallet_information.data.model.TransactionRemoteModel
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionType
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionUiModel

class TransactionMapper {

    fun map(model: TransactionRemoteModel): TransactionUiModel {
        return TransactionUiModel(
            model.id,
            model.name,
            model.category,
            when (model.type) {
                "INCOME" -> TransactionType.Income
                else -> TransactionType.Expence
            },
            model.amount,
            model.currency,
            model.date
        )
    }

}