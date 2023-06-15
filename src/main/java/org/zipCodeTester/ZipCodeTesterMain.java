package org.zipCodeTester;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ZipCodeTesterMain implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        String apiEndpoint = "https://pry8wl8hj0.execute-api.us-east-1.amazonaws.com/Dev/multipletechnicianfirstrequestdevgamma";

        // Read the input stream to get the request body from the API Gateway
        String requestBody = readInputToString(input);

        // Convert the request body JSON to RequestDTO object
        ObjectMapper objectMapper = new ObjectMapper();
        ZipCodeRequestDTO reqBody = null;
        try {
            reqBody = objectMapper.readValue(requestBody, ZipCodeRequestDTO.class);
            System.out.println("reqBody " + reqBody);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception accordingly
        }

        String payloadJsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqBody);
        System.out.println("payloadJsonString" + payloadJsonString);
        ZipCodeResponseDTO response = ExecutionController.invokeApi(apiEndpoint, payloadJsonString);

        ResponseDTO responseObject = new ResponseDTO();
        if (response != null) {
            boolean passed = ExecutionController.checkResponse(response);
            if (passed) {
                responseObject.setStatusCode(HttpStatus.SC_OK);
                responseObject.setResult(true);
                responseObject.setLocationId(response.getLocationId());
                // Convert the response DTO object to JSON
                String responseJson = objectMapper.writeValueAsString(responseObject);
                output.write(responseJson.getBytes());
                System.out.println("Response passed the test, Response Is " + response);
                // Create the Lambda function and store the response as an environment variable
            } else {
                responseObject.setStatusCode(HttpStatus.SC_BAD_REQUEST);
                responseObject.setResult(false);
                responseObject.setLocationId(response.getLocationId());
                // Convert the response DTO object to JSON
                String responseJson = objectMapper.writeValueAsString(responseObject);
                output.write(responseJson.getBytes());
                System.out.println("Response failed the test, Response Is " + response);
            }
        } else {
            responseObject.setStatusCode(HttpStatus.SC_NO_CONTENT);
            responseObject.setResult(false);
            responseObject.setLocationId("EMPTY RESPONSE");

            // Convert the response DTO object to JSON
            String responseJson = objectMapper.writeValueAsString(responseObject);
            output.write(responseJson.getBytes());
            System.out.println("No Response Returned");
        }
    }


    private String readInputToString(InputStream input) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) > -1) {
            stringBuilder.append(new String(buffer, 0, bytesRead));
        }
        return stringBuilder.toString();
    }
}
