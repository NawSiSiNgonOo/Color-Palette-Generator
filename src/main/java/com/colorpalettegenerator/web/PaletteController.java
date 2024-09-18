package com.colorpalettegenerator.web;

import com.colorpalettegenerator.model.ColorModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class PaletteController {

    @GetMapping("/palette")
    public String showPaletteGenerator(Model model) {
        List<ColorModel> colors = generateRandomColors();
        model.addAttribute("colors", colors);

        return "palette-generator";
    }

    private List<ColorModel> generateRandomColors() {
        List<ColorModel> colors = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 24; i++) {
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);
            colors.add(new ColorModel(red, green, blue));
        }

        return colors;
    }

    @PostMapping(value = "/palette/refresh", produces = "application/json")
    public ResponseEntity<List<ColorModel>> refreshPalette(Model model) {
        List<ColorModel> colors = generateRandomColors();
        return ResponseEntity.ok().body(colors);
    }


}
