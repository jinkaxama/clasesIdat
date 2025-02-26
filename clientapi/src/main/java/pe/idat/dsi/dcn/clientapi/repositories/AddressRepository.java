package pe.idat.dsi.dcn.clientapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.idat.dsi.dcn.clientapi.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{

    
}
