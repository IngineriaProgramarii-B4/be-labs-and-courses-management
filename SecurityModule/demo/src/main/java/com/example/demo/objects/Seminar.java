package com.example.demo.objects;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Seminars")
@Component
public class Seminar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Date createAt;
    @Column(nullable = false)
    private Date updatedAt;
    @Column(nullable = false)
    private boolean isDeleted=false;

    public Seminar( String name, Date createAt, Date updatedAt) {
        this.name = name;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public Seminar() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getisDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}