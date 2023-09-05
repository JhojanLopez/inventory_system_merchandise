package com.example.merchandise.services;

import com.example.merchandise.models.MerchandisePageableDto;

import java.util.List;

public interface MerchandiseService {
    List<MerchandisePageableDto> getAll(int page, int size);
}
