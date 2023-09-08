package com.example.merchandise.services;

import com.example.merchandise.clients.UserClient;
import com.example.merchandise.database.entities.Merchandise;
import com.example.merchandise.database.entities.User;
import com.example.merchandise.database.repositories.MerchandiseRepository;
import com.example.merchandise.models.MerchandiseDto;
import com.example.merchandise.models.MerchandisePageableDto;
import com.example.merchandise.models.MerchandiseToSaveDto;
import com.example.merchandise.models.MerchandiseToUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchandiseServiceImpl implements MerchandiseService {

    private final MerchandiseRepository merchandiseRepository;
    private final ModelMapper mapper;
    private final UserClient userClient;

    @Override
    public Map<String, Object> getAll(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Merchandise> merchandises = merchandiseRepository.findAll(pageable);

        List<MerchandisePageableDto> content = merchandises.stream().map(e -> mapper.map(e, MerchandisePageableDto.class)).toList();
        Map<String, Object> response = new HashMap<>();
        response.put("content", content);
        response.put("totalElements", merchandises.getTotalElements());

        return response;
    }

    @Override
    public MerchandiseDto getById(long id) {
        return merchandiseRepository.findById(id).map(e -> mapper.map(e, MerchandiseDto.class)).orElse(null);
    }

    @Override
    public List<MerchandisePageableDto> getByNameContainingIgnoreCase(String name) {
        return merchandiseRepository.findByNameContainingIgnoreCase(name).stream()
                .map(e-> mapper.map(e, MerchandisePageableDto.class))
                .toList();
    }

    @Override
    public MerchandiseDto save(MerchandiseToSaveDto merchandiseToSaveDto) {
        if (merchandiseRepository.existsByName(merchandiseToSaveDto.getName()))
            throw new DataIntegrityViolationException("The name must be unique");

        if (!userClient.existUserById(merchandiseToSaveDto.getRegisteredById()))
            throw new DataIntegrityViolationException("The user with id " + merchandiseToSaveDto.getRegisteredById() + " does not exist");

        Merchandise toSave = Merchandise.builder()
                .name(merchandiseToSaveDto.getName())
                .amount(merchandiseToSaveDto.getAmount())
                .dateEntry(merchandiseToSaveDto.getDateEntry())
                .registeredBy(User.builder().id(merchandiseToSaveDto.getRegisteredById()).build())
                .build();
        log.info(merchandiseToSaveDto.toString());

        Merchandise saved = merchandiseRepository.save(toSave);
        return mapper.map(saved, MerchandiseDto.class);
    }

    @Override
    public MerchandiseDto update(long merchandiseId, MerchandiseToUpdateDto merchandiseToUpdateDto) {
        if (!userClient.existUserById(merchandiseToUpdateDto.getUpdatedById()))
            throw new DataIntegrityViolationException("The user with id " + merchandiseToUpdateDto.getUpdatedById() + " does not exist");

        Merchandise entityToUpdate = merchandiseRepository.findById(merchandiseId).get();

        if (merchandiseToUpdateDto.getName() != null && (!Objects.equals(entityToUpdate.getName(), merchandiseToUpdateDto.getName())
                && merchandiseRepository.existsByName(merchandiseToUpdateDto.getName()))) {
            throw new DataIntegrityViolationException("The name must be unique");
        }

        entityToUpdate.setUpdatedBy(User.builder().id(merchandiseToUpdateDto.getUpdatedById()).build());

        if (merchandiseToUpdateDto.getName() != null)
            entityToUpdate.setName(merchandiseToUpdateDto.getName());

        if (merchandiseToUpdateDto.getAmount() != null)
            entityToUpdate.setAmount(merchandiseToUpdateDto.getAmount());

        if (merchandiseToUpdateDto.getDateEntry() != null)
            entityToUpdate.setDateEntry(merchandiseToUpdateDto.getDateEntry());

        Merchandise updated = merchandiseRepository.save(entityToUpdate);
        return mapper.map(updated, MerchandiseDto.class);
    }

    @Override
    public void delete(long merchandiseId) {
        Merchandise merchandise = merchandiseRepository.findById(merchandiseId).get();
        merchandiseRepository.delete(merchandise);
    }
}
