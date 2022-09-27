package student.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;

import student.model.dao.StudentDAO;
import student.vo.Student;

public class StudentService {

	private StudentDAO dao = new StudentDAO();

	/** 회원정보 업데이트 service
	 * @param studentNo
	 * @param studentName
	 * @param studentPhone
	 * @return result
	 * @throws Exception
	 */
	public int updateStudent(Student student) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.updateStudent(conn, student);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 비밀번호 수정 service
	 * @param studentPw
	 * @param newPw2
	 * @param studentNo
	 * @return result
	 * @throws Exception
	 */
	public int updatePw(String studentPw, String newPw2, int studentNo) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updatePw(conn, studentPw, newPw2, studentNo);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 회원 탈퇴 service
	 * @param studentNo
	 * @param studentPw
	 * @return result
	 * @throws Exception
	 */
	public int secession(int studentNo, String studentPw) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.secession(conn, studentNo, studentPw);

		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	
}
