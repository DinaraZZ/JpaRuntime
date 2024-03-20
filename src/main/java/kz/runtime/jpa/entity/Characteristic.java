package kz.runtime.jpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "characteristics")
public class Characteristic {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "characteristic")
    private List<ProductCharacteristic> characteristicDescription;

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public List<ProductCharacteristic> getCharacteristicDescription() {
        return characteristicDescription;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCharacteristicDescription(List<ProductCharacteristic> characteristicDescription) {
        this.characteristicDescription = characteristicDescription;
    }
}
