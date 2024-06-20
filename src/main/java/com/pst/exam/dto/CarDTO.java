package com.pst.exam.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.UUID;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CarDTO implements Serializable {
    private UUID id;
    private double length;
    private double weight;
    private double velocity;
    private String color;
}
