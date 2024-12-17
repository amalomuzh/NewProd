package test;

import Generator.ModelGenerator;
import model.ToDoModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoPostTests extends BaseTest {

    @Test
    public void postNewModel() throws Exception {
       var  response =  httpClient.sendPostRequest(ModelGenerator.createToDoModel());
       assertEquals(201, response.getStatusLine().getStatusCode());
    }

    @Test
    public void postExistingModel() throws Exception {
        var allModels = httpClient.sendGetRequestSerialized();
        var model = new ToDoModel(allModels.get(0).getId(), allModels.get(0).getText(), allModels.get(0).isCompleted());

        var response =  httpClient.sendPostRequest(model);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void postInvalidValueModel() throws Exception {
        var model = ModelGenerator.createToDoModel();
        model.setId(-1);

        var response =  httpClient.sendPostRequest(model);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void postModelWithoutField() throws Exception {
        var model = ModelGenerator.createToDoModel();
        model.setText(null);

        var response =  httpClient.sendPostRequest(model);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

}
