package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TransactionService {

    @Inject
    TransactionRepository transactionRepository;

    @Transactional
    public Transaction processTransaction(String userId, Double amount) {
        Transaction tx = new Transaction();
        tx.userId = userId;
        tx.amount = amount;
        tx.status = (amount > 1000) ? "HIGH_VALUE" : "NORMAL";
        transactionRepository.persist(tx);
        return tx;
    }
}
