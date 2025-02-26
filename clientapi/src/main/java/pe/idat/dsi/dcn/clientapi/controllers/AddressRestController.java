package pe.idat.dsi.dcn.clientapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.idat.dsi.dcn.clientapi.models.Address;
import pe.idat.dsi.dcn.clientapi.services.AddressService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/addresses")
public class AddressRestController {
    
    private AddressService addressService;

    public AddressRestController(AddressService service){
        this.addressService = service;
    }
    @PostMapping()
    public ResponseEntity<Address> insert(@RequestBody Address entity) {
        try{
            var result = addressService.insert(entity);
            return ResponseEntity.ok(result);

        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        
    }
    
}
