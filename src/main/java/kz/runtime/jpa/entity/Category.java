package kz.runtime.jpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // инкрементирование поля на стороне бд
    // если не писать, будет требовать передать айди
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category") // поле в классе, не с таблицы
    private List<Product> products;

    @OneToMany(mappedBy = "category")
    private List<Characteristic> characteristics;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
