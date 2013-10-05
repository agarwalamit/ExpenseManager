package com.expense.manager;
public class TransactionDetails {
	String amount,reason,time,id;
	public TransactionDetails(String amount,String reason,String time,String id){
		this.amount=amount;
		this.reason=reason;
		this.time=time;
		this.id=id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return this.id;
	}
	public void setAmount(String amount){
		this.amount=amount;
	}
	public String getAmount(){
		return this.amount;
	}
	public void setReason(String reason){
		this.reason=reason;
	}
	public String getReason(){
		return this.reason;
	}
	public String getTime(){
		return this.time;
	}
	public void setTime(String time){
		this.time=time;
	}
}
