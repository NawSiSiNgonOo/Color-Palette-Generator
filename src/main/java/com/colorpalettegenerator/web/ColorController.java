package com.colorpalettegenerator.web;

import com.colorpalettegenerator.model.Color;
import com.colorpalettegenerator.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping("/search")
    public String searchColors(@RequestParam(required = false) String query, Model model) {
        if (query != null) {
            List<Color> colors = colorService.searchColors(query);
            model.addAttribute("colors", colors);
        }
        return "color-search";
    }

    @GetMapping("/addColor")
    public String showColorForm() {
        return "add-color";
    }

    @PostMapping("/addColor")
    public String addColor(@RequestParam String name, @RequestParam String hexCode, Model model) {
        if (colorService.isColorDuplicate(name, hexCode)) {
            model.addAttribute("duplicateColorMessage", "Color already exists in the database!");
            return "add-color";
        }

        Color color = new Color();
        color.setName(name);
        color.setHexCode(hexCode);
        colorService.saveColor(color);
        return "redirect:/addColor";
    }




}
