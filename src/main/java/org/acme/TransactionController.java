package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionController {
    @Inject
    TransactionService service;

    @GET
    public List<Transaction> list() {
        return Transaction.listAll();
    }

    @POST
    public Transaction create(Transaction request) {
        return service.processTransaction(request.userId, request.amount);
    }
}
