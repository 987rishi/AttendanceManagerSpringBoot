package com.rishi.model;

import java.util.HashMap;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceHelper {
	@NotBlank
	private Integer teacherId;
	@NotBlank
	private Integer courseId;
	@NotNull
	private Set<Integer> absenteesIds;

}
