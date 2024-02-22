package ua.bp;

import java.util.Optional;

import lombok.AllArgsConstructor;

public class AddressResolverService {
    private ISimpleHttpClient client;
    private String api_key = "fjpDDX0NAqWObrm30nW1w2njohV84QWs";

    public AddressResolverService(ISimpleHttpClient client){
        this.client=client;
    }

    public String prepareUriForRemoteEndpoint(double lat, double lng) {
        
    }

    public Optional<Address> findAddressForLocation(double lat, double lng) {

    }
}
