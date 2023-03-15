package com.cst438.domain;

public class StudentDTO {
	public int student_id;
	public String name;
	public String email;
	public String status;
	public int statusCode;
	
	@Override
	public String toString() {
		return "StudentDTO [id=" + student_id + ", student_name=" + name + ", student_email=" + email +
				", status=" + status + ", status_code=" + statusCode + "]";
	}
	
	public String getEmail() { return email;}
	public String getName() { return name;}

}

	

