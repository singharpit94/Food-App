package com.example.arpit.frizin;

/**
 * Created by arpit on 11/3/16.
 */
public class Product {
    private String imgUrl;
    private String name;
    private String desc;
    private Long price;
    public Product(){

    }
    public Product(String name,Long price,String desc){
        this.name=name;
        this.desc=desc;

        this.price=price;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }



}
