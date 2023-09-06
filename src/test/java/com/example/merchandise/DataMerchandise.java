package com.example.merchandise;

import com.example.merchandise.database.entities.Merchandise;
import com.example.merchandise.database.entities.User;
import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.models.MerchandisePageableDto;
import com.example.merchandise.models.MerchandiseToSaveDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DataMerchandise {
    DataUsers dataUsers;
    private List<Merchandise> merchandises;
    private List<MerchandisePageableDto> merchandisePageableDtos;
    private Merchandise merchandise;
    private MerchandiseDto merchandiseDto;
    private MerchandiseToSaveDto merchandiseToSaveDto;
    private Merchandise merchandiseToSave;
    private MerchandiseDto merchandiseDtoSaved;

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

    public MerchandiseToSaveDto getMerchandiseToSaveDto() {
        return MerchandiseToSaveDto.builder()
                .name("Nuevo Producto")
                .amount(20)
                .dateEntry(LocalDate.now())
                .registeredById(1)
                .build();
    }

    public Merchandise getMerchandiseToSave() {
        return Merchandise.builder()
                .id(6)
                .name(getMerchandiseToSaveDto().getName())
                .amount(getMerchandiseToSaveDto().getAmount())
                .dateEntry(getMerchandiseToSaveDto().getDateEntry())
                .createdAt(LocalDateTime.now())
                .registeredBy(User.builder()
                        .id(getMerchandiseToSaveDto().getRegisteredById())
                        .build())
                .build();
    }

    public MerchandiseDto getMerchandiseDtoSaved() {
        return MerchandiseDto.builder()
                .id(getMerchandiseToSave().getId())
                .name(getMerchandiseToSave().getName())
                .amount(getMerchandiseToSave().getAmount())
                .dateEntry(getMerchandiseToSave().getDateEntry())
                .registeredByName(getMerchandiseToSave().getRegisteredBy().getName())
                .createdAt(getMerchandiseToSave().getCreatedAt())
                .build();
    }
}
