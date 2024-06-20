package com.pst.exam.service;

import com.pst.exam.dto.CarDTO;
import com.pst.exam.dto.SearchDTO;
import com.pst.exam.exception.RestResourceNotFoundException;
import com.pst.exam.mapper.CarMapper;
import com.pst.exam.model.entity.Car;
import com.pst.exam.model.repo.CarRepository;
import com.pst.exam.model.repo.SearchCarDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class, Error.class})
public class CarService {
    private final CarRepository repository;
    private final SearchCarDao searchCarDao;
    private final CarMapper mapper;

    public CarService(CarRepository repository, SearchCarDao searchCarDao, CarMapper mapper) {
        this.repository = repository;
        this.searchCarDao = searchCarDao;
        this.mapper = mapper;
    }

    public CarDTO save(CarDTO dto) {
        Car car = mapper.toEntity(dto);
        car.setId(UUID.randomUUID());
        Car result = repository.save(car);
        return mapper.toDTO(result);
    }

    public CarDTO update(UUID id, CarDTO dto) throws RestResourceNotFoundException {
        Car car = findById(id);

        if (Objects.isNull(car)) {
            throw createResourceNotFoundException(id);
        } else {
            dto.setId(car.getId());
            Car result = repository.save(mapper.toEntity(dto));
            return mapper.toDTO(result);
        }
    }

    public List<CarDTO> findAllCars() {
        return mapper.toDTOList(repository.findAll());
    }

    public CarDTO findCarById(UUID id){
        Car car = findById(id);
        return mapper.toDTO(car);
    }

    public void deleteCar(UUID id) {
        repository.deleteById(id);
    }

    public List<CarDTO> searchCars(SearchDTO searchDTO) {
        List<Car> carList = searchCarDao.processSearch(searchDTO);
        return mapper.toDTOList(carList);
    }

    private Car findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
    }
    private RestResourceNotFoundException createResourceNotFoundException(UUID id) {
        return RestResourceNotFoundException
                .builder()
                .resourceId(id)
                .resourceName("cars")
                .build();
    }
}
