package com.example.merchandise.services;

import com.example.merchandise.database.repositories.MerchandiseRepository;
import com.example.merchandise.models.MerchandisePageableDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchandiseServiceImpl implements MerchandiseService {

    private final MerchandiseRepository merchandiseRepository;
    private final ModelMapper mapper;

    @Override
    public List<MerchandisePageableDto> getAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return merchandiseRepository.findAll(pageable).stream()
                .map(e -> mapper.map(e, MerchandisePageableDto.class))
                .toList();
    }
}
