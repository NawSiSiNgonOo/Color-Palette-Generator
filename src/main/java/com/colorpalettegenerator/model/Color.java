package com.colorpalettegenerator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "color", uniqueConstraints = @UniqueConstraint(columnNames = "hex-code"))
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "hex-code",unique = true)
    private String hexCode;

    public Color(String name, String hexCode) {
        this.name = name;
        this.hexCode = hexCode;
    }

    public Color() {

    }

    public String getName() {
        return name;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }
}
