package com.vgec.bean;

public class Marksheet {
	int id;
	int marks;
	String question;
	String co;
	String answer;
	int marksobt;
	String actualans;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCo() {
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getMarksobt() {
		return marksobt;
	}
	public void setMarksobt(int marksobt) {
		this.marksobt = marksobt;
	}
	public String getActualans() {
		return actualans;
	}
	public void setActualans(String actualans) {
		this.actualans = actualans;
	}
	@Override
	public String toString() {
		return "Marksheet [id=" + id + ", marks=" + marks + ", question=" + question + ", co=" + co + ", answer="
				+ answer + ", marksobt=" + marksobt + ", actualans=" + actualans + "]";
	}
	
}
