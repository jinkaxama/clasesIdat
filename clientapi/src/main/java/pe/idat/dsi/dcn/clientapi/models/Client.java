package pe.idat.dsi.dcn.clientapi.models;

import lombok.AllArgsConstructor; // importamos la anotación @AllArgsConstructor de Lombok para que genere un constructor con todos los atributos de la clase
import lombok.Data; // importamos la anotación @Data de Lombok para que genere los métodos getter, setter, equals, hashcode y toString
import lombok.NoArgsConstructor; // importamos la anotación @NoArgsConstructor de Lombok para que genere un constructor vacío

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Client {
    private Long id;
    private String name;
    private String lastName;
    private String nid;
    private String email;

}