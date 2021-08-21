package com.siriusproject.coshelek.wallet_list.domain.mappers

import com.siriusproject.coshelek.wallet_list.data.model.WalletInfoRemoteModel
import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel

class WalletMapper {
    fun map(model: WalletRemoteModel): WalletUiModel {
        return WalletUiModel(
            model.id,
            model.name,
            model.balance,
            model.income,
            model.expense,
            model.currency,
            model.visibility,
            model.limit
        )
    }

    fun map(
        model: WalletInfoRemoteModel,
        id: Int,
        currency: String,
        visibility: Boolean
    ): WalletUiModel {
        return WalletUiModel(
            id,
            model.name,
            model.balance,
            model.income,
            model.expense,
            currency,
            visibility,
            model.limit
        )
    }
}