package com.example.walletservice.application.port.out

import com.example.walletservice.domain.Wallet


interface LoadWalletPort {

  fun getWallets(sellerIds: Set<Long>): Set<Wallet>
}