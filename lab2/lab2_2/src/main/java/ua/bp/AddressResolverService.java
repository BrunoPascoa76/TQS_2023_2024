package ua.bp;

import java.util.Arrays;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;

public class AddressResolverService {
    private ISimpleHttpClient client;
    private String api_key = "fjpDDX0NAqWObtm30nW1w2njohV84QWs";

    public AddressResolverService(ISimpleHttpClient client) {
        this.client = client;
    }

    public String prepareUriForRemoteEndpoint(double lat, double lng) {
        return "https://www.mapquestapi.com/geocoding/v1/reverse?key=" + api_key + "&location=" + lat + "," + lng
                + "&outFormat=json";
    }

    public Optional<Address> findAddressForLocation(double lat, double lng) {
        String uri = prepareUriForRemoteEndpoint(lat, lng);

        String response = client.doHttpGet(uri);

        if (response.length() < 4) { // because I am forced to return a string according to the figure, I have to
                                     // improvise responses
            return Optional.empty();
        }
        try {
            JSONObject json = new JSONObject(response);
            String result = json.toString();
            if (json.getJSONObject("info").getInt("statuscode") != 0) {
                return Optional.empty();
            } else {
                JSONObject locations = json.getJSONArray("results").getJSONObject(0).getJSONArray("locations").getJSONObject(0);
                String houseNumber = locations.getString("street").split(" ")[0];
                Address address = new Address(locations.getString("adminArea5"), locations.getString("postalCode"),
                        locations.getString("street"), houseNumber);
                return Optional.of(address);
            }
        } catch (Exception e) {
            String msg = e.getMessage();
            return Optional.empty();
        }
    }
}
