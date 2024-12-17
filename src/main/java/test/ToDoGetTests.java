package test;

import Generator.ModelGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import model.ToDoModel;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static Helper.ModelHelper.findModelById;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoGetTests extends BaseTest {

    @BeforeAll
    public static void addToDOModel() throws Exception {
        for (var i = 0;i < 2; i++) {
            httpClient.sendPostRequest(ModelGenerator.createToDoModel());
        }
    }

    @Test
    public void getRequestWithoutParameters() throws Exception {
        HttpResponse response = httpClient.sendGetRequest();
        assertEquals(200, response.getStatusLine().getStatusCode());

        var jsonResponse = EntityUtils.toString(response.getEntity());

        var toDoModels =  objectMapper.readValue(jsonResponse,  new TypeReference<List<ToDoModel>>() {});
        assertFalse(toDoModels.isEmpty());
    }

    @Test
    public void getRequestWithParameters() throws Exception {
        var allModels = httpClient.sendGetRequestSerialized();

        var response = httpClient.sendGetRequest(1,1);
        assertEquals(200, response.getStatusLine().getStatusCode());
        var jsonResponse = EntityUtils.toString(response.getEntity());

        var toDoModels =  objectMapper.readValue(jsonResponse,  new TypeReference<List<ToDoModel>>() {});

        var sameModel =  findModelById(toDoModels, allModels.get(0).getId());;

        assertNull(sameModel);
        assertTrue(toDoModels.size() == 1);
    }

    @Test
    public void getRequestWithoutInvalidParameters() throws Exception {
        var response = httpClient.sendGetRequest(-1,-1);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }
}