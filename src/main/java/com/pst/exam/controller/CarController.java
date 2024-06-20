package com.pst.exam.controller;

import com.pst.exam.dto.CarDTO;
import com.pst.exam.dto.CarDTOWrapper;
import com.pst.exam.dto.SearchDTO;
import com.pst.exam.exception.RestResourceNotFoundException;
import com.pst.exam.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class CarController {

    private final String REDIRECT = "redirect:/index";
    private final String INDEX = "index";
    private final String EDIT_CAR = "edit-car";
    private final String ADD_CAR = "add-car";
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String showCarList(Model model,HttpServletRequest request) {
        List<CarDTO> list = service.findAllCars();
        request.getSession().setAttribute("cars", list);
        model.addAttribute("cars", list);
        return INDEX;
    }

    @GetMapping("/add")
    public String showAddCar(CarDTO carDTO) {
        return "add-car";
    }

    @GetMapping("/edit/{id}")
    public String showEditCar(@PathVariable("id") UUID id, Model model) {
        CarDTO carDTO = service.findCarById(id);
        model.addAttribute("car", carDTO);
        return EDIT_CAR;
    }

    @PostMapping("/save")
    public String addCar(CarDTO car, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return ADD_CAR;
        }
        service.save(car);
        return REDIRECT;
    }

    @PostMapping("/update/{id}")
    public String updateCar(@PathVariable("id") UUID id, CarDTO car, BindingResult result, Model model) throws RestResourceNotFoundException {
        if (result.hasErrors()) {
            car.setId(id);
            return EDIT_CAR;
        }

        service.update(id, car);
        return REDIRECT;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID id, Model model) {
        service.deleteCar(id);
        return REDIRECT;
    }

    @PostMapping(value = "/search")
    public String searchCars(Model model, HttpServletRequest request) {
        String field = request.getParameter("field");
        String operation = request.getParameter("operation");
        String param1 = request.getParameter("param1");
        String param2 = request.getParameter("param2");

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setField(field);
        searchDTO.setOperation(operation);
        searchDTO.setParam1(param1);
        searchDTO.setParam2(param2);

        List<CarDTO> list = null;

        if (searchDTO.getField().equals("all")) {
            list = service.findAllCars();
            request.getSession().setAttribute("cars", list);
            model.addAttribute("cars", list);
        } else {
            list = service.searchCars(searchDTO);
            request.getSession().setAttribute("cars", list);
            model.addAttribute("cars", list);
        }

        return INDEX;
    }

    @RequestMapping(value = "/download", params = "btnDownload", method = RequestMethod.POST)
    public void download(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

        List<CarDTO> list = (List<CarDTO>) request.getSession().getAttribute("cars");

        CarDTOWrapper carDTOWrapper = new CarDTOWrapper();
        carDTOWrapper.setCar(list);

        JAXBContext context = JAXBContext.newInstance(CarDTOWrapper.class, CarDTO.class);
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        String filename = "Cars.xml";

        File file = new File(filename);
        try (PrintStream ps = new PrintStream(file)) {
            jaxbMarshaller.marshal(carDTOWrapper, ps);
        }
        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader( "Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));

        OutputStream out = response.getOutputStream();
        try (FileInputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
        out.flush();
    }
}
