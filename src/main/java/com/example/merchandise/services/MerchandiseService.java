package com.example.merchandise.services;

import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.models.MerchandisePageableDto;
import com.example.merchandise.models.MerchandiseToSaveDto;
import com.example.merchandise.models.MerchandiseToUpdateDto;

import java.util.List;

public interface MerchandiseService {
    List<MerchandisePageableDto> getAll(int page, int size);
    MerchandiseDto getById(long id);
    MerchandiseDto save(MerchandiseToSaveDto merchandiseToSaveDto);
    MerchandiseDto update(long merchandiseId, MerchandiseToUpdateDto merchandiseToSaveDto);
}
