package com.trevor.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

//接收前端傳過來的json object
public class CreateOrderRequest {
    @NotEmpty
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
