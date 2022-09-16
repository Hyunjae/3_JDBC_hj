package edu.kh.jdbc.run;

import java.sql.SQLException;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run {
	public static void main(String[] args) {
		
		TestService service = new TestService();
		
		// TB_TEST 테이블에 INSERT 수행
		TestVO vo1 = new TestVO(1, "제목1", "내용1");
		
		// vo1 객체를 만들고 service에 vo1 객체를 보내서 결과 반환 받기
		// TB_TEST 테이블에 INSERT를 수행하는 서비스 메서드를 호출 후 결과 반환 받기
		
		try {
			int result = service.insert(vo1);  // 1 or 0
			
			if(result > 0) {
				System.out.println("insert 성공");
			} else {
				System.out.println("insert 실패");
			}
		} catch (SQLException e) {
			System.out.println("SQL 수행 중 오류 발생");
			
			e.printStackTrace();
		}
		// DB에 INSERT 하는 코드 수행 후 결과 반환 -> 수행 결과 : 변경된 row값을 반환
		
	}
}
