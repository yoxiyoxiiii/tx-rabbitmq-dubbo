package com.example.txuser1.enums;


public enum MessageStatusEnum {

    WAIT_SEND(1,"待发送"),
    WAIT_USE(2,"待消费"),
    WAIT_CONFIRM(3,"待确认");

    private int index;
    private String description;

    MessageStatusEnum(int index, String description) {
        this.index = index;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }
}
