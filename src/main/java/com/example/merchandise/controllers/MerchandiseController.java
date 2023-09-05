package com.example.merchandise.controllers;

import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.services.MerchandiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchandise")
@RequiredArgsConstructor
public class MerchandiseController {
    private final MerchandiseService merchandiseService;
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam int page, @RequestParam int size){
      return ResponseEntity.ok(merchandiseService.getAll(page,size));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getById(@PathVariable long userId){
        MerchandiseDto byId = merchandiseService.getById(userId);
        if(byId == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(byId);
    }
}
