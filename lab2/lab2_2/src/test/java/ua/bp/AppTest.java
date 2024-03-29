package ua.bp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppTest {
    @Mock
    public ISimpleHttpClient client;

    @InjectMocks
    public AddressResolverService service;

    @BeforeEach
    public void init() {
        service = new AddressResolverService(client);
    }

    @Test
    public void TestURIPreparation() {
        assertEquals(
                "https://www.mapquestapi.com/geocoding/v1/reverse?key=fjpDDX0NAqWObtm30nW1w2njohV84QWs&location=30.33356,-81.47019&outFormat=json",
                service.prepareUriForRemoteEndpoint(30.33356, -81.47019));
    }

    @Test
    public void TestCorrectResponse() {
        //this is just trying to create a json to compare to, it looks complicated due to all the nesting on the response
        JSONObject info = new JSONObject();
        info.put("statuscode", 0);
        JSONObject locations = new JSONObject();
        locations.put("street", "802 Arkenstone Dr");
        locations.put("adminArea5", "Jacksonville");
        locations.put("postalCode", "32225");

        JSONArray results=new JSONArray();
        results.put(new JSONObject("{\"locations\": ["+locations+"]}"));

        JSONObject expected=new JSONObject();
        expected.put("info",info);
        expected.put("results",results);

        Mockito.when(client.doHttpGet(
                "https://www.mapquestapi.com/geocoding/v1/reverse?key=fjpDDX0NAqWObtm30nW1w2njohV84QWs&location=30.33356,-81.47019&outFormat=json"))
                .thenReturn(expected.toString());
        Optional<Address> response = service.findAddressForLocation(30.33356, -81.47019);

        assertAll(
                () -> assertTrue(response.isPresent()),
                () -> assertEquals("32225", response.get().getZip()),
                () -> assertEquals("Jacksonville", response.get().getCity()),
                () -> assertEquals("802", response.get().getHouseNumber()), // there's not houseNumber field, so I'm
                                                                            // guessing
                () -> assertEquals("802 Arkenstone Dr", response.get().getRoad()));
    }

    @Test
    public void TestWrongCoordinates() {
        JSONObject info = new JSONObject();
        info.put("statuscode", 400);

        JSONObject expected = new JSONObject();
        expected.put("info", info);

        Mockito.when(client.doHttpGet(
                "https://www.mapquestapi.com/geocoding/v1/reverse?key=fjpDDX0NAqWObtm30nW1w2njohV84QWs&location=300.33356,-81.47019&outFormat=json"))
                .thenReturn(expected.toString());
        Optional<Address> response = service.findAddressForLocation(300.33356, -81.47019);

        assertFalse(response.isPresent());
    }
}
