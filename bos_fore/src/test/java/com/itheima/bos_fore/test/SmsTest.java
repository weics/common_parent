package com.itheima.bos_fore.test;


import com.aliyuncs.exceptions.ClientException;
import com.itheima.bos_fore.utils.SMSUtils;
import org.junit.Test;

public class SmsTest {
	@Test
	public void test1(){
		try {
			SMSUtils.sendValidateCode("18600715065", "1234");
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
