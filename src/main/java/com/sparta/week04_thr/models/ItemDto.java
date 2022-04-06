package com.sparta.week04_thr.models;

import lombok.Getter;
import org.json.JSONObject;

@Getter
public class ItemDto {
    //내가 가져온 정보를 클라이언트에게 돌려주기
    private String title;
    private String link;
    private String image;
    private int lprice;

    public ItemDto(JSONObject itemJson){
        this.title = itemJson.getString("title");
        this.image = itemJson.getString("image");
        this.link = itemJson.getString("link");
        this.lprice = itemJson.getInt("lprice");
    }
}
