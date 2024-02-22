package ua.bp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class TQSBasicHttpClient implements ISimpleHttpClient {

    @Override
    public String doHttpGet(String uri) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
        try{
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            if(response.statusCode()!=200){
                return "401";
            }else{
                return response.body();
            }

        }catch(Exception e){
            return "401";
        }
    }
    
}
