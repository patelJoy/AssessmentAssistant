package com.vgec.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.vgec.bean.EvaluateBean;
import com.vgec.bean.Marksheet;
import com.vgec.bean.Question;
import com.vgec.bean.Test;
import com.vgec.util.DB_Connection;

public class TestDAO {
	
	public static ArrayList<Marksheet> getMarkSheet(int tid, String erno){
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ArrayList<Marksheet> ts = new ArrayList<Marksheet>();
		
		String query = "select * from questions_"+tid;
		String query1 = "select * from answers_"+tid+" where a_erno=?";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			Marksheet t = null;
			while(rs.next()) {
				if(rs.getInt("q_type") == 1) {
					t = new Marksheet();
					t.setId(rs.getInt("q_id"));
					t.setMarks(rs.getInt("q_marks"));
					t.setQuestion(rs.getString("q_question"));
					t.setAnswer(rs.getString("q_answer"));
					t.setCo(rs.getString("q_co"));
					ts.add(t);
				}
			}
			
			ps1 = con.prepareStatement(query1);
			ps1.setString(1, erno);
			rs1 = ps1.executeQuery();
			
			if(rs1.next()) {
				int size = ts.size();
				String[] parts = rs1.getString("a_markslist").split(",");
				for(int i=0;i<size;i++) {
					ts.get(i).setActualans(rs1.getString("a_"+ts.get(i).getId()));
					ts.get(i).setMarksobt(Integer.parseInt(parts[ts.get(i).getId()-1]));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		System.out.println(ts);
		return ts;
	}
	
	
	public static void updateMarks(ArrayList<EvaluateBean> eb, int tid) {
		Collections.sort(eb, new Comparator<EvaluateBean>() {
			  public int compare(EvaluateBean u1, EvaluateBean u2) {
			    return (int) (Long.parseLong(u1.getErno()) - Long.parseLong(u2.getErno()));
			  }
			});
		
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		String query = "select q_marks from questions_"+tid;
		ArrayList<Integer> marks = new ArrayList<Integer>();
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				marks.add(rs.getInt(1));
			}
			int size = eb.size();
			for(int i=0;i<size;i++) {
				double a = eb.get(i).getMarks();
				eb.get(i).setMarks(Math.round(marks.get(eb.get(i).getQid()-1)*a));
			}
			
			String query1 = "select a_erno, a_markslist from answers_"+tid;
			ps1 = con.prepareStatement(query1);
			rs1 = ps1.executeQuery();
			
			HashMap<String,ArrayList<Integer>> nice = new HashMap<String,ArrayList<Integer>>(); 
			while(rs1.next()) {
				if(rs1.getString(2) != null) {
					List<Integer> numbers = Stream.of(rs1.getString(2).split(","))
			                .map(Integer::parseInt)
			                .collect(Collectors.toList());
					nice.put(rs1.getString(1), (ArrayList<Integer>) numbers);
				}
			}
			
			for(int i=0;i<size;i++) {
				if(nice.containsKey(eb.get(i).getErno()))
					nice.get(eb.get(i).getErno()).set(eb.get(i).getQid()-1, (int)Math.round(eb.get(i).getMarks()));
			}
			
		    Iterator iterator = nice.keySet().iterator();
		    int batchSize = 20;
			con.setAutoCommit(false);
			String query4 = "update answers_"+tid+" set a_markslist=? where a_erno=?";
			PreparedStatement ps4 = null;
			int j=0;
		    while (iterator.hasNext()) {
		    	j++;
		    	String key = iterator.next().toString();
		    	String out = "";
			    int siz = nice.get(key).size();
			    for(int i=0;i<siz;i++) {
			    	if(i == (siz-1)) {
			    		out += ""+nice.get(key).get(i);
			    	}else {
			    		out += nice.get(key).get(i) + ",";
			    	}
			    }
				ps4 = con.prepareStatement(query4);
				ps4.setString(1, out);
			    ps4.setString(2, key);
			    ps4.addBatch();
			    
			    if (j % batchSize == 0) {
                    ps4.executeBatch();
                }
		    }
		    ps4.executeBatch();
		    con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	public static ArrayList<EvaluateBean> getEvaluateHash(ArrayList<Integer> qid, int tid) {
	    ArrayList<EvaluateBean> eb = new ArrayList<EvaluateBean>();
	    
	    Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		int size = qid.size();
		try {
		String query = "select * from answers_" + tid;
		
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		EvaluateBean b = null;
		String erno = null;
		while(rs.next()) {
			erno = rs.getString("a_erno");
			for(int i=0;i<size;i++) {
				b = new EvaluateBean();
				b.setErno(erno);
				b.setAnswer(rs.getString("a_"+qid.get(i)));
				b.setQid(qid.get(i));
				eb.add(b);
			}
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		Collections.sort(eb, new Comparator<EvaluateBean>() {
			  public int compare(EvaluateBean u1, EvaluateBean u2) {
			    return u1.getQid() - u2.getQid();
			  }
			});

		return  eb;
	}
	
	public static ArrayList<Integer> getQuestionIds(int tid){
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String st = "questions_" + tid;
		String query = "select q_id,q_type from "+st;
		ArrayList<Integer> qids = new ArrayList<Integer>();
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt(2) == 1) {
					qids.add(rs.getInt(1));
				}
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return qids;
	}
	
	public static void updateEvaluate(int tid) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		
		String query = "update test set evaluated=? where test_id=?";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, 1);
			ps.setInt(2, tid);
			ps.executeUpdate();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	public static boolean getAttempt(String erno, int tid) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean b = false;
		
		String query = "select attempted from answers_"+tid+" where a_erno=?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, erno);
			rs = ps.executeQuery();
			
			
			if(rs.next()) {
				if(rs.getInt("attempted") == 1) {
					b = false;
				}else {
					b = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return b;
	}
	
	public static ArrayList<Question> getQuestions(int tid){
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Question> ts = new ArrayList<Question>();
		
		String query = "select * from questions_"+tid;
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			
			Question t = null;
			while(rs.next()) {
				t = new Question();
				t.setId(rs.getInt("q_id"));
				t.setType(rs.getInt("q_type"));
				t.setMarks(rs.getInt("q_marks"));
				t.setQuestion(rs.getString("q_question"));
				t.setAnswer(rs.getString("q_answer"));
				t.setCo(rs.getString("q_co"));
				if(rs.getInt("q_type") != 1) {
					t.setA(rs.getString("q_a"));
					t.setB(rs.getString("q_b"));
					t.setC(rs.getString("q_c"));
					t.setD(rs.getString("q_d"));
				}
				ts.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		Collections.shuffle(ts);
		return ts;
	}
	
	public static ArrayList<Test> getFacultyTest(int id) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Test> ts = new ArrayList<Test>();
		
		String query = "select * from test where test_fid=?";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			Test t = null;
			while(rs.next()) {
				t = new Test();
				t.setSubject(rs.getString("test_subject"));
				t.setCategory(rs.getString("test_category"));
				t.setTotalmarks(rs.getInt("test_totalmarks"));;
				t.setDate(Date.valueOf(rs.getString("test_date")));
				//t.setFromtime(new Time(new SimpleDateFormat("HH:MM:SS").parse(rs.getString("test_fromtime")).getTime()));
				//t.setFromtime(new Time(new SimpleDateFormat("HH:MM:SS").parse(rs.getString("test_totime")).getTime()));
				t.setDuration(rs.getInt("test_duration"));
				ts.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return ts;
	}
	
	public static int createTest(Test t) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		
		String query = "insert into test(test_subject, test_duration, test_batch, test_date, test_fromTime, test_toTime, test_fid, test_category, test_totalmarks, test_department, evaluated) values (?,?,?,?,?,?,?,?,?,?,?)";
		int testId = -1;
		try {
			String cat = null;
			if(t.getCategory().equals("1")) {
				cat = "Test";
			}else {
				cat = "Mid Sem";
			}
			ps = con.prepareStatement(query);
			ps.setString(1, t.getSubject());
			ps.setInt(2, t.getDuration());
			ps.setInt(3, t.getBatch());
			ps.setDate(4, t.getDate());
			ps.setTime(5, t.getFromtime());
			ps.setTime(6, t.getTotime());
			ps.setInt(7, t.getFid());
			ps.setString(8, cat);
			ps.setInt(9, t.getTotalmarks());
			ps.setString(10, t.getDepartment());
			ps.setInt(11, 0);
			ps.executeUpdate();
			
			String query3 = "select max(test_id) from test";
			PreparedStatement ps3 = con.prepareStatement(query3);
			ResultSet rs3 = ps3.executeQuery();
			
			if(rs3.next()) {
				testId = rs3.getInt(1);
			}
			
			String st = "questions_" + String.valueOf(testId);
			String query1 = "CREATE TABLE "+ st +" (\r\n" + 
					"  `q_id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `q_type` INT NOT NULL,\r\n" + 
					"  `q_marks` INT NOT NULL,\r\n" + 
					"  `q_question` LONGTEXT NOT NULL,\r\n" +
					"  `q_co` VARCHAR(45) NOT NULL,\r\n" + 
					"  `q_answer` LONGTEXT NOT NULL,\r\n" + 
					"  `q_a` VARCHAR(45) NULL,\r\n" + 
					"  `q_b` VARCHAR(45) NULL,\r\n" + 
					"  `q_c` VARCHAR(45) NULL,\r\n" + 
					"  `q_d` VARCHAR(45) NULL,\r\n" + 
					"  PRIMARY KEY (`q_id`),\r\n" + 
					"  UNIQUE INDEX `q_id_UNIQUE` (`q_id` ASC) VISIBLE);";
			
			ps1 = con.prepareStatement(query1);
			ps1.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return testId;
	}
	
	public static ArrayList<Test> getPendingTests(int batch, String dept){
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Test> ts = new ArrayList<Test>();
		ArrayList<Test> sel = new ArrayList<Test>();
		
		String query = "select * from test where test_batch=? and test_department=?";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, batch);
			ps.setString(2, dept);
			rs = ps.executeQuery();
			
			Test t = null;
			while(rs.next()) {
				t = new Test();
				t.setId(rs.getInt("test_id"));
				t.setBatch(rs.getInt("test_batch"));
				t.setSubject(rs.getString("test_subject"));
				t.setCategory(rs.getString("test_category"));
				t.setTotalmarks(rs.getInt("test_totalmarks"));;
				t.setDate(Date.valueOf(rs.getString("test_date")));
				t.setFromtime(rs.getTime("test_fromtime"));
				t.setTotime(rs.getTime("test_totime"));
				t.setDuration(rs.getInt("test_duration"));
				ts.add(t);
			}
			int size = ts.size();
			if(size != 0) {
				java.util.Date currentDate = new java.util.Date();  
				
				for(int i=0;i<size;i++) {
					java.util.Date utilDate = new java.util.Date(ts.get(i).getDate().getTime());
					Calendar calTo = Calendar.getInstance();
					Calendar cal = Calendar.getInstance();
					calTo.setTime(utilDate);
					cal.setTime(utilDate);
					calTo.add(Calendar.HOUR, ts.get(i).getFromtime().getHours());
					cal.add(Calendar.HOUR, ts.get(i).getTotime().getHours());
					calTo.add(Calendar.MINUTE, ts.get(i).getFromtime().getMinutes());
					cal.add(Calendar.MINUTE, ts.get(i).getTotime().getMinutes());
					java.util.Date update = cal.getTime();
					java.util.Date updateTo = calTo.getTime();
					if(currentDate.compareTo(update) < 0 && currentDate.compareTo(updateTo) > 0) {
				         sel.add(ts.get(i));
				    }
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return sel;
	}
	
	public static ArrayList<Test> getPendingTests(int id, boolean b){
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Test> ts = new ArrayList<Test>();
		ArrayList<Test> sel = new ArrayList<Test>();
		
		String query = "select * from test where test_fid=?";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			Test t = null;
			while(rs.next()) {
				t = new Test();
				t.setId(rs.getInt("test_id"));
				t.setBatch(rs.getInt("test_batch"));
				t.setSubject(rs.getString("test_subject"));
				t.setCategory(rs.getString("test_category"));
				t.setTotalmarks(rs.getInt("test_totalmarks"));;
				t.setDate(Date.valueOf(rs.getString("test_date")));
				t.setFromtime(rs.getTime("test_fromtime"));
				t.setTotime(rs.getTime("test_totime"));
				t.setDuration(rs.getInt("test_duration"));
				t.setEvaluated(rs.getInt("evaluated"));
				ts.add(t);
			}
			int size = ts.size();
			if(size != 0) {
				LocalTime currentTime = LocalTime.now();
				java.util.Date currentDate = new java.util.Date();  
				
				for(int i=0;i<size;i++) {
					java.util.Date utilDate = new java.util.Date(ts.get(i).getDate().getTime());
					Calendar cal = Calendar.getInstance();
					cal.setTime(utilDate);
					cal.add(Calendar.HOUR, ts.get(i).getTotime().getHours());
					cal.add(Calendar.MINUTE, ts.get(i).getTotime().getMinutes());
					java.util.Date update = cal.getTime();
					if(!b) {
						if(currentDate.compareTo(update) < 0) {
					         sel.add(ts.get(i));
					    }
					}else {
						if(currentDate.compareTo(update) > 0) {
					         sel.add(ts.get(i));
					    }
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return sel;
	}
	
	public static void facultyDeleteTest(int testId) {
		Connection con = DB_Connection.getConnection();
		PreparedStatement ps = null;
		
		String query = "drop table questions_" + testId + ", " + "answers_" + testId + ";";
		String query1 = "delete from test where test_id=?";
		try {
			con.prepareStatement(query).executeUpdate();
			ps = con.prepareStatement(query1);
			ps.setInt(1, testId);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
            try {   
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
}
