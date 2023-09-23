package com.healontrip.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@Log4j2

@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "detail", nullable = false, length = 4000)
    private String detail;

    public ReviewEntity(Long userId, String title, String detail) {
        this.userId = userId;
        this.title = title;
        this.detail = detail;
    }
}
