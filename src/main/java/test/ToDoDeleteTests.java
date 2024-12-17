package test;

import Generator.ModelGenerator;
import org.junit.jupiter.api.Test;

import static Helper.ModelHelper.findModelById;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoDeleteTests extends BaseTest {

    @Test
    public void deleteModel() throws Exception {
        var model = ModelGenerator.createToDoModel();

        var response =  httpClient.sendPostRequest(model);
        assertEquals(201, response.getStatusLine().getStatusCode());

        response = httpClient.sendDeleteRequest(model);
        assertEquals(204, response.getStatusLine().getStatusCode());

        var allModels = httpClient.sendGetRequestSerialized();

        var updatedModel =  findModelById(allModels, model.getId());;
        assertEquals(null, updatedModel);
    }

    @Test
    public void deleteNonExistingModel() throws Exception {
        var model = ModelGenerator.createToDoModel();
        model.setId(Integer.MAX_VALUE);

        var allModels = httpClient.sendGetRequestSerialized();

        var updatedModel =  findModelById(allModels, model.getId());;

        if (updatedModel != null){
            httpClient.sendDeleteRequest(model);
        }

        var response = httpClient.sendDeleteRequest(model);
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    public void deleteModelWithoutAuth() throws Exception {
        var model = ModelGenerator.createToDoModel();

        var response = httpClient.sendDeleteRequestWithoutAuth(model);
        assertEquals(401, response.getStatusLine().getStatusCode());
    }
}
