package pe.idat.dsi.dcn.clientapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "avenue", length = 200)
    private String avenue;
    @Column(name = "zipCode", length = 10)
    private String zipCode;
    @Column(name = "number")
    private int number;

    @ManyToOne
    @JoinColumn( name = "client_id", nullable = false)
    private Client client;
}
