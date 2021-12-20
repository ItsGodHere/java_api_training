package fr.lernejo.navy_battle.server.utils;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URL;
import java.util.UUID;

public class JsonUtil {
    public boolean schemaValidate(String data) throws IOException {
        final JSONObject jsonSchema = new JSONObject(
            new JSONTokener(new FileInputStream(
                new File("src/main/resources/GameStart.json").getAbsolutePath())));
        JSONObject jsonData = new JSONObject( new JSONTokener(data) );
        final Schema schemaValidator = SchemaLoader.load(jsonSchema);
        try {
            schemaValidator.validate(jsonData);
            return true;
        }catch (ValidationException e) {
            return false;
        }
    }

    public String createResponseBody(URL url, String message) {
        final JSONObject responseBody = new JSONObject();
        responseBody.put("id", UUID.randomUUID().toString());
        responseBody.put("url", url.toString());
        responseBody.put("message", message);
        return responseBody.toString();
    }

    public String createFireRequestBody(String consequence, boolean isShipLeft) {
        final JSONObject responseBody = new JSONObject();
        responseBody.put("consequence", consequence);
        responseBody.put("shipLeft", isShipLeft);
        return responseBody.toString();
    }
}
