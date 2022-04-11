package com.sparta.week04_thr.models;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter // get 함수를 자동 생성합니다. 정보를 조회할 수 있도록
@MappedSuperclass // createdAt,modifiedAt 상속한 클래스에 멤버 변수가 컬럼이 되도록 합니다.
@EntityListeners(AuditingEntityListener.class) // 변경되었을 때 자동으로 기록합니다. 클래스에 Auditing 기능을 포함시킵니다.
public abstract class Timestamped { //생성하는게 없고 상속이 돼서 다른 데서 사용되게
    @CreatedDate // 최초 생성 시점,Entity가 생성되어 저장될 때 시간이 자동 저장
    private LocalDateTime createdAt;

    @LastModifiedDate // 마지막 변경 시점,조회한 Entity의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedAt;
}

//데이터베이스에서 누가, 언제하였는지 기록을 잘 남겨놓아야 합니다. 그렇기 때문에 생성일, 수정일 컬럼은 대단히 중요한 데이터 입니다.
//그래서 JPA에서는 Audit이라는 기능을 제공하고 있습니다. Audit은 감시하다, 감사하다라는 뜻으로 Spring Data JPA에서 시간에 대해서 자동으로 값을 넣어주는 기능입니다.