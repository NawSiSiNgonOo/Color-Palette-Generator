package com.colorpalettegenerator.repository;

import com.colorpalettegenerator.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color,Long> {
    List<Color> findByNameContainingIgnoreCase(String name);

    List<Color> findByNameIgnoreCaseOrHexCodeIgnoreCase(String name, String hexCode);
}
