package com.healontrip.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@Log4j2

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity implements Serializable {
    @Column(name = "name", nullable = false)
    private String name;

    public CategoryEntity(String name) {
        this.name = name;
    }
}
