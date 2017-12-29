package com.itheima.bos_fore.test;


import org.junit.Test;

public class SmsTest {
	@Test
	public void test1(){
		try {
			SMSUtils.sendValidateCode("15833331095", "1234");
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
