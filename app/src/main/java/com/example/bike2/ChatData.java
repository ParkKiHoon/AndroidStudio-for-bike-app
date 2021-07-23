package com.example.bike2;


public class ChatData {
    private String room_num;
    private String tv_nickname;
    private String tv_chat;
    private Long tv_time;
    public ChatData(String room_num, String tv_nickname, String tv_chat, Long tv_time) {
        this.room_num = room_num;
        this.tv_nickname = tv_nickname;
        this.tv_chat = tv_chat;
        this.tv_time = tv_time;
    }


    public String getRoom_num() {
        return room_num;
    }

    public void setRoom_num(String room_num) {
        this.room_num = room_num;
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
