package com.expense.manager;
import java.util.Comparator;

public class CustomComparator_by_amount implements Comparator<Details> {
    @Override
    public int compare(Details o1, Details o2) {
    	Integer i1=Integer.parseInt(o1.getAmount());
    	Integer i2=Integer.parseInt(o2.getAmount());
    	if(i1==i2)return 0;
    	else if(i1>i1)return 1;
    	else if (i1<i2)return -1;
    	return 1;
        //return o1.getAmount().compareTo(o2.getAmount());//problem        
    }
}
