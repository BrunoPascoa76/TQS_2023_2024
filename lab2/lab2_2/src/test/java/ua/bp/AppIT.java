package ua.bp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class AppIT {
    public ISimpleHttpClient client;

    public AddressResolverService service;

    @BeforeEach
    public void init() {
        client=new TQSBasicHttpClient();
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
        Optional<Address> response = service.findAddressForLocation(300.33356, -81.47019);

        assertFalse(response.isPresent());
    }
}
