package com.rustam.e_commerce.service;

import com.rustam.e_commerce.dao.entity.user.BaseUser;
import com.rustam.e_commerce.dao.entity.user.Employee;
import com.rustam.e_commerce.dao.repository.EmployeeRepository;
import com.rustam.e_commerce.dto.request.EmployeeRequestCreate;
import com.rustam.e_commerce.dto.request.EmployeeUpdateRequest;
import com.rustam.e_commerce.dto.response.EmployeeCreateResponse;
import com.rustam.e_commerce.dto.response.EmployeeDeletedResponse;
import com.rustam.e_commerce.dto.response.EmployeeResponse;
import com.rustam.e_commerce.dto.response.EmployeeUpdateResponse;
import com.rustam.e_commerce.exception.custom.ExistsException;
import com.rustam.e_commerce.mapper.EmployeeMapper;
import com.rustam.e_commerce.util.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class EmployeeService {

    EmployeeRepository employeeRepository;
    UtilService utilService;
    PasswordEncoder passwordEncoder;
    EmployeeMapper employeeMapper;
    ModelMapper modelMapper;

    public EmployeeCreateResponse create(EmployeeRequestCreate employeeRequestCreate) {
        BaseUser baseUser = utilService.findById(employeeRequestCreate.getId());
        Employee employee = Employee.builder()
                .name(baseUser.getName())
                .surname(baseUser.getSurname())
                .phone(baseUser.getPhone())
                .enabled(true)
                .startWorkDay(LocalDate.now())
                .iban(utilService.generateIbanForUser())
                .build();
        if (employeeRequestCreate.getUsername().isBlank()){
            employee.setUsername(employeeRequestCreate.getUsername());
        }
        if (employeeRequestCreate.getEmail().isBlank()){
            employee.setEmail(employeeRequestCreate.getEmail());
        }
        if (employeeRequestCreate.getPassword().isBlank()){
            employee.setPassword(passwordEncoder.encode(employeeRequestCreate.getPassword()));
        }
        employeeRepository.save(employee);
        EmployeeCreateResponse.builder().text("Good luck in your new position.").build();
        return employeeMapper.toResponse(employee);
    }

    public EmployeeResponse read(UUID id) {
        BaseUser user = utilService.findById(id);
        return employeeMapper.toRead(user);
    }

    public EmployeeUpdateResponse update(EmployeeUpdateRequest employeeUpdateRequest) {
        String currentUsername = utilService.getCurrentUsername();
        Employee employee = utilService.findByEmployeeId(employeeUpdateRequest.getId());
        utilService.validation(currentUsername, employee.getId());
        boolean exists = employeeRepository.findAll().stream()
                .map(Employee::getUsername)
                .anyMatch(existingUsername -> existingUsername.equals(employeeUpdateRequest.getUsername()));
        if (exists) {
            throw new ExistsException("This username is already taken.");
        }
        modelMapper.map(employeeUpdateRequest, employee);
        employeeRepository.save(employee);
        return employeeMapper.toUpdated(employee);
    }

    public EmployeeDeletedResponse delete(UUID id) {
        Employee employee = utilService.findByEmployeeId(id);
        String currentUsername = utilService.getCurrentUsername();
        utilService.validation(employee.getId(), currentUsername);
        EmployeeDeletedResponse deletedResponse = new EmployeeDeletedResponse();
        modelMapper.map(employee, deletedResponse);
        deletedResponse.setText("This user was deleted by you.");
        employeeRepository.delete(employee);
        return deletedResponse;
    }
}
