package pe.idat.dsi.dcn.clientapi.controllers;
 
import java.net.URI;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.idat.dsi.dcn.clientapi.dtos.UpdateNidClientRequest;
import pe.idat.dsi.dcn.clientapi.models.Client;
import pe.idat.dsi.dcn.clientapi.services.ClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController  // indica que esta clase es un controlador REST
@RequestMapping("/api/v1/clients") // nos indica que la URL base para este controlador será /api/v1/clients
public class ClientRestController {

    private ClientService clientService;

    public ClientRestController(ClientService service){
        this.clientService = service;
    }

    // Aquí irán los métodos que expondrán los servicios REST para el manejo de los clientes
    @GetMapping // indica que este método responderá a una petición GET
    public ResponseEntity<List<Client>> getAll() {  
        //cuando usar responseentity? cuando necesitamos devolver una respuesta con un código de estado y un cuerpo de respuesta
        //.ok es un método estático de ResponseEntity que nos permite devolver una respuesta con el código de estado 200 (OK) y el objeto que le pasamos como parámetro en el cuerpo de la respuesta 
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping("/{id}") // indica que este método responderá a una petición GET con un parámetro en la URL llamado id 
    public ResponseEntity<Client> getById(@PathVariable Long id) { //no tienen body => GET, DELETE
        var response = clientService.getById(id);

            return response != null ?
                ResponseEntity.ok(response):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<Client>> getAllPageable(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "5") int pageSize,
        @RequestParam(defaultValue = "id") String sortColumn,
        @RequestParam(defaultValue = "asc") String sortOrder,
        @RequestParam(defaultValue = "") String name, 
        @RequestParam(defaultValue = "") String nid){
        
        return ResponseEntity.ok(clientService.getAllPageable(pageNumber, pageSize, sortColumn, sortOrder , name, nid));
    }
      
    @PostMapping // indica que este método responderá a una petición POST
    public ResponseEntity<Client> create(@RequestBody Client entity ){    //con body -> POST, PUT
        try {
            var response = clientService.create(entity);
            return ResponseEntity.created(URI.create("/api/v1/clients/"+response.getId())).body(entity);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}") // indica que este método responderá a una petición PUT con un parámetro en la URL llamado id
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client entity) {
        
        var response = clientService.update(id, entity);
            
        if(response == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}") // indica que este método responderá a una petición DELETE con un parámetro en la URL llamado id
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean hasRemoved = clientService.delete(id);
        return hasRemoved ?
            ResponseEntity.noContent().build():
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @PatchMapping()
    public ResponseEntity<Client> delete(@RequestBody UpdateNidClientRequest filter) {
        try {
            var client = clientService.updateNid(filter.getId(), filter.getNid());
            return client == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(client);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        
    }
}

 

 
