package com.example.model;

import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(ItemListener.class)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "SEQ_ITEM", allocationSize = 10)
    @Column(name = "items_id", nullable = false)
    private Long id; // Идентификатор

    @Column(name = "items_name", nullable = false)
    private String name; // Наименование

    @Column(name = "items_description", nullable = false)
    private String description; // Полное описание блюда

    @Column(name = "items_price", nullable = false)
    private Integer price; // Цена блюда

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) && Objects.equals(description, item.description) &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price);
    }

}