package com.example.bike2;


public class ChatActivityData {
    private String tv_nickname;
    private int is_right;
    private String tv_chat;
    private Long tv_time;

    public ChatActivityData(String tv_nickname, int is_right, String tv_chat, Long tv_time) {
        this.tv_nickname = tv_nickname;
        this.is_right=is_right;
        this.tv_chat = tv_chat;
        this.tv_time = tv_time;
    }


    public int getIs_right() {
        return is_right;
    }

    public void setIs_right(int is_right) {
        this.is_right = is_right;
    }

    public String getTv_nickname() {
        return tv_nickname;
    }

    public void setTv_nickname(String tv_nickname) {
        this.tv_nickname = tv_nickname;
    }

    public String getTv_chat() {
        return tv_chat;
    }

    public void setTv_chat(String tv_chat) {
        this.tv_chat = tv_chat;
    }

    public Long getTv_time() {
        return tv_time;
    }

    public void setTv_time(Long tv_time) {
        this.tv_time = tv_time;
    }
}
