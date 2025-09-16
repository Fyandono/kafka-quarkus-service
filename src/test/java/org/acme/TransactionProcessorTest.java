package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.junit.jupiter.api.Test;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class TransactionProcessorTest {
    @Inject
    @Channel("my-in")
    Emitter<String> myInEmitter;

    // Queue untuk menyimpan pesan yang diterima dari topik 'my-output-topic'
    private static final BlockingQueue<String> receivedMessages = new LinkedBlockingQueue<>();

    @Incoming("my-out")
    public void receive(String message) {
        receivedMessages.offer(message);
    }

    @Test
    public void testHighValueTransaction() throws Exception {
        // Pesan yang akan dikirim
        String messageToSend = "{\"userId\":\"user-123\", \"amount\":1500.0}";

        // Mengirim pesan ke channel 'my-in'
        myInEmitter.send(messageToSend);

        // Menunggu pesan diterima dari channel 'my-out'
        String receivedMessage = receivedMessages.poll(10, TimeUnit.SECONDS);

        assertNotNull(receivedMessage, "Pesan tidak diterima dari channel 'my-out' dalam 10 detik.");
        System.out.println("Pesan diterima: " + receivedMessage);

        // Memastikan pesan yang diterima mengandung status yang benar
        assertTrue(receivedMessage.contains("\"status\":\"HIGH_VALUE\""));
        assertTrue(receivedMessage.contains("\"userId\":\"user-123\""));
    }
}
