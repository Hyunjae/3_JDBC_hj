package edu.kh.jdbc.run;

import java.sql.SQLException;
import java.util.Scanner;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run3 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("번호 : ");
		int num = sc.nextInt();
		sc.nextLine();
		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String content = sc.nextLine();
		
		TestService service = new TestService();
		
		TestVO vo1 = new TestVO(num, title, content);
		
		try {
			
			int result = service.update(vo1);
			
			if(result > 0) {
				System.out.println("수정되었습니다.");
			} else {
				System.out.println("일치하는 번호가 없습니다.");
			}
		} catch(SQLException e) {
			System.out.println("수정 중 예외가 발생했습니다.");
			e.printStackTrace();
		}
		
	}
}
