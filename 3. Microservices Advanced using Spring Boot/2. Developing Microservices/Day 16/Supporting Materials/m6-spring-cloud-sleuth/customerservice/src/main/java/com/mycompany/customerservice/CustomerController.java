package com.mycompany.customerservice;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class CustomerController {

    @RequestMapping(value = "/customer/{cid}", method = RequestMethod.GET)
    public CustomerDetails getCustomer(@PathVariable String cid) {

        CustomerDetails customer = new CustomerDetails();
        WebClient client = WebClient.create();

        ContactDetails svc1 = client.get()
                .uri("http://localhost:8081/customer/" + cid + "/contactdetails")
                .retrieve()
                .bodyToMono(ContactDetails.class)
                .block();

        VehicleDetails svc2 = client.get()
                .uri("http://localhost:8082/customer/" + cid + "/vehicledetails")
                .retrieve()
                .bodyToMono(VehicleDetails.class)
                .block();

        customer.setContactId(cid);
        customer.setContactName(svc1.getContactName());
        customer.setPostalCode(svc1.getPostalCode());
        customer.setLicensePlate(svc2.getLicensePlate());
        customer.setCarType(svc2.getCarType());

        return customer;

    }

}
