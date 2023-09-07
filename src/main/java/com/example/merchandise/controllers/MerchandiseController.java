package com.example.merchandise.controllers;

import com.example.merchandise.database.repositories.MerchandiseRepository;
import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.models.MerchandiseToSaveDto;
import com.example.merchandise.models.MerchandiseToUpdateDto;
import com.example.merchandise.services.MerchandiseService;
import com.example.merchandise.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/merchandise")
@RequiredArgsConstructor
public class MerchandiseController {
    private final MerchandiseService merchandiseService;
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(merchandiseService.getAll(page, size));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getAllByName(@PathVariable String name) {
        return ResponseEntity.ok(merchandiseService.getByNameContainingIgnoreCase(name));
    }

    @GetMapping("/{merchandiseId}")
    public ResponseEntity<?> getById(@PathVariable long merchandiseId) {
        MerchandiseDto byId = merchandiseService.getById(merchandiseId);
        if (byId == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(byId);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MerchandiseToSaveDto merchandiseToSaveDto, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.badRequest().body(ValidationUtils.getErrors(result));
        merchandiseService.save(merchandiseToSaveDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{merchandiseId}")
    public ResponseEntity<?> update(@PathVariable long merchandiseId, @Valid @RequestBody MerchandiseToUpdateDto merchandiseToUpdateDto, BindingResult result) {

        if (merchandiseService.getById(merchandiseId) == null)
            return ResponseEntity.badRequest().body("The merchandise does not exist");

        if (result.hasErrors())
            return ResponseEntity.badRequest().body(ValidationUtils.getErrors(result));

        return ResponseEntity.ok(merchandiseService.update(merchandiseId, merchandiseToUpdateDto));
    }

    @DeleteMapping("/{merchandiseId}")
    public ResponseEntity<?> update(@PathVariable long merchandiseId) {
        if (merchandiseService.getById(merchandiseId) == null)
            return ResponseEntity.badRequest().body("The merchandise does not exist");

        merchandiseService.delete(merchandiseId);
        return ResponseEntity.noContent().build();
    }
}
