package com.example.txuser1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Administrator
 */
@ImportResource(locations = "classpath:provider-user.xml")
@SpringBootApplication(scanBasePackages = "com.example")
//@ComponentScan(basePackages = "com.example")
public class TxUser1Application {

	public static void main(String[] args) {
		SpringApplication.run(TxUser1Application.class, args);
	}

}
