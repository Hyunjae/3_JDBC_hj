package main.model.dao;

import static common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import student.vo.Student;

public class MainDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public MainDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-query.xml"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 로그인 DAO
	 * @param conn
	 * @param loginId
	 * @param loginPw
	 * @return loginStudent
	 * @throws Exception
	 */
	public Student login(Connection conn, String loginId, String loginPw) throws Exception {
		Student loginStudent = null;
		
		try {
			String sql = prop.getProperty("login");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, loginPw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				loginStudent = new Student();
				
				loginStudent.setStudentNo(rs.getInt("STUDENT_NO"));
				loginStudent.setStudentId(rs.getString("STUDENT_ID"));
				loginStudent.setStudentName(rs.getString("STUDENT_NM"));
				loginStudent.setStudentGender(rs.getString("STUDENT_GENDER"));
				loginStudent.setEnrollDate(rs.getString("ENROLL_DATE"));
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return loginStudent;
	}

	/** 아이디 중복검사 DAO
	 * @param conn
	 * @param stdId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String stdId) throws Exception {
		int result = 0;
		
		String sql = prop.getProperty("idDupCheck");
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, stdId);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			result = rs.getInt(1);
		}
		
		return result;
	}

	/** 회원가입 DAO
	 * @param conn
	 * @param student
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Connection conn, Student student) throws Exception {
		
		int result = 0;
		try {
			String sql = prop.getProperty("signUp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, student.getStudentId());
			pstmt.setString(2, student.getStudentPw());
			pstmt.setString(3, student.getStudentName());
			pstmt.setString(4, student.getStudentGender());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
}
