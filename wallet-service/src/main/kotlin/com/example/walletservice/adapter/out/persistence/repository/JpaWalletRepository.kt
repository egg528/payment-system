package com.example.walletservice.adapter.out.persistence.repository

import com.example.walletservice.adapter.out.persistence.entity.JpaWalletEntity
import com.example.walletservice.adapter.out.persistence.entity.JpaWalletMapper
import com.example.walletservice.domain.Wallet
import com.example.walletservice.wallet.adapter.out.persistence.repository.WalletRepository
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.support.TransactionTemplate
import java.math.BigDecimal

@Repository
class JpaWalletRepository(
    private val springDataJpaWalletRepository: SpringDataJpaWalletRepository,
    private val jpaWalletMapper: JpaWalletMapper,
    private val walletTransactionRepository: WalletTransactionRepository,
    private val transactionTemplate: TransactionTemplate,
    private val retryTemplate: RetryTemplate
) : WalletRepository {
    override fun getWallets(sellerIds: Set<Long>): Set<Wallet> {
        return springDataJpaWalletRepository.findByUserIdIn( sellerIds )
            .map { jpaWalletMapper.mapToDomainEntity(it) }
            .toSet()
    }

    override fun save(wallets: List<Wallet>) {
        retryTemplate.execute<Unit, OptimisticLockingFailureException> { context ->
            if (context.retryCount == 0) {
                performInitialSave(wallets)
            } else {
                performRetrySave(wallets)
            }
        }
    }

    private fun performInitialSave(wallets: List<Wallet>) {
        transactionTemplate.execute {
            springDataJpaWalletRepository.saveAll(wallets.map { jpaWalletMapper.mapToJpaEntity(it) })
            walletTransactionRepository.save(wallets.flatMap { it.walletTransactions })
        }
    }

    private fun performRetrySave(wallets: List<Wallet>) {
        val recentWalletsById = springDataJpaWalletRepository
            .findByIdIn(wallets.map { it.id }.toSet())
            .associateBy { it.id }

        val updatedWallets = wallets.map { wallet ->
            val recentWallet = recentWalletsById[wallet.id]
                ?: throw IllegalStateException("Wallet not found: ${wallet.id}")

            recentWallet.addBalance(
                BigDecimal(wallet.walletTransactions.sumOf { it.amount })
            )
        }

        transactionTemplate.execute {
            springDataJpaWalletRepository.saveAll(updatedWallets)
            walletTransactionRepository.save(wallets.flatMap { it.walletTransactions })
        }
    }
}

interface SpringDataJpaWalletRepository : JpaRepository<JpaWalletEntity, Long> {

  fun findByUserIdIn(userIds: Set<Long>): List<JpaWalletEntity>

  fun findByIdIn(ids: Set<Long>): List<JpaWalletEntity>
}
