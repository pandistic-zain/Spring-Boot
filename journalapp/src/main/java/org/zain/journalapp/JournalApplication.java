package org.zain.journalapp;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = "org.zain.journalapp.DAO")

public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
		System.out.println("--------------------------");
		System.out.println("| Journal App Is Running |");
		System.out.println("--------------------------");
	}
	@Bean
	public PlatformTransactionManager Manager (MongoDatabaseFactory factory){
		return new MongoTransactionManager(factory);
	}

}
