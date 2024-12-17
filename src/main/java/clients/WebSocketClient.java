package clients;

import jakarta.websocket.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ClientEndpoint
public class WebSocketClient {

    private Session userSession = null;
    private List<String> messages = new ArrayList<>();

    private CompletableFuture<Void> connectionFuture = new CompletableFuture<>();

    public WebSocketClient(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.userSession = container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            e.printStackTrace();
            connectionFuture.completeExceptionally(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
        connectionFuture.complete(null);
    }

    public CompletableFuture<Void> getConnectionFuture() {
        return connectionFuture;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
        messages.add(message);
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("Session closed: " + reason);
        this.userSession = null;
    }

    @OnError
    public void onError(Session userSession, Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
    }

    public List<String> getMessages() {
        return messages;
    }
}