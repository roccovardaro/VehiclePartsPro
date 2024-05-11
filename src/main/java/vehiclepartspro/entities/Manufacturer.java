package vehiclepartspro.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "manufacturer", schema = "PezziDiRicambio")

public class Manufacturer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name="id", nullable = false)
    private int id;
    @Basic
    @Column(name = "code", nullable = false, length = 50,unique = true)
    private String code;

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Basic
    @Column(name= "telephone_number", length = 100)
    private String telephoneNumber;

    @Basic
    @Column(name= "address", length = 100)
    private String address;

    @OneToMany(mappedBy = "manufacturer",cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Product> products;


}
