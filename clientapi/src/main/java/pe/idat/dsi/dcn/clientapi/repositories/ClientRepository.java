package pe.idat.dsi.dcn.clientapi.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.idat.dsi.dcn.clientapi.models.Client;
import org.springframework.data.repository.query.Param;




@Repository 
public interface ClientRepository extends JpaRepository<Client, Long>{
    @Query("SELECT c FROM Client c where c.name LIKE %:name% AND c.nid LIKE %:nid%")
    Page<Client> findAllWithPagingAndCustomFilter(@Param("name")String name, @Param("nid") String nid, Pageable pageable);
   
}
