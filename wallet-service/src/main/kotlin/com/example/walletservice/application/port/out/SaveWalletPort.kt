package com.example.walletservice.application.port.out

import com.example.walletservice.domain.Wallet

interface SaveWalletPort {

  fun save(wallets: List<Wallet>)
}