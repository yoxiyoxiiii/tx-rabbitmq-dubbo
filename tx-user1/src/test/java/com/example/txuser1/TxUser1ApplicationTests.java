package com.example.txuser1;

import com.example.txuser1.entity.UserA;
import com.example.txuser1.service.UserAService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TxUser1ApplicationTests {

	@Autowired
	private UserAService userAService;
	@Test
	public void contextLoads() {
	}

	@Test
	public void addUser() {
		String id = userAService.add(UserA.builder().id(UUID.randomUUID().toString())
				.username("admin")
				.password("admin")
				.build());
		Assert.assertTrue(id!=null);
	}

}
