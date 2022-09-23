package main.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;

import main.model.dao.MainDAO;
import student.vo.Student;

public class MainService {
	
	private MainDAO dao = new MainDAO();

	/** 1.로그인 service
	 * @param loginId
	 * @param loginPw
	 * @return student
	 * @throws Exception
	 */
	public Student login(String loginId, String loginPw) throws Exception {
		Connection conn = getConnection();
		
		Student loginStudent = dao.login(conn, loginId, loginPw);

		close(conn);
		
		return loginStudent;
	}

	/** 2-1. 아이디 중복검사 service
	 * @param stdId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(String stdId) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.idDupCheck(conn, stdId);
		
		close(conn);
		
		return result;
	}

	/** 2-2. 회원가입 service
	 * @param student
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Student student) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.signUp(conn, student);
		
		close(conn);
		
		return result;
	}

	/** 3. 아이디 찾기 service
	 * @param stdName
	 * @param stdPhone
	 * @return stdId
	 * @throws Exception
	 */
	public String findId(String stdName, String stdPhone) throws Exception {
		
		Connection conn = getConnection();
		
		String stdId = dao.findId(conn, stdName, stdPhone);
		
		close(conn);
		
		return stdId;
	}
	
	
	
	
	
}
