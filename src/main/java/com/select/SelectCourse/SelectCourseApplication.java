package com.select.SelectCourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DatabaseDriver;

@SpringBootApplication
public class SelectCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelectCourseApplication.class, args);
	}

}
