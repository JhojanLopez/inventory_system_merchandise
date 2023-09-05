package com.example.merchandise.controllers;

import com.example.merchandise.services.MerchandiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/merchandise")
@RequiredArgsConstructor
public class MerchandiseController {
    private final MerchandiseService merchandiseService;
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam int page, @RequestParam int size){
      return ResponseEntity.ok(merchandiseService.getAll(page,size));
    }
}
