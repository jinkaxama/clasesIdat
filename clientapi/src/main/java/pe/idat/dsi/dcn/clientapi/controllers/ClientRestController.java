package pe.idat.dsi.dcn.clientapi.controllers;
 
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.idat.dsi.dcn.clientapi.models.Client;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController  // indica que esta clase es un controlador REST
@RequestMapping("/api/v1/clients") // nos indica que la URL base para este controlador será /api/v1/clients
public class ClientRestController {
    private List<Client> clients;
    private final AtomicLong idGenerator = new AtomicLong(3);

    public ClientRestController(){
        this.clients = populate();
    }

    // Aquí irán los métodos que expondrán los servicios REST para el manejo de los clientes
    @GetMapping // indica que este método responderá a una petición GET
    public ResponseEntity<List<Client>> getAll() {  
        //cuando usar responseentity? cuando necesitamos devolver una respuesta con un código de estado y un cuerpo de respuesta
        //.ok es un método estático de ResponseEntity que nos permite devolver una respuesta con el código de estado 200 (OK) y el objeto que le pasamos como parámetro en el cuerpo de la respuesta 
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}") // indica que este método responderá a una petición GET con un parámetro en la URL llamado id 
    public ResponseEntity<Client> getById(@PathVariable Long id) { //no tienen body => GET, DELETE
        Optional<Client> client = this.clients
            .stream()
            .filter(c -> c.getId() == id)
            .findFirst();

        return client.isPresent() ?
            ResponseEntity.ok(client.get()):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping // indica que este método responderá a una petición POST
    public ResponseEntity<Client> create(@RequestBody Client entity ){    //con body -> POST, PUT
        try {
            Long newId = idGenerator.incrementAndGet();
            entity.setId(newId);
            clients.add(entity);
            return ResponseEntity.created(URI.create("/api/v1/clients/"+newId)).body(entity);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}") // indica que este método responderá a una petición PUT con un parámetro en la URL llamado id
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client entity) {
        Optional<Client> existsClient = clients
            .stream()
            .filter(c -> c.getId().equals(id))
            .findFirst();

        if(existsClient.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        clients = clients
            .stream()
            .map(i -> {
                if(i.getId().equals(id)){
                    i.setName(entity.getName());
                    i.setLastName(entity.getLastName());
                    i.setNid(entity.getNid());
                    i.setEmail(entity.getEmail());
                }
                return i;
            }).collect(Collectors.toList());
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}") // indica que este método responderá a una petición DELETE con un parámetro en la URL llamado id
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean hasBeenRemoved = clients.removeIf(c-> c.getId().equals(id));
        return hasBeenRemoved ?
            ResponseEntity.noContent().build():
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    
    private List<Client> populate(){
        List<Client> bucket = new ArrayList<>();
        
            bucket.add(new Client(1L, "julio", "leonardo", "45345345","leo@gmail.com"));
            bucket.add(new Client(2L, "pepe","leonardo", "w14234234","car@gmail.com"));
            bucket.add(new Client(3L, "pjunacho","salas", "4353455","crrrr@gmail.com"));
            return bucket;
    }
}

 

 
