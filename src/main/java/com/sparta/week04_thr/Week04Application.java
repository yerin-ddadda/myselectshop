package com.sparta.week04_thr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //스케줄러가 있으니까 돌게해줘
@EnableJpaAuditing //jpa에서 타임스탬프 기능을 사용할때는 시간 자동 변경이 가능하도록
@SpringBootApplication
public class Week04Application {

    public static void main(String[] args) {
        SpringApplication.run(Week04Application.class, args);
    }

}