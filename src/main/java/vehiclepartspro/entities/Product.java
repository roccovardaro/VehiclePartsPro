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
@Table(name = "product", schema = "PezziDiRicambio")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "name", nullable = true, length = 50,unique = true)
    private String name;

    @Basic
    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Basic
    @Column(name = "price", nullable = true)
    private float price;

    @Basic
    @Column(name = "quantity", nullable = true)
    private int quantity;

    @ManyToOne()
    @JoinColumn(name="car_id")
    private Car car;

    @ManyToOne()
    @JoinColumn(name = "manufacturer_id", nullable = true)
    private Manufacturer manufacturer;

    @OneToMany(targetEntity = ProductInPurchase.class, mappedBy = "product", cascade = CascadeType.MERGE)
    @JsonIgnore
    @ToString.Exclude
    private List<ProductInPurchase> productsInPurchase;


}
