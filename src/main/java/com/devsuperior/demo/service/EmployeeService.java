package com.devsuperior.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.EmployeeDTO;
import com.devsuperior.demo.entities.Department;
import com.devsuperior.demo.entities.Employee;
import com.devsuperior.demo.repositories.DepartmentRepository;
import com.devsuperior.demo.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
	private DepartmentRepository depRepo;

	@Transactional(readOnly = true)
	public Page<EmployeeDTO> findAllPaged(Pageable pageable) {
		Page<Employee> result = repository.findAll(pageable);
		return result.map(x -> new EmployeeDTO(x));
	}
	
	@Transactional
	public EmployeeDTO insert(EmployeeDTO dto) {
		Employee entity = new Employee();
		Optional<Department> dep = depRepo.findById(dto.getDepartmentId());
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());		
//		entity.setDepartment(new Department(dto.getDepartmentId(), null));
		entity.setDepartment(dep.get());
		entity = repository.save(entity);
		return new EmployeeDTO(entity);
	}
}
