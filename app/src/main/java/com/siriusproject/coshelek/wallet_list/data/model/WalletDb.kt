package com.siriusproject.coshelek.wallet_list.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class WalletDb (
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val name: String,
        val balance: BigDecimal,
        val income: BigDecimal,
        val expense: BigDecimal,
        val currency: String,
        val visibility: Boolean,
        val limit: BigDecimal
)