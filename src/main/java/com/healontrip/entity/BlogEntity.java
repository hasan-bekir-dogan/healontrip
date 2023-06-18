package com.healontrip.entity;

import com.healontrip.dto.BlogStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@Log4j2

@Entity
@Table(name = "blogs")
public class BlogEntity extends BaseEntity implements Serializable {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    @Column(name = "img_id")
    private Long imgId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private BlogStatus status;

    public BlogEntity(String title, Long categoryId, String description, Long imgId, Long userId, BlogStatus status) {
        this.title = title;
        this.categoryId = categoryId;
        this.description = description;
        this.imgId = imgId;
        this.userId = userId;
        this.status = status;
    }
}
