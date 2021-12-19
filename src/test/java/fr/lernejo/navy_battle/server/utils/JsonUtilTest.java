package fr.lernejo.navy_battle.server.utils;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

class JsonUtilTest {
    @Test
    void test_schemaValidate_with_validData() throws IOException {
        JSONObject jsonSchema = new JSONObject(
            new JSONTokener(new FileInputStream(
                new File("src/test/resources/GameStartValid.json").getAbsolutePath())));
        Assertions.assertTrue(new JsonUtil().schemaValidate(jsonSchema.toString()));
    }

    @Test
    void test_schemaValidate_with_invalidData() throws IOException {
        JSONObject jsonSchema = new JSONObject(
            new JSONTokener(new FileInputStream(
                new File("src/test/resources/GameStartInvalid.json").getAbsolutePath())));
        Assertions.assertFalse(new JsonUtil().schemaValidate(jsonSchema.toString()));
    }

    @Test
    void test_createResponseBody() throws IOException {
        org.assertj.core.api.Assertions.assertThat(new JsonUtil().createResponseBody(
            new URL("http://localhost:9876"), "May the fate be with you")).isNotEmpty();
    }

    @Test
    void test_createFireResponseBody() throws FileNotFoundException {
        JSONObject jsonSchema = new JSONObject(
            new JSONTokener(new FileInputStream(
                new File("src/main/resources/Fire.json").getAbsolutePath())));
        String responseFire = (new JsonUtil().createFireRequestBody("sunk", true));
        JSONObject jsonData = new JSONObject( new JSONTokener(responseFire) );
        Schema schemaValidator = SchemaLoader.load(jsonSchema);
        schemaValidator.validate(jsonData);
    }
}
