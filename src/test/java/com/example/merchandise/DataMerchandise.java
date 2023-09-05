package com.example.merchandise;

import com.example.merchandise.database.entities.Merchandise;
import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.models.MerchandisePageableDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DataMerchandise {
    DataUsers dataUsers;
    private List<Merchandise> merchandises;
    private List<MerchandisePageableDto> merchandisePageableDtos;
    private Merchandise merchandise;
    private MerchandiseDto merchandiseDto;

    public DataMerchandise() {
        dataUsers = new DataUsers();
    }

    public List<Merchandise> getMerchandises() {
        return List.of(
                Merchandise.builder()
                        .id(1)
                        .name("Iphone X")
                        .amount(100)
                        .dateEntry(LocalDate.now())
                        .createdAt(LocalDateTime.now())
                        .registeredBy(dataUsers.getUsers().get(0))
                        .build(),
                Merchandise.builder()
                        .id(2)
                        .name("Iphone 11")
                        .amount(100)
                        .dateEntry(LocalDate.now())
                        .createdAt(LocalDateTime.now())
                        .registeredBy(dataUsers.getUsers().get(1))
                        .build(),
                Merchandise.builder()
                        .id(3)
                        .name("Iphone 12")
                        .amount(100)
                        .dateEntry(LocalDate.now())
                        .createdAt(LocalDateTime.now())
                        .registeredBy(dataUsers.getUsers().get(2))
                        .build(),
                Merchandise.builder()
                        .id(4)
                        .name("Iphone 13")
                        .amount(100)
                        .dateEntry(LocalDate.now())
                        .createdAt(LocalDateTime.now())
                        .registeredBy(dataUsers.getUsers().get(0))
                        .build(),
                Merchandise.builder()
                        .id(5)
                        .name("Iphone 13 pro max")
                        .amount(100)
                        .dateEntry(LocalDate.now())
                        .createdAt(LocalDateTime.now())
                        .registeredBy(dataUsers.getUsers().get(1))
                        .build()
        );
    }

    public List<MerchandisePageableDto> getMerchandisePageableDtos() {
        return List.of(
                MerchandisePageableDto.builder()
                        .id(getMerchandises().get(0).getId())
                        .name(getMerchandises().get(0).getName())
                        .amount(getMerchandises().get(0).getAmount())
                        .dateEntry(getMerchandises().get(0).getDateEntry())
                        .build(),
                MerchandisePageableDto.builder()
                        .id(getMerchandises().get(1).getId())
                        .name(getMerchandises().get(1).getName())
                        .amount(getMerchandises().get(1).getAmount())
                        .dateEntry(getMerchandises().get(1).getDateEntry())
                        .build(),
                MerchandisePageableDto.builder()
                        .id(getMerchandises().get(2).getId())
                        .name(getMerchandises().get(2).getName())
                        .amount(getMerchandises().get(2).getAmount())
                        .dateEntry(getMerchandises().get(2).getDateEntry())
                        .build(),
                MerchandisePageableDto.builder()
                        .id(getMerchandises().get(3).getId())
                        .name(getMerchandises().get(3).getName())
                        .amount(getMerchandises().get(3).getAmount())
                        .dateEntry(getMerchandises().get(3).getDateEntry())
                        .build(),
                MerchandisePageableDto.builder()
                        .id(getMerchandises().get(4).getId())
                        .name(getMerchandises().get(4).getName())
                        .amount(getMerchandises().get(4).getAmount())
                        .dateEntry(getMerchandises().get(4).getDateEntry())
                        .build()
        );
    }

    public Merchandise getMerchandise() {
        return getMerchandises().get(0);
    }

    public MerchandiseDto getMerchandiseDto() {
        return MerchandiseDto.builder()
                .id(getMerchandise().getId())
                .name(getMerchandise().getName())
                .amount(getMerchandise().getAmount())
                .dateEntry(getMerchandise().getDateEntry())
                .registeredByName(getMerchandise().getRegisteredBy().getName())
                .updatedByName(null)
                .createdAt(getMerchandise().getCreatedAt())
                .updatedAt(null)
                .build();
    }
}
