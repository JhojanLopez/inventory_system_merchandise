package com.example.merchandise.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchandisePageableDto {
    private long id;
    private String name;
    private long amount;
    private LocalDate dateEntry;
}
