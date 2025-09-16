package org.acme;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class TransactionProcessor {
    @Inject
    TransactionService service;

    @Incoming("my-in") // consume
    @Outgoing("my-out") // produce
    @Blocking
    public String consumeAndProcess(String message) {
        JsonObject json = new JsonObject(message);
        Transaction tx = service.processTransaction(
                json.getString("userId"),
                json.getDouble("amount")
        );
        return new JsonObject()
                .put("id", tx.id)
                .put("userId", tx.userId)
                .put("amount", tx.amount)
                .put("status", tx.status)
                .encode();
    }
}
