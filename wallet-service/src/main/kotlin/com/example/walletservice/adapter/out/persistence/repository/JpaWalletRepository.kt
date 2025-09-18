package com.example.walletservice.adapter.out.persistence.repository


import com.example.walletservice.adapter.out.persistence.entity.JpaWalletEntity
import com.example.walletservice.adapter.out.persistence.entity.JpaWalletMapper
import com.example.walletservice.domain.Wallet
import com.example.walletservice.wallet.adapter.out.persistence.repository.WalletRepository
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class JpaWalletRepository (
    private val springDataJpaWalletRepository: SpringDataJpaWalletRepository,
    private val jpaWalletMapper: JpaWalletMapper,
    private val walletTransactionRepository: WalletTransactionRepository,
) : WalletRepository {
    override fun getWallets(sellerIds: Set<Long>): Set<Wallet> {
        return springDataJpaWalletRepository.findByUserIdIn( sellerIds )
            .map { jpaWalletMapper.mapToDomainEntity(it) }
            .toSet()
    }

    @Transactional
    override fun save(wallets: List<Wallet>) {
        springDataJpaWalletRepository.saveAll(wallets.map { jpaWalletMapper.mapToJpaEntity(it) })
        walletTransactionRepository.save(wallets.flatMap { it.walletTransactions }.toList())
    }
}

interface SpringDataJpaWalletRepository : JpaRepository<JpaWalletEntity, Long> {

  fun findByUserIdIn(userIds: Set<Long>): List<JpaWalletEntity>
}
