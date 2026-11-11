package com.example.universitysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@RestController
public class UniversitySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversitySystemApplication.class, args);
	}


/*
	@GetMapping

	public List<Student> hello() {
		return List.of(
				new Student(1L, "Mariam", "mariam.jamal@gmail.com", LocalDate.of(2000, Month.JANUARY, 5), 21)
		);
	}

*/

		/*
		Note: the output was: ["Hello","World"]


	public String hello() {
		List<String> words = List.of("Hello", "World");
		return String.join("\n", words);
	}
	 */
}
