package com.silence.duimymoneylibrary;

/**
 * @作者: PJ
 * @创建时间: 2017/11/1 / 18:50
 * @描述: 这是一个 bean 类.
 */
public class bean {
    private String balance;

    public bean() {
    }

    public bean(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "bean{" +
                "balance='" + balance + '\'' +
                '}';
    }
}
