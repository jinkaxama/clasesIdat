package pe.idat.dsi.dcn.clientapi.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pe.idat.dsi.dcn.clientapi.models.Client;
import pe.idat.dsi.dcn.clientapi.repositories.ClientRepository;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clienteRepository){
        this.clientRepository = clienteRepository;
    }

    public Client create(Client client){
        var result = clientRepository.saveAndFlush(client);

        return result;
    }

    public Client update(Long id, Client entity){
        var response = clientRepository.findById(id)
            .orElse(null);

        if(response == null) return null;
        
        response.setName(entity.getName());
        response.setLastName(entity.getLastName());
        response.setNid(entity.getNid());
        response.setEmail(entity.getEmail());

        clientRepository.saveAndFlush(response);
        return response;
        
    }

    public boolean delete(Long id){
        var response = clientRepository.findById(id)
        .orElse(null);
    
        if(response == null) return false;
        clientRepository.deleteById(id);

        return true;
    }

    public List<Client> getAll(){
        return clientRepository.findAll();
    }

    public Page<Client> getAllPageable(String name, String nid, int pageNumber, int pageSize){

        Sort sorting = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sorting);

        return clientRepository.findAll(pageable);
    }

    public Client getById(Long id){
        return clientRepository
        .findById(id)
        .orElse(null);
    }
}
