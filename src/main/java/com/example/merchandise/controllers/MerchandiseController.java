package com.example.merchandise.controllers;

import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.models.MerchandiseToSaveDto;
import com.example.merchandise.services.MerchandiseService;
import com.example.merchandise.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
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

    @GetMapping("/{userId}")
    public ResponseEntity<?> getById(@PathVariable long userId) {
        MerchandiseDto byId = merchandiseService.getById(userId);
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
}
