package com.rustam.e_commerce.controller;

import com.rustam.e_commerce.dto.request.EmployeeRequestCreate;
import com.rustam.e_commerce.dto.request.EmployeeUpdateRequest;
import com.rustam.e_commerce.dto.response.EmployeeCreateResponse;
import com.rustam.e_commerce.dto.response.EmployeeDeletedResponse;
import com.rustam.e_commerce.dto.response.EmployeeResponse;
import com.rustam.e_commerce.dto.response.EmployeeUpdateResponse;
import com.rustam.e_commerce.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(path = "/create")
    public ResponseEntity<EmployeeCreateResponse> create(@RequestBody EmployeeRequestCreate employeeRequestCreate){
        return new ResponseEntity<>(employeeService.create(employeeRequestCreate), HttpStatus.CREATED);
    }

    @GetMapping(path = "/read-by-id/{id}")
    public ResponseEntity<EmployeeResponse> readById(@PathVariable UUID id){
        return new ResponseEntity<>(employeeService.read(id),HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<EmployeeUpdateResponse> update(@RequestBody EmployeeUpdateRequest employeeUpdateRequest){
        return new ResponseEntity<>(employeeService.update(employeeUpdateRequest),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<EmployeeDeletedResponse> delete(@PathVariable UUID id){
        return new ResponseEntity<>(employeeService.delete(id),HttpStatus.ACCEPTED);
    }
}
