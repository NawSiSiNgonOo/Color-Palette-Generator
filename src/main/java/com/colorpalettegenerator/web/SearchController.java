package com.colorpalettegenerator.web;

import com.colorpalettegenerator.model.Color;
import com.colorpalettegenerator.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private ColorService colorService;

    @GetMapping("/search1")
    public String searchColors(@RequestParam(required = false) String query, Model model) {
        if (query != null) {
            List<Color> colors = colorService.searchColors(query);
            model.addAttribute("colors", colors);
        }
        return "fragments/colors :: colorResults";
    }
}
