package entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "car", schema = "PezziDiRicambio")

public class Car
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Basic
    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Basic
    @Column(name = "year", nullable = false)
    private int year;

    @Basic
    @Column(name = "description", nullable = true, length = 500)
    private String description;






}
