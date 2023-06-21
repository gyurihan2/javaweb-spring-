package com.spring.javawebS.vo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class UserVO {
	private int idx;
	
	@Range(min=18, max=99,message = "나이 범위를 확인세요")
	private int age;

	private String address;
	
	@NotEmpty(message = "공백 안됨")
	@Size(min = 3, max = 20, message = "입력 범위 확인")
	private String mid, name;
}
