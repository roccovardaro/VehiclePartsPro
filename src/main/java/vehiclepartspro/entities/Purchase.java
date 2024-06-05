package vehiclepartspro.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "purchase", schema = "PezziDiRicambio")
public class Purchase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "purchase_time")
    private Date purchaseTime;

    @Basic
    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "buyer")
    //viene utilizzata per specificare il mapping tra un campo di un'entità e una colonna di una tabella che rappresenta
    //un'altra entità in una relazione di join.
    private Customer buyer;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private List<ProductInPurchase> productsInPurchase;
}
