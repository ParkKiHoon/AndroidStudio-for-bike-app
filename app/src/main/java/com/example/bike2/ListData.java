package com.example.bike2;

public class ListData {
    private String iv_image;
    private String tv_name;
    private String tv_time;
    private String tv_weight_price;

    public ListData(String iv_image, String tv_name,String tv_time,String tv_weight_price) {
        this.iv_image = iv_image;
        this.tv_name = tv_name;
        this.tv_time = tv_time;
        this.tv_weight_price =  tv_weight_price;
    }



    public String getIv_image() {
        return iv_image;
    }

    public void setIv_image(String iv_image) {
        this.iv_image = iv_image;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_time() {
        return tv_time;
    }

    public void setTv_time(String tv_time) {
        this.tv_time = tv_time;
    }

    public String getTv_weight_price() {
        return tv_weight_price;
    }

    public void setTv_weight_price(String tv_weight_price) {
        this.tv_weight_price = tv_weight_price;
    }
}
