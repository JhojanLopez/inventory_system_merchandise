package com.example.merchandise.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseToUpdateDto {
    private String name;
    @Min(value = 0, message = "The amount should not be less than 0")
    private Long amount;
    @PastOrPresent(message = "The date of admission must be current or previous")
    private LocalDate dateEntry;
    @NotNull(message = "The id of the user who updated cannot be null")
    private Long updatedById;
}
