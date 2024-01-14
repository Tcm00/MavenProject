package com.example.demo.pojo;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author tangcanming
 * @date 2024/1/4 16:57
 * @describe
 */
public class GsonConfig extends TypeAdapter<StudentEntity> {
    @Override
    public void write(JsonWriter out, StudentEntity value) throws IOException {
        out.beginObject();
        out.name("name").value(value.getName());
        out.name("age").value(value.getName());
        out.name("email").value(value.getName());
        out.endObject();
    }

    @Override
    public StudentEntity read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
