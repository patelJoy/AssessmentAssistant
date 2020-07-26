package com.vgec.bean;

import java.sql.Date;
import java.sql.Time;

public class Test {
	int id;
	String subject;
	int duration;
	int batch;
	int fid;
	String category;
	Date date;
	Time fromtime;
	Time totime;
	int totalmarks;
	String department;
	int evaluated;
	
	public int getEvaluated() {
		return evaluated;
	}
	public void setEvaluated(int evaluated) {
		this.evaluated = evaluated;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getTotalmarks() {
		return totalmarks;
	}
	public void setTotalmarks(int totalmarks) {
		this.totalmarks = totalmarks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getBatch() {
		return batch;
	}
	public void setBatch(int batch) {
		this.batch = batch;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date2) {
		this.date = date2;
	}
	public Time getFromtime() {
		return fromtime;
	}
	public void setFromtime(Time fromtime) {
		this.fromtime = fromtime;
	}
	public Time getTotime() {
		return totime;
	}
	public void setTotime(Time totime) {
		this.totime = totime;
	}
}
