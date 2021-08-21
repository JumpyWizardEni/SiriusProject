package com.siriusproject.coshelek.wallet_list.domain.mappers

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
}