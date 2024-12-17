package test;

import Generator.ModelGenerator;
import clients.WebSocketClient;
import model.KafkaMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;

import static Helper.PropertiesReaderHelper.getUrlWs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ToDoWsTests extends BaseTest {

    private WebSocketClient client;

    @BeforeEach
    public void setUp() throws Exception {
        client = new WebSocketClient(URI.create(getUrlWs()));
        client.getConnectionFuture().join();
    }

    @Test
    public void wsCreatedNewToDo () throws Exception {
        var model = ModelGenerator.createToDoModel();
        httpClient.sendPostRequest(model);

        var receivedMessages = client.getMessages();

        ArrayList<KafkaMessage> kafkaMessages = new ArrayList<>();

        for (var msg:receivedMessages) {

            try {
                var kafkaMsg = objectMapper.readValue(msg, KafkaMessage.class);
                kafkaMessages.add(kafkaMsg);
            }
            catch (Exception exception){

            }
        }
        assertFalse(kafkaMessages.isEmpty());

        var createdModelEvent = kafkaMessages.stream().filter(x -> x.getData().getId() == model.getId()).findFirst().get();

        assertEquals(createdModelEvent.getType(), "new_todo");
        assertEquals(createdModelEvent.getData().getText(), model.getText());
        assertEquals(createdModelEvent.getData().isCompleted(), model.isCompleted());
    }
}