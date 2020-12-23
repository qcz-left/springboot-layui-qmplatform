package com.qcz.qmplatform;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QmplatformApplication.class)
@WebAppConfiguration
public class TestJasypt {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void test() {
        System.out.println(encryptor.encrypt("password"));
    }

}
