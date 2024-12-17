package clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.ToDoModel;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.util.List;


public class CustomHttpClient {
    private final HttpClient httpClient;
    private final String url;
    private final BasicHeader header;
    private ObjectMapper objectMapper = new ObjectMapper();

    public CustomHttpClient(HttpClient httpClient, String url, BasicHeader header) {
        this.httpClient = httpClient;
        this.url = url;
        this.header = header;
    }

    public HttpResponse sendGetRequest() throws Exception {
        var request = new HttpGet(url);

        return httpClient.execute(request);
    }

    public List<ToDoModel> sendGetRequestSerialized() throws Exception {
        var request = new HttpGet(url);

        var response =  httpClient.execute(request);
        String jsonResponse = EntityUtils.toString(response.getEntity());

        var toDoModels =  objectMapper.readValue(jsonResponse,  new TypeReference<List<ToDoModel>>() {});
        return toDoModels;
    }

    public HttpResponse sendGetRequest(Integer offset, Integer limit) throws Exception {
        var uriBuilder = new URIBuilder(url);

        uriBuilder.addParameter("offset", String.valueOf(offset));
        uriBuilder.addParameter("limit", String.valueOf(limit));

        var request = new HttpGet(uriBuilder.toString());
        return httpClient.execute(request);
    }

    public HttpResponse sendPostRequest(ToDoModel model) throws Exception {
        var request = new HttpPost(url);

        request.addHeader("Content-Type", "application/json");

        var entity = new StringEntity(objectMapper.writeValueAsString(model));
        request.setEntity(entity);

        return httpClient.execute(request);
    }

    public HttpResponse sendPutRequest(ToDoModel model) throws Exception {
        var request = new HttpPut(url + "/" + model.getId());

        request.addHeader("Content-Type", "application/json");

        var entity = new StringEntity(objectMapper.writeValueAsString(model));
        request.setEntity(entity);

        return httpClient.execute(request);
    }

    public HttpResponse sendDeleteRequest(ToDoModel model) throws Exception {
        var request = new HttpDelete(url + "/" + model.getId());

        request.addHeader(header);
        return httpClient.execute(request);
    }

    public HttpResponse sendDeleteRequestWithoutAuth(ToDoModel model) throws Exception {
        var request = new HttpDelete(url + "/" + model.getId());

        return httpClient.execute(request);
    }
}