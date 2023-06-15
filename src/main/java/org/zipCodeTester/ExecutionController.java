package org.zipCodeTester;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class ExecutionController {

    public static ZipCodeResponseDTO invokeApi(String apiEndpoint, String payload) {
        System.out.println("Inside invokeApi");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String responseString = "";
        ZipCodeResponseDTO zipCodeResponseDTO = null;

        try {
            // Create a POST request
            HttpPost httpPost = new HttpPost(apiEndpoint);

            // Set the payload as the request body
            StringEntity requestEntity = new StringEntity(payload);
            httpPost.setEntity(requestEntity);

            // Execute the request and get the response
            CloseableHttpResponse response = httpClient.execute(httpPost);

            try {
                // Read the response body as a string
                HttpEntity responseEntity = response.getEntity();
                responseString = EntityUtils.toString(responseEntity);
                System.out.println("Response String is "+responseString);
                if(!responseString.contains("Internal server error")) {
                    // Create an instance of ObjectMapper
                    ObjectMapper mapper = new ObjectMapper();

                    // Deserialize the response body into a ResponseDTO object
                    zipCodeResponseDTO = mapper.readValue(responseString, ZipCodeResponseDTO.class);
                }
                // Print the response
            } finally {
                // Close the response
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zipCodeResponseDTO;
    }

    public static boolean checkResponse(ZipCodeResponseDTO response) {
        System.out.println("inside checkResponse");
        boolean result = false;
        System.out.println("response.getInServiceArea() = "+response.getInServiceArea());
           if(response.getInServiceArea().equals("true")){
               System.out.println(true);
               result = true;
           }
           return result;
    }
}
