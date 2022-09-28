package student.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.view.MainView;
import student.model.service.StudentService;
import student.vo.Student;

public class StudentView {

	private Scanner sc = new Scanner(System.in);
	
	private StudentService service = new StudentService();
	
	private Student loginStudent = null;
	
	private int input = -1;
	
	public void studentMenu(Student loginStudent) {
		
		this.loginStudent = loginStudent;
		
		do {
			try {
				System.out.println("\n<< 회원 기능 >>\n");
				System.out.println("1. 내 정보 조회");
				System.out.println("2. 내 정보 수정(이름, 핸드폰 번호)");
				System.out.println("3. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)");
				System.out.println("4. 회원 탈퇴");
				System.out.println("0. 메인메뉴로 이동");
				System.out.println();

				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				System.out.println();
				
				switch (input) {
				case 1 : selectMyInfo(); break;
				case 2 : updateStudent(); break;
				case 3 : updatePw(); break;
				case 4 : secession(); break;
				case 0 : System.out.println("\n* 메인 메뉴로 이동합니다 *\n"); break;
				default : System.out.println("\n** 메뉴에 작성된 번호만 입력해주세요.\n");
				}
				
				System.out.println();
				
			} catch(InputMismatchException e) {
				System.out.println("\n※ 입력 형식이 올바르지 않습니다 ※");
				sc.nextLine();
			}
		} while (input != 0);
	}

	/**
	 * 1. 내 정보 조회 
	 */
	private void selectMyInfo() {
		System.out.println("\n[내 정보 조회]\n");
		
		System.out.println("회원 번호   : " + loginStudent.getStudentNo());
		System.out.println("회원 아이디 : " + loginStudent.getStudentId());
		System.out.println("회원 이름   : " + loginStudent.getStudentName());
		System.out.print("회원 성별   : ");
		if(loginStudent.getStudentGender().equals("M")) {
			System.out.println("남");
		} else {
			System.out.println("여");			
		}
		System.out.println("회원 전화번호 : " + loginStudent.getStudentPhone());
		System.out.println("회원 가입일   : " + loginStudent.getEnrollDate());
	}


	/**
	 * 2. 내 정보 수정(이름, 핸드폰 번호)
	 */
	private void updateStudent() {
		try {
			System.out.println("\n[내 정보 수정]\n");
			
			System.out.print("변경할 이름 : ");
			String studentName = sc.next();
			
			System.out.print("변경할 전화번호('-'포함) : ");
			String studentPhone = sc.next();
			
			Student student = new Student();
			student.setStudentNo(loginStudent.getStudentNo());
			student.setStudentName(studentName);
			student.setStudentPhone(studentPhone);
			
			int result = service.updateStudent(student);
			
			if(result > 0) {
				System.out.println("\n* 정보 수정 완료 *\n");
			} else {
				System.out.println("\n** 정보 수정 실패\n");				
			}
					
		} catch (Exception e) {
			System.out.println("\n ※ 정보 수정 중 예외 발생 ※\n");
			e.printStackTrace();
		}		
	}

	/**
	 * 3. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
	 */
	private void updatePw() {
		try {
			System.out.println("\n[비밀번호 변경]\n");
			
			System.out.print("현재 비밀번호 입력 : ");
			String studentPw = sc.next();
			
			String newPw1 = null;
			String newPw2 = null;
			
			while(true) {					
				System.out.print("새 비밀번호 입력 : ");
				newPw1 = sc.next();
				
				System.out.print("새 비밀번호 확인 : ");
				newPw2 = sc.next();
				
				if(newPw1.equals(newPw2)) {
					break;
				} else { 
					System.out.println("\n** 비밀번호가 일치하지 않습니다. 다시 입력하세요.\n");						
				}
			}
			int result = service.updatePw(studentPw, newPw2, loginStudent.getStudentNo());
				
			if(result > 0) {
				System.out.println("\n* 비밀번호가 변경되었습니다 *\n");
			} else {
				System.out.println("\n** 현재 비밀번호가 올바르지 않습니다.\n");
			}
			
		}catch(Exception e) {
			System.out.println("\n※ 비밀번호 변경 중 예외 발생 ※\n");
			e.printStackTrace();
		}
	}

	/**
	 * 4. 회원 탈퇴
	 */
	private void secession() {
		try {
			System.out.println("\n[회원 탈퇴]\n");
			
			System.out.print("비밀번호 입력 : ");
			String studentPw = sc.next();
			
			while(true) {
				
				System.out.print("\n정말 탈퇴하시겠습니까? (Y/N) : ");
				char ch = sc.next().toUpperCase().charAt(0);
				
				if(ch == 'Y') {
					int result = service.secession(loginStudent.getStudentNo(), studentPw);
					
					if(result > 0) {
						System.out.println("\n* 탈퇴 되었습니다... *");
						
						input = 0;
						
						MainView.loginStudent = null;
					} else { 
						System.out.println("\n** 비밀번호가 일치하지 않습니다.");
					}
					break;
					
				} else if(ch == 'N'){
					System.out.println("\n* 취소 되었습니다 *");
					break;
				}else {
					System.out.println("\n** Y/N 중 입력해주세요.\n");
				}
			}
			
		}catch(Exception e) {
			System.out.println("\n※ 회원탈퇴 중 예외 발생 ※");
			e.printStackTrace();
		}
	}
}
