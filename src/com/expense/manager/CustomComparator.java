package com.expense.manager;
import java.util.Comparator;

public class CustomComparator implements Comparator<Details> {
    @Override
    public int compare(Details o1, Details o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
