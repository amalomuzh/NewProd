package test;

import clients.CustomHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import Helper.PropertiesReaderHelper;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

public abstract class BaseTest {

    protected ObjectMapper objectMapper = new ObjectMapper();
    static final BasicHeader header = new BasicHeader("Authorization", PropertiesReaderHelper.getAuthHeader());
    protected static CustomHttpClient httpClient = new CustomHttpClient(HttpClients.createDefault(),
            PropertiesReaderHelper.getUrl(), header);;
}