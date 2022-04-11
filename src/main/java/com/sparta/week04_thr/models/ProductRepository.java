package com.sparta.week04_thr.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> { //Spring Boot에선 Entity의 기본적인 CRUD가 가능하도록 JpaRepository Interface를 제공,사용할 Entity Class, Long에는 ID 값이 들어가게 된다.
}
