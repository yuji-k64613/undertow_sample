package com.demo;

import org.springframework.stereotype.Component;

@Component
public class AccountServiceImpl implements AccountService {
	@Override
	public String getName() {
		return "foo";
	}
}
