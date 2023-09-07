package com.example.merchandise.services;

import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.models.MerchandisePageableDto;
import com.example.merchandise.models.MerchandiseToSaveDto;
import com.example.merchandise.models.MerchandiseToUpdateDto;

import java.util.List;
import java.util.Map;

public interface MerchandiseService {
    Map<String, Object> getAll(int page, int size);
    MerchandiseDto getById(long id);
    List<MerchandisePageableDto> getByNameContainingIgnoreCase(String name);
    MerchandiseDto save(MerchandiseToSaveDto merchandiseToSaveDto);
    MerchandiseDto update(long merchandiseId, MerchandiseToUpdateDto merchandiseToSaveDto);
    void delete(long merchandiseId);
}
