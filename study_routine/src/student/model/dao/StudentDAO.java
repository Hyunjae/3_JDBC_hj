package student.model.dao;

import static common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import student.vo.Student;

public class StudentDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public StudentDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("student-query.xml"));			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/** 회원정보 업데이트 DAO
	 * @param conn
	 * @param studentNo
	 * @param studentName
	 * @param studentPhone
	 * @return result
	 * @throws Exception
	 */
	public int updateStudent(Connection conn, Student student) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateStudent");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.getStudentName());
			pstmt.setString(2, student.getStudentPhone());
			pstmt.setInt(3, student.getStudentNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 비밀번호 수정 DAO
	 * @param conn
	 * @param studentPw
	 * @param newPw2
	 * @param studentNo
	 * @return result
	 * @throws Exception
	 */
	public int updatePw(Connection conn, String studentPw, String newPw2, int studentNo) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updatePw");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPw2);
			pstmt.setInt(2, studentNo);
			pstmt.setString(3, studentPw);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 회원 탈퇴 DAO
	 * @param conn
	 * @param studentNo
	 * @param studentPw
	 * @return result
	 * @throws Exception
	 */
	public int secession(Connection conn, int studentNo, String studentPw) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("secession");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, studentNo);
			pstmt.setString(2, studentPw);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


}
