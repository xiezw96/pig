package com.pig4cloud.test;

import com.pig4cloud.pig.PigAdminApplication;
import com.pig4cloud.pig.admin.service.SMSCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={PigAdminApplication.class,TestSMS.class})
public class TestSMS {

	@Autowired
	SMSCodeService sMSCodeService;
	
	@Test
	public void testSendMsg() {
		sMSCodeService.get("13665099356", "agent_regist");
	}
}
