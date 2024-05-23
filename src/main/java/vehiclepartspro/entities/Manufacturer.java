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
    @Column(name = "id")
    private Long Id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "manufacturer",cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Product> products;

    // Getter and setter methods
}