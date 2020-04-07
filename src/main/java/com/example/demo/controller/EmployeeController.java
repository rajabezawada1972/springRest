package com.example.demo.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeRepository emplooyeeRepository;
	
	@Autowired
	private RestTemplate restTemplate1;

	@PostMapping("saveEmployee")
	public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee) {
		System.out.println("inside saveEmployee method...........");
		Employee emp = emplooyeeRepository.save(employee);
		return ResponseEntity.ok().body(emp);
	}

	@GetMapping("getEmployeeById/{empId}")
	public ResponseEntity<Object> getEmployeeById(@PathVariable("empId") int empId) {
		System.out.println("inside getEmployeeById..............");
		try {
			Optional<Employee> empById = emplooyeeRepository.findById(empId);
			return ResponseEntity.ok().body(empById);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("");
		}
		// return ResponseEntity.badRequest().body("");
	}

	@GetMapping("getAllEmployeeData")
	public ResponseEntity<Object> getAllEmployeeData() {
		List<Employee> listData = emplooyeeRepository.findAll();
		return ResponseEntity.ok().body(listData);
	}

	@PutMapping("updateEmployeeData/{empId}")
	public ResponseEntity<Employee> updateEmployeeData(@Valid @PathVariable("empId") int empId,
			@RequestBody Employee employee) {

		employee.setEmpId(empId);
		Employee empUpdateId = emplooyeeRepository.save(employee);
		return ResponseEntity.ok().body(empUpdateId);
	}

	@DeleteMapping("deleteEmployeeData/{empId}")
	public ResponseEntity<Object> deleteEmployeeData(@PathVariable("empId") int empId) {
		emplooyeeRepository.deleteById(empId);
		return ResponseEntity.ok().body("done ...........");
	}
	
	//@QueryParam Example................
	@GetMapping("byUsingQueryParam")
	//@Path("/data")
	public ResponseEntity<Object> byUsingQueryParam(@QueryParam("empName") String empName,@QueryParam("empCity") String empCity){
        
		String response = "empName.."+empName;
		
		return ResponseEntity.ok().body(response);
	}
		//PathParam
	@GetMapping("byUsingPathParam/{empName}/{empCity}")
	public ResponseEntity<Object> byUsingPathParam(@PathParam("empName") String empName,@PathParam("empCity") String empCity){
		
		String responseData = "empName.."+empName+"empCity..."+empCity;
		
		
		return ResponseEntity.ok().body(responseData);
	}
	
	//byUsing restTemplate
	
	/*
	 * @GetMapping("byUsingRestTemplate") public ResponseEntity<Object>
	 * byUsingRestTemplate(){ String responseUrl =
	 * "http://localhost:8080/getAllEmployeeData"; RestTemplate restTemplate = new
	 * RestTemplate(); String data =
	 * restTemplate.getForObject(responseUrl,String.class);
	 * System.out.println(restTemplate.toString()+"--"+data); return
	 * ResponseEntity.ok().body(data); }
	 */
	
	//another way of using restTemplate
	
	@GetMapping("byUsingNewRestTemplate")
	public ResponseEntity<ResponseEntity<String>> byUsingNewRestTemplate(){
		String url = "http://localhost:8080/getAllEmployeeData";
		
	      HttpHeaders headers = new org.springframework.http.HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<Employee> entity = new HttpEntity<Employee>(headers);
	      
		ResponseEntity<String> dataResponse = restTemplate1.exchange(url, HttpMethod.GET, entity, String.class);
		
		return ResponseEntity.ok().body(dataResponse);
	}
}
