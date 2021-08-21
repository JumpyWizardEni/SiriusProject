package com.siriusproject.coshelek.wallet_list.domain.use_cases

import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepository
import javax.inject.Inject

class CreateWalletUseCaseImpl @Inject constructor(private val walletsRepository: WalletsRepository) :
    CreateWalletUseCase {
}