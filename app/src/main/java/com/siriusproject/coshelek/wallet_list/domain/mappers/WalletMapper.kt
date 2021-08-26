package com.siriusproject.coshelek.wallet_list.domain.mappers

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
            model.limit,
            model.isLimitReached ?: false
        )
    }
}
