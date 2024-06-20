package com.pst.exam.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "cars")
public class CarDTOWrapper {
    private List<CarDTO> car;
}
