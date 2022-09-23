package main.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.model.service.MainService;
import student.vo.Student;

public class MainView {

	private Scanner sc = new Scanner(System.in);
	
	private MainService service = new MainService();
	
	public static Student loginStudent;
	
	public void mainMenu() {
		int input = -1;		
		
		do {
			try {
				if(loginStudent == null) {
					System.out.println("<< 메인 메뉴 >>\n");
					
					System.out.println("1. 로그인");
					System.out.println("2. 회원가입");
					System.out.println("3. 아이디 찾기");
					System.out.println("0. 프로그램 종료");
					System.out.println();
					
					System.out.print("메뉴 선택 : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1 : login(); break;
					case 2 : signUp(); break;
					case 3 : findId(); break;
					case 0 : System.out.println("\n[프로그램을 종료합니다.]\n"); break;
					default : System.out.println("\n>>메뉴에 있는 번호만 입력해주세요<<\n");
					}
					System.out.println();
				} else {
					System.out.println("<< 회원 메뉴 >>\n");
					
					System.out.println("1. 나만의 루틴 만들기");
					System.out.println("2. 성적관리");
					System.out.println("3. 회원 정보 수정");
					System.out.println("0. 로그아웃");
					System.out.println("99. 프로그램 종료");
					System.out.println();
					
					System.out.print("메뉴 선택 : ");
					input = sc.nextInt();
					sc.nextLine();
					
					switch(input) {
					case 1 :  break;
					case 2 :  break;
					case 3 :  break;
					case 0 :  break;
					case 99 : System.out.println("\n[프로그램을 종료합니다.]\n"); break;
					default : System.out.println("\n>>메뉴에 있는 번호만 입력해주세요<<\n");
					}
					System.out.println();
					
				}
			} catch(InputMismatchException e) {
			System.out.println("\n>>입력 형식이 올바르지 않습니다<<\n");
			sc.nextLine();
			}
		} while(input != 0);			
	}

	
	/**
	 * 1.로그인
	 */
	private void login() {
		System.out.println("\n[로그인]\n");
		
		try {
			System.out.print("아이디 : ");
			String loginId = sc.next();

			System.out.print("비밀번호 : ");
			String loginPw = sc.next();
			System.out.println();
			
			loginStudent = service.login(loginId, loginPw);
			
			if(loginStudent != null) {
				System.out.println("\n" + loginStudent.getStudentName() + "님 오늘도 열공하세요 :)\n");
			} else {
				System.out.println("\n>>아이디 또는 비밀번호가 일치하지 않습니다<<\n");
			}
			
		} catch(Exception e) {
			System.out.println("\n>>로그인 중 예외 발생<<\n");
		}	
	}
	
	/**
	 * 2. 회원가입
	 */
	private void signUp() {
		System.out.println("\n[회원가입]\n");
		String stdId;
		String stdPw1;
		String stdPw2;
		String stdName;
		String stdGender;
		String stdPhone;
		
		try {
			// 1) 아이디 입력
			while(true) {
				System.out.print("아이디 : ");
				stdId = sc.next();
				int	result = service.idDupCheck(stdId);
				
				if(result == 0) {
					System.out.println("\n[사용 가능한 아이디입니다.]\n");
					break;
				} else {
					System.out.println("\n>>이미 존재하는 아이디입니다. 다른 아이디를 입력해주세요.<<\n");
				}
			}
			
			// 2) 비밀번호 입력
			while(true) {
				System.out.print("비밀번호 : ");
				stdPw1 = sc.next();
				
				System.out.print("비밀번호 확인 : ");
				stdPw2 = sc.next();
				
				if(stdPw1.equals(stdPw2)) {
					System.out.println("\n[비밀번호가 일치합니다.]\n");
					break;
				} else {
					System.out.println("\n>>비밀번호가 일치하지 않습니다. 다시 입력하세요.<<\n");
				}
			}
			
			// 3) 이름 입력
			System.out.print("이름 : ");
			stdName = sc.next();
			
			// 4) 성별 입력 
			while(true) {
				System.out.print("성별(M/F) : ");
				stdGender = sc.next().toUpperCase();
				
				if(stdGender.equals("M") || stdGender.equals("F")) {
					break;
				} else {
					System.out.println("\n>>M 또는 F만 입력해주세요.<<\n");
				}
			}
			// 5) 핸드폰 번호 입력
			System.out.print("핸드폰 번호('-' 포함) : ");
			stdPhone = sc.next();
			
			Student student = new Student(stdId, stdPw2, stdName, stdGender, stdPhone);
			
			int result = service.signUp(student);
			
			if(result > 0) {
				System.out.println("\n[회원 가입이 완료되었습니다.]\n");
			} else {
				System.out.println("\n>>회원 가입 실패<<\n");
			}
			
		} catch(Exception e) {
			System.out.println("\n>>회원가입 중 예외 발생<<\n");
			e.printStackTrace();
		}
	}
	
	private void findId() {
		System.out.println("\n[아이디 찾기]\n");
		
		System.out.print("이름 : ");
		String stdName = sc.next();
		
		System.out.print("핸드폰 번호('-' 포함) : ");
		String stdPhone = sc.next();
		
		try {
			String stdId = service.findId(stdName, stdPhone);
			
			while(true) {
				if(stdId != null) {
					System.out.println("\n회원님의 아이디는 [ " + stdId + " ]입니다.\n");
					break;				
				} else {
					System.out.println("\n>>가입된 정보가 없습니다. 다시 입력하세요.<<\n");		
				}
			}
			
		}catch(Exception e) {
			System.out.println("\n>>아이디 조회 중 예외 발생<<\\n");
			e.printStackTrace();
		}	
	}
}
