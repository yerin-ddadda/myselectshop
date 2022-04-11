package com.sparta.week04_thr.Controller;

import com.sparta.week04_thr.models.ItemDto;
import com.sparta.week04_thr.utils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor
@RestController
public class SearchRequestController {

    private final NaverShopSearch naverShopSearch;

    @GetMapping("/api/search")
    public List<ItemDto> getItems(@RequestParam String query){ //requestparam은 필수여부가 true이기때문에 반드시 해당 파라미터가 전송되어야한다.
        String result = naverShopSearch.search(query); //물음표뒤에있는애 파라미터라고 일컫는데 변수로 받고싶다면 필요한 어노테이션
        return naverShopSearch.fromJSONtoItems(result);
    }
}

//@RequestParam :
//전달받은 데이터를 url상에서 찾는다. get방식으로 넘어온 url의 queryString을 받기 적절하다.
//ex) http://localhost:8080/receive?name=jun&age=13

//@PathVariable:
//url 경로의 일부를 파라미터로 사용할때 이용한다.

//@RequestBody
//http 요청의 body에 담긴 값을 자바 객체로 변환
//ex) http://localhost:8080/receive

//@RequestParam query 처리
//@PathVariable URI 변수 처리
//@RequestBody JSON 처리