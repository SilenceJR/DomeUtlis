package com.silence.recyclerbasedemo;

import android.graphics.drawable.Drawable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @作者: PJ
 * @创建时间: 2017/11/9 / 17:19
 * @描述: 这是一个 Student 类.
 */
public class Student implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    private String name;
    private int age;
    private int itemType;
    private Drawable mDrawable;

    public Student() {
        super();
    }

    public Student(String name, Drawable drawable, int age, int itemType) {
        this.name = name;
        this.age = age;
        this.itemType = itemType;
        mDrawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
