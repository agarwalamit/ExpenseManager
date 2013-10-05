package com.expense.manager;

public class Details {
	String name,amount,time,color;
	public Details(String name,String amount,String time,String color){
		this.name=name;
		this.amount=amount;
		this.time=time;
		this.color=color;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setTime(String time){
		this.time=time;
	}
	public void setAmount(String amount){
		this.amount=amount;
	}
	public void setColor(String color){
		this.color=color;
	}
	public String getTime(){
		return this.time;
	}
	public String getName(){
		return this.name;
	}
	public String getAmount(){
		return this.amount;
	}
	public String getColor(){
		return this.color;
	}

}
