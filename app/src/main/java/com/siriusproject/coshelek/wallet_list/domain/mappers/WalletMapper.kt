package com.siriusproject.coshelek.wallet_list.domain.mappers

import com.siriusproject.coshelek.wallet_list.data.model.WalletDb
import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel
import java.math.BigDecimal

class WalletMapper {
    fun map(model: WalletRemoteModel): WalletUiModel {
        return WalletUiModel(
            model.id,
            model.name,
            model.balance ?: BigDecimal.ZERO,
            model.income ?: BigDecimal.ZERO,
            model.expense ?: BigDecimal.ZERO,
            model.currency,
            model.visibility ?: true,
            model.limit
        )
    }

    fun map(
        model: WalletRemoteModel,
        id: Int,
        currency: String,
        visibility: Boolean
    ): WalletUiModel {
        return WalletUiModel(
            id,
            model.name,
            model.balance ?: BigDecimal.ZERO,
            model.income ?: BigDecimal.ZERO,
            model.expense ?: BigDecimal.ZERO,
            currency,
            visibility,
            model.limit
        )
    }

    fun map(
        model: WalletDb,
    ): WalletUiModel {
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