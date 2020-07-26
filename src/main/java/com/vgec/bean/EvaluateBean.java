package com.vgec.bean;

public class EvaluateBean{
	int qid;
	String erno;
	String answer;
	double marks;
	
	public double getMarks() {
		return marks;
	}
	public void setMarks(double a) {
		this.marks = a;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getErno() {
		return erno;
	}
	public void setErno(String erno) {
		this.erno = erno;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Override
    public String toString() { 
        return String.format(qid + " " + erno + " " + answer + " " + marks); 
    } 
}
