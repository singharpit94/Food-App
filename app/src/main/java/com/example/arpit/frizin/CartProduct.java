package com.example.arpit.frizin;

/**
 * Created by arpit on 13/3/16.
 */
public class CartProduct {
    private String imgUrl;
    private String name;
    private String desc;
    private Long price;
    private int id;
    private int quantity;
    public CartProduct(){

    }
    public CartProduct(String name,Long price,String desc,int id,int quantity){
        this.name=name;
        this.desc=desc;

        this.price=price;
        this.id=id;
        this.quantity=quantity;
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
    public int getId() {
        return id;}

    public void setId(int id) {
        this.id=id;}
    public int getQty() {
        return quantity;}

    public void setQty(int qty) {
        this.quantity=qty;}



}
