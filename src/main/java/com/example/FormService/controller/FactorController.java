package com.example.FormService.controller;

import com.example.quality_management_service.form.model.Factor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factors")
public class FactorController {

    @PostMapping
    public Factor createFactor(@RequestBody Factor factor) {
        return null;
    }

    // READ ALL
    @GetMapping
    public List<Factor> getAllFactors() {
        return null;
    }

    @GetMapping("/{id}")
    public Factor getFactorById(@PathVariable Long id) {
        return null;
    }

    // UPDATE
    @PutMapping("/{id}")
    public Factor updateFactor(@PathVariable Long id, @RequestBody Factor factor) {
        return null;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteFactor(@PathVariable Long id) {

    }
}