package com.example.expensemanager;

import java.io.Serializable;

public class ExpenseModdel implements Serializable {
    private String expenseId,note,categoty,type,uid;
    private long amount,time;

    public ExpenseModdel() {
    }

    public ExpenseModdel(String expenseId, String note, String categoty, String type, String uid, long amount, long time) {
        this.expenseId = expenseId;
        this.note = note;
        this.categoty = categoty;
        this.type = type;
        this.uid = uid;
        this.amount = amount;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategoty() {
        return categoty;
    }

    public void setCategoty(String categoty) {
        this.categoty = categoty;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
