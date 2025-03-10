// PositionService.java
package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dao.PositionDao;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.model.Position;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionService {

    private final PositionDao positionDao;

    @Autowired
    public PositionService(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    @Transactional(readOnly = true)
    public Optional<Position> getPositionById(Long id) {
        return positionDao.findById(id);
    }
    @Transactional(readOnly = true)
    public Optional<Position> getPositionByName(String name) { // Добавляем для CommandLineRunner
        return positionDao.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Position> getAllPositions() {
        return positionDao.findAll();
    }

    @Transactional
    public Position createPosition(Position position) {
        return positionDao.save(position);
    }

    @Transactional
    public Position updatePosition(Long id, Position positionDetails) {
        Position position = positionDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Position not found with id " + id));

        position.setName(positionDetails.getName());
        position.setDescription(positionDetails.getDescription());
        position.setMinSalary(positionDetails.getMinSalary());
        position.setMaxSalary(positionDetails.getMaxSalary());

        //Связи обновляем, если приходят не null значения
        if(positionDetails.getEmployees() != null && !positionDetails.getEmployees().isEmpty())
        {
            position.setEmployees(positionDetails.getEmployees());
        }
        return positionDao.save(position);
    }

    @Transactional
    public void deletePosition(Long id) {
        positionDao.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Position not found with id " + id));
        positionDao.deleteById(id);
    }
}