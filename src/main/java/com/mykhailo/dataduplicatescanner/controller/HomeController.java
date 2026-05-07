package com.mykhailo.dataduplicatescanner.controller;

import com.mykhailo.dataduplicatescanner.model.DuplicateRecord;
import com.mykhailo.dataduplicatescanner.service.DatabaseService;
import com.mykhailo.dataduplicatescanner.service.DuplicateScanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final DatabaseService databaseService;
    private final DuplicateScanService duplicateScanService;

    public HomeController(DatabaseService databaseService, DuplicateScanService duplicateScanService) {
        this.databaseService = databaseService;
        this.duplicateScanService = duplicateScanService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("databases", databaseService.findDatabases());

        return "index";
    }

    @GetMapping("/scan")
    public String scan(@RequestParam String database, Model model) {
        List<DuplicateRecord> duplicateRecords = duplicateScanService.scanDatabase(database);

        model.addAttribute("database", database);
        model.addAttribute("duplicateRecords", duplicateRecords);

        return "scan";
    }
}