package test;

import Generator.ModelGenerator;
import org.junit.jupiter.api.Test;

import static Helper.ModelHelper.findModelById;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoPutTests extends BaseTest {

    @Test
    public void putExistingModel() throws Exception {
        var model = ModelGenerator.createToDoModel();

        var response =  httpClient.sendPostRequest(model);
        assertEquals(201, response.getStatusLine().getStatusCode());

        var newTextValue = "test";
        model.setText(newTextValue);

        response =  httpClient.sendPutRequest(model);
        assertEquals(200, response.getStatusLine().getStatusCode());

        var allModels = httpClient.sendGetRequestSerialized();

        var updatedModel = findModelById(allModels, model.getId());;
        assertEquals(newTextValue, updatedModel.getText());
    }

    @Test
    public void putModelToInvalidValue() throws Exception {
        var model = ModelGenerator.createToDoModel();

        var response =  httpClient.sendPostRequest(model);
        assertEquals(201, response.getStatusLine().getStatusCode());

        model.setText(null);

        response =  httpClient.sendPutRequest(model);
        assertEquals(401, response.getStatusLine().getStatusCode());
    }

    @Test
    public void putNonExistingModel() throws Exception {
        var model = ModelGenerator.createToDoModel();
        model.setId(Integer.MAX_VALUE);

        var allModels = httpClient.sendGetRequestSerialized();
        var updatedModel = findModelById(allModels, model.getId());;

        if (updatedModel != null){
            httpClient.sendDeleteRequest(model);
        }

        var response =  httpClient.sendPutRequest(model);
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}
