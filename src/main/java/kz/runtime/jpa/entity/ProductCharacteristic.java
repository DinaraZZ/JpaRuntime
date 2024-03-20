package kz.runtime.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_characteristics")
public class ProductCharacteristic {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "characteristics_id")
    private Characteristic characteristic;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
