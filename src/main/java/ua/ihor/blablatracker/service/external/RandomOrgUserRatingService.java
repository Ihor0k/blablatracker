package ua.ihor.blablatracker.service.external;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class RandomOrgUserRatingService extends AbstractExternalUserRatingService {
    private static final String API_URL = "https://api.random.org/json-rpc/2/invoke";

    @Value("${user.rating.random-org.api-key}")
    private String apiKey;

    private URL url;
    private JsonFactory jsonFactory;

    @PostConstruct
    private void init() {
        jsonFactory = new JsonFactory();
    }

    @Override
    protected int makeRequest() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) getUrl().openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        writeRequestBody(connection);
        return parseResponseBody(connection);
    }

    private void writeRequestBody(HttpURLConnection connection) throws IOException {
        try (
                OutputStream outputStream = connection.getOutputStream();
                JsonGenerator generator = jsonFactory.createGenerator(outputStream)
        ) {
            generator.writeStartObject();
            generator.writeStringField("jsonrpc", "2.0");
            generator.writeStringField("method", "generateIntegers");
            generator.writeObjectFieldStart("params");
            generator.writeStringField("apiKey", apiKey);
            generator.writeNumberField("n", 1);
            generator.writeNumberField("min", 1);
            generator.writeNumberField("max", 3);
            generator.writeEndObject();
            generator.writeNumberField("id", 1);
        }
    }

    private int parseResponseBody(HttpURLConnection connection) throws IOException {
        try (
                InputStream inputStream = connection.getInputStream();
                JsonParser jsonParser = jsonFactory.createParser(inputStream)
        ) {
            JsonToken token;
            while ((token = jsonParser.nextToken()) != JsonToken.END_OBJECT) {
                if (token == JsonToken.FIELD_NAME && jsonParser.getCurrentName().equals("result")) {
                    jsonParser.nextToken();
                    while ((token = jsonParser.nextToken()) != JsonToken.END_OBJECT) {
                        if (token == JsonToken.FIELD_NAME && jsonParser.getCurrentName().equals("data")) {
                            if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
                                jsonParser.nextToken();
                                return jsonParser.getIntValue();
                            }
                        }
                    }
                }
            }
            throw new IOException("Can't parse response body");
        }
    }

    private URL getUrl() throws MalformedURLException {
        if (url == null) {
            url = new URL(API_URL);
        }
        return url;
    }
}
