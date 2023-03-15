package com.cst438.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://registerf-cst438.herokuapp.com/"})
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	@PostMapping("/student")
	@Transactional
	public Student addStudent(@RequestBody StudentDTO student) {
		
		Student check = studentRepository.findByEmail(student.getEmail());
		
		
		if (check == null) {
			Student newStudent = new Student();
			newStudent.setStudent_id(student.student_id);
			newStudent.setName(student.name);
			newStudent.setEmail(student.email);
			newStudent.setStatus(student.status);
			newStudent.setStatusCode(student.statusCode);
			
			Student createdStudent = studentRepository.save(newStudent);
			
			return createdStudent;

		}
		else {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Email is already in use: " + student.email);
		}
	}
	
	
	@PostMapping("/createHold")
	@Transactional
	private int setHold(@RequestBody StudentDTO studentDTO) {
		String email = studentDTO.email;
		Student student = studentRepository.findByEmail(email);
		if (student != null)  {						
			if (student.getStatusCode() == -1) {	
				throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student with email: " + email + " Already has active hold");
			}
			student.setStatus("HOLD");
			student.setStatusCode(-1);
			return -1;
		}
		else throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student with email: " + email + " Doesn't exist");
	}
	
	@PostMapping("/deleteHold")
	@Transactional
	private int releaseHold(@RequestBody StudentDTO studentDTO) {
		String email = studentDTO.email;
		Student student = studentRepository.findByEmail(email);
		if (student != null)  {						
			if (student.getStatusCode() != -1) {	
				throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student with email: " + email + " has no active hold");
			}
			student.setStatus("No Holds");
			student.setStatusCode(0);
			return 0;
		}
		else throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student with email: " + email + " Doesn't exist");
	}

}
