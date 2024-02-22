package ua.bp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    public void init() {
        service = new AddressResolverService(client);
    }

    @Test
    public void TestCorrectResponse() {
        Mockito.when(client.doHttpGet("https://www.mapquestapi.com/geocoding/v1/reverse?key=fjpDDX0NAqWObtm30nW1w2njohV84QWs&location=30.333472,-81.470448&outFormat=json")).thenReturn("""
                
                """;)

        Optional<Address> response = service.findAddressForLocation(399.755695, -104.995986);
        assertAll(
                () -> assertTrue(response.isPresent()),
                () -> assertEquals("32225", response.get().getZip()),
                () -> assertEquals("Jacksonville", response.get().getCity()),
                () -> assertEquals("802", response.get().getHouseNumber()),
                () -> assertEquals("Arkenstone Dr", response.get().getRoad()));
    }
}
