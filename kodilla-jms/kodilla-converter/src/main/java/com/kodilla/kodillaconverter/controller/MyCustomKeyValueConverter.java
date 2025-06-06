package com.kodilla.kodillaconverter.controller;

import com.kodilla.kodillaconverter.domain.MyCustomClass;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MyCustomKeyValueConverter implements HttpMessageConverter<Object> {


    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return clazz.equals(MyCustomClass.class) && mediaType.getSubtype().equals("plain")
                && mediaType.getType().equals("text");
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.equals(MyCustomClass.class) && mediaType.getSubtype().equals("plain")
                && mediaType.getType().equals("text");
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.ALL);
    }

    @Override
    public Object read(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputMessage.getBody()))) {
            int c;
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
        }

        String[] fields = builder.toString().split(";");
        String fieldOne = null, fieldTwo = null, fieldThree = null;

        for (String field : fields) {
            String[] keyValue = field.split("=");
            if (keyValue.length == 2) {
                switch (keyValue[0]) {
                    case "fieldOne":
                        fieldOne = keyValue[1];
                        break;
                    case "fieldTwo":
                        fieldTwo = keyValue[1];
                        break;
                    case "fieldThree":
                        fieldThree = keyValue[1];
                        break;
                }
            }
        }

        return new MyCustomClass(fieldOne, fieldTwo, fieldThree);
    }

    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
