package com.pst.exam.mapper;

import com.pst.exam.dto.CarDTO;
import com.pst.exam.model.entity.Car;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {
    Car toEntity(CarDTO dto);
    CarDTO toDTO(Car car);
    List<CarDTO> toDTOList(List<Car> carList);
}
