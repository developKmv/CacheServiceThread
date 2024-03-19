package ru.develop.entity;

import ru.develop.my.annotations.Cache;
import ru.develop.my.annotations.Mutator;
public class Fraction implements Fractionable{
    private int num;
    private int denum;

    public Fraction(int num,int denum){
        this.num = num;
        this.denum = denum;
    }

    @Override
    @Mutator
    public void setNum(int num) {
        if (num==0)throw new IllegalArgumentException();
        this.num = num;
    }

    @Override
    @Mutator
    public void setDenum(int denum) {
        this.denum = denum;
    }
    @Override
    @Cache(lifetime = 1000)
    public double doubleValue() {
        System.out.println("invoke double value");
        return (double) num/denum;
    }
}
