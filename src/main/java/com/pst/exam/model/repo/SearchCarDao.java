package com.pst.exam.model.repo;

import com.pst.exam.dto.SearchDTO;
import com.pst.exam.enums.FieldEnum;
import com.pst.exam.enums.OperationEnum;
import com.pst.exam.model.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class SearchCarDao {
    private final EntityManager em;
    public SearchCarDao(EntityManager em) {
        this.em = em;
    }

    public List<Car> processSearch(SearchDTO searchDTO) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Car> cq = cb.createQuery(Car.class);
        Root<Car> car = cq.from(Car.class);
        Predicate predicate = null;

        if (searchDTO.getField().equalsIgnoreCase(FieldEnum.COLOR.toString())) {
            predicate = identifyColorPredicate(cb, searchDTO, car).get(searchDTO.getOperation());
        } else if (searchDTO.getField().equalsIgnoreCase(FieldEnum.LENGTH.name())) {
            predicate = identifyLengthPredicate(cb, searchDTO, car).get(searchDTO.getOperation());
        } else if (searchDTO.getField().equalsIgnoreCase(FieldEnum.WEIGHT.name())) {
            predicate = identifyWeightPredicate(cb, searchDTO, car).get(searchDTO.getOperation());
        }else {
            predicate = identifyVelocityPredicate(cb, searchDTO, car).get(searchDTO.getOperation());
        }

        cq.where(predicate);

        TypedQuery<Car> query = em.createQuery(cq);
        return query.getResultList();
    }



    private Map<String, Predicate> identifyColorPredicate(CriteriaBuilder cb, SearchDTO searchDTO, Root<Car> car) {
        Map<String, Predicate> map = new HashMap<>();
        map.put(OperationEnum.EQUAL.toString(), cb.equal(car.get(FieldEnum.COLOR.toString().toLowerCase()), searchDTO.getParam1().toLowerCase()));
        map.put(OperationEnum.NOT_EQUAL.toString(), cb.notEqual(car.get(FieldEnum.COLOR.toString().toLowerCase()), searchDTO.getParam1().toLowerCase()));
        map.put(OperationEnum.LIKE.toString(), cb.like(car.get(FieldEnum.COLOR.toString().toLowerCase()), "%" + searchDTO.getParam1().toLowerCase() + "%" ));
        return map;
    }

    private Map<String, Predicate> identifyLengthPredicate(CriteriaBuilder cb, SearchDTO searchDTO, Root<Car> car) {
        Map<String, Predicate> map = new HashMap<>();
        map.put(OperationEnum.EQUAL.toString(), cb.equal(car.get(FieldEnum.LENGTH.toString().toLowerCase()), searchDTO.getParam1().toLowerCase()));
        map.put(OperationEnum.NOT_EQUAL.toString(), cb.notEqual(car.get(FieldEnum.LENGTH.toString().toLowerCase()), searchDTO.getParam1().toLowerCase()));
        map.put(OperationEnum.GREATER_THAN_OR_EQUAL_TO.toString(), cb.greaterThanOrEqualTo(car.get(FieldEnum.LENGTH.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        map.put(OperationEnum.LESS_THAN_OR_EQUAL_TO.toString(), cb.lessThanOrEqualTo(car.get(FieldEnum.LENGTH.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        map.put(OperationEnum.GREATER_THAN.toString(), cb.greaterThan(car.get(FieldEnum.LENGTH.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        map.put(OperationEnum.LESS_THAN.toString(), cb.lessThan(car.get(FieldEnum.LENGTH.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        if (searchDTO.getParam2().length() > 0) {
            map.put(OperationEnum.BETWEEN.toString(), cb.between(car.get(FieldEnum.LENGTH.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase()), Double.parseDouble(searchDTO.getParam2().toLowerCase())));
        }
        return map;
    }

    private Map<String, Predicate> identifyWeightPredicate(CriteriaBuilder cb, SearchDTO searchDTO, Root<Car> car) {
        Map<String, Predicate> map = new HashMap<>();
        map.put(OperationEnum.EQUAL.toString(), cb.equal(car.get(FieldEnum.WEIGHT.toString().toLowerCase()), searchDTO.getParam1().toLowerCase()));
        map.put(OperationEnum.NOT_EQUAL.toString(), cb.notEqual(car.get(FieldEnum.WEIGHT.toString().toLowerCase()), searchDTO.getParam1().toLowerCase()));
        map.put(OperationEnum.GREATER_THAN_OR_EQUAL_TO.toString(), cb.greaterThanOrEqualTo(car.get(FieldEnum.WEIGHT.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        map.put(OperationEnum.LESS_THAN_OR_EQUAL_TO.toString(), cb.lessThanOrEqualTo(car.get(FieldEnum.WEIGHT.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        map.put(OperationEnum.GREATER_THAN.toString(), cb.greaterThan(car.get(FieldEnum.WEIGHT.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        map.put(OperationEnum.LESS_THAN.toString(), cb.lessThan(car.get(FieldEnum.WEIGHT.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        if (searchDTO.getParam2().length() > 0) {
            map.put(OperationEnum.BETWEEN.toString(), cb.between(car.get(FieldEnum.WEIGHT.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase()), Double.parseDouble(searchDTO.getParam2().toLowerCase())));
        }
        return map;
    }

    private Map<String, Predicate> identifyVelocityPredicate(CriteriaBuilder cb, SearchDTO searchDTO, Root<Car> car) {
        Map<String, Predicate> map = new HashMap<>();
        map.put(OperationEnum.EQUAL.toString(), cb.equal(car.get(FieldEnum.VELOCITY.toString().toLowerCase()), searchDTO.getParam1().toLowerCase()));
        map.put(OperationEnum.NOT_EQUAL.toString(), cb.notEqual(car.get(FieldEnum.VELOCITY.toString().toLowerCase()), searchDTO.getParam1().toLowerCase()));
        map.put(OperationEnum.GREATER_THAN_OR_EQUAL_TO.toString(), cb.greaterThanOrEqualTo(car.get(FieldEnum.VELOCITY.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        map.put(OperationEnum.LESS_THAN_OR_EQUAL_TO.toString(), cb.lessThanOrEqualTo(car.get(FieldEnum.VELOCITY.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        map.put(OperationEnum.GREATER_THAN.toString(), cb.greaterThan(car.get(FieldEnum.VELOCITY.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        map.put(OperationEnum.LESS_THAN.toString(), cb.lessThan(car.get(FieldEnum.VELOCITY.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase())));
        if (searchDTO.getParam2().length() > 0) {
            map.put(OperationEnum.BETWEEN.toString(), cb.between(car.get(FieldEnum.VELOCITY.toString().toLowerCase()), Double.parseDouble(searchDTO.getParam1().toLowerCase()), Double.parseDouble(searchDTO.getParam2().toLowerCase())));
        }
        return map;
    }
}
