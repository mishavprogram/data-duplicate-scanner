package com.mykhailo.dataduplicatescanner.controller;

import com.mykhailo.dataduplicatescanner.service.DatabaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final DatabaseService databaseService;

    public HomeController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("databases", databaseService.findDatabases());

        return "index";
    }
}