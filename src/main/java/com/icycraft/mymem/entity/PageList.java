package com.icycraft.mymem.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageList<T> {

    private List<T> list;

    private int count;

    private int page;
}
