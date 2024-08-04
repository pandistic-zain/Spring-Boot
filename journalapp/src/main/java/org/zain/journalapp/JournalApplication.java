package org.zain.journalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
		System.out.println("--------------------------");
		System.out.println("| Journal App Is Running |");
		System.out.println("--------------------------");
	}

}
