package com.jyujyu.dayonetest;

public class MyCalculatorApplication {

    public static void main(String[] args) {
        MyCalculator cal = new MyCalculator();

        cal.add(10.0);
        cal.minus(2.0);
        cal.multiply(2.0);

        cal.divide(2.0);

        System.out.println(cal.getResult());
    }
}
