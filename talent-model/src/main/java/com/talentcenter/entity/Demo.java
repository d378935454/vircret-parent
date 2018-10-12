package com.talentcenter.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name="talent_demo")
public class Demo {
    private Long id;

    private String name;

    private int age;
}
