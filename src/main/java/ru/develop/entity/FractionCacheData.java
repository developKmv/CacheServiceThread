package ru.develop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FractionCacheData {
    private int num;
    private int denum;
    private int timeout;
    private double doubleCacheValue;

}
