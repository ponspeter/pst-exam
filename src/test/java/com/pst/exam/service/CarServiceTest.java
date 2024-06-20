package com.pst.exam.service;

import com.pst.exam.dto.CarDTO;
import com.pst.exam.extension.TestContainerExtension;
import com.pst.exam.mapper.CarMapper;
import com.pst.exam.model.entity.Car;
import com.pst.exam.model.repo.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({TestContainerExtension.class})
@Slf4j
public class CarServiceTest {
    @InjectMocks
    private CarService carService;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper carMapper;

    @Test
    void whenRequestedGet_thenFetchAll() {

        List<Car> carList = new ArrayList<>();
        List<CarDTO> carDTOList = new ArrayList<>();

        given(carRepository.findAll())
                .willReturn(carList);
        given(carMapper.toDTOList(carList))
                .willReturn(carDTOList);

        List<CarDTO> result = carService.findAllCars();
        assertThat(result.size()).isNotNull();
    }

    @Test
    void whenRequestedGet_thenFetchById() {

        UUID uuid = UUID.randomUUID();

        Car car = new Car();
        Optional<Car> optionalCar = Optional.of(car);
        CarDTO carDTO = new CarDTO();

        given(carRepository.findById(uuid))
                .willReturn(optionalCar);
        given(carMapper.toDTO(car))
                .willReturn(carDTO);

        CarDTO result = carService.findCarById(uuid);
        assertThat(result).isNotNull();
    }
}
