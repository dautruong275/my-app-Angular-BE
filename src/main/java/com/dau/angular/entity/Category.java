package com.dau.angular.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "created_by",nullable = false)
    private String createdBy;

    @Column(name = "created_at")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private String description;

    @PrePersist
    protected void onCreate() {
        createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

}
