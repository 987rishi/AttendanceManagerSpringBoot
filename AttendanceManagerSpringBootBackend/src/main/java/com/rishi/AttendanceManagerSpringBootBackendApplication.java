package com.rishi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AttendanceManagerSpringBootBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttendanceManagerSpringBootBackendApplication.class, args);
	}

}
 