package com.rishi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRegisterWrapper {

	@NotBlank
	private String name;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@Email
	private String email;
}
