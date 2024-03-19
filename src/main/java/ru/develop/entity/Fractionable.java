package ru.develop.entity;

import ru.develop.my.annotations.Cache;
import ru.develop.my.annotations.Mutator;

public interface Fractionable {
    double doubleValue();
    void setNum(int num);
    void setDenum(int denum);
}
