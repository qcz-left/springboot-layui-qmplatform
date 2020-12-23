package com.qcz.qmplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.qcz.qmplatform.**.mapper")
public class QmplatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(QmplatformApplication.class, args);
    }
}
