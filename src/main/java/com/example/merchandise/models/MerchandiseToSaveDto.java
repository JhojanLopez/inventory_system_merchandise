package com.example.merchandise.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseToSaveDto {
    @NotNull(message = "The name cannot be null")
    private String name;//unique
    @Min(value = 0, message = "The amount should not be less than 0")
    private long amount;
    private long registeredById;
    @PastOrPresent(message = "The date of admission must be current or previous")
    private LocalDate dateEntry;
}
