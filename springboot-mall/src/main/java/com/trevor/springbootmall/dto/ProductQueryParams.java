package com.trevor.springbootmall.dto;

import com.trevor.springbootmall.constant.ProductCategory;

public class ProductQueryParams {
    private ProductCategory category;
    private String search;

    private String orderByl;
    private  String sort;

    public String getOrderByl() {
        return orderByl;
    }

    public void setOrderByl(String orderByl) {
        this.orderByl = orderByl;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
