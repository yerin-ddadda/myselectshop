package com.sparta.week04_thr.models;

import lombok.Getter;
import org.json.JSONObject;

//setter는 생성자를 통해서 하기때문에
@Getter
public class ItemDto {
    private String link;
    private String image;
    private String title;
    private int lprice;

    //생성자를 통해서 주입
    public ItemDto(JSONObject itemJson){
        this.title = itemJson.getString("title");
        this.image = itemJson.getString("image");
        this.link = itemJson.getString("link");
        this.lprice = itemJson.getInt("lprice");
    }
}

