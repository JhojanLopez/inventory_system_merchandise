package com.example.merchandise;

import com.example.merchandise.database.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DataUsers {
    private List<User> users;

    public List<User> getUsers() {
        return List.of(
                User.builder()
                        .id(1)
                        .name("Juan Garcia")
                        .age(30)
                        .dateEntry(LocalDate.now().minusDays(2))
                        .createdAt(LocalDateTime.now())
                        .build(),
                User.builder()
                        .id(2)
                        .name("Jhohan Lopez")
                        .age(23)
                        .dateEntry(LocalDate.now().minusDays(20))
                        .createdAt(LocalDateTime.now())
                        .build(),
                User.builder()
                        .id(3)
                        .name("Kevin Casierra")
                        .age(22)
                        .dateEntry(LocalDate.now().minusDays(10))
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }
}
