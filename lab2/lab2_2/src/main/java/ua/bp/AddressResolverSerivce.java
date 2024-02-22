package ua.bp;

import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddressResolverSerivce {
    private ISimpleHttpClient client;

    public String prepareUriForRemoteEndpoint(double lat, double lng) {

    }

    public Optional<Address> findAddressForLocation(double lat, double lng){

    }
}
