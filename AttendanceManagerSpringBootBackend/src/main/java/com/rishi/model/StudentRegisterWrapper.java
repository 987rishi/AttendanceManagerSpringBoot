package com.rishi.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegisterWrapper {
	
	
	@NotBlank
	private String name;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	
	@Email
	private String email;
	private Integer semester;

}
