package com.colorpalettegenerator.service;

import com.colorpalettegenerator.model.Color;
import com.colorpalettegenerator.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    public List<Color> searchColors(String query) {
        return colorRepository.findByNameContainingIgnoreCase(query);
    }

    public void saveColor(Color color) {
        colorRepository.save(color);
    }

    public boolean isColorDuplicate(String name, String hexCode) {
        List<Color> existingColors = colorRepository.findByNameIgnoreCaseOrHexCodeIgnoreCase(name, hexCode);
        return !existingColors.isEmpty();
    }

}
