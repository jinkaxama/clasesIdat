package pe.idat.dsi.dcn.clientapi.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*; // importamos la anotación @Entity de JPA para que la clase sea una entidad
import lombok.AllArgsConstructor; // importamos la anotación @AllArgsConstructor de Lombok para que genere un constructor con todos los atributos de la clase
import lombok.Data; // importamos la anotación @Data de Lombok para que genere los métodos getter, setter, equals, hashcode y toString
import lombok.NoArgsConstructor; // importamos la anotación @NoArgsConstructor de Lombok para que genere un constructor vacío

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")


public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @Column(name = "lastname", length = 100, nullable = false)
    private String lastName;
    @Column(name = "nid", length = 8, unique = true, nullable = false)
    private String nid;
    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Address> addresses;
}