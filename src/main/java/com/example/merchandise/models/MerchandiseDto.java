package com.example.merchandise.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseDto {
    private long id;
    private String name;
    private long amount;
    private String registeredByName;
    private String updatedByName;
    private LocalDate dateEntry;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
