package routine.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.view.MainView;
import routine.model.service.RoutineService;
import routine.model.vo.Routine;

public class RoutineView {

	private Scanner sc = new Scanner(System.in);
	private RoutineService service = new RoutineService();
	
	public void routineMenu() {
		
		int input = -1;
		
		do {
			try {
				System.out.println("\n<< 나만의 루틴 만들기 >>\n");
				
				System.out.println("1. 나의 루틴 목록");
				System.out.println("2. 오늘의 기록");
				System.out.println("3. 나의 루틴 추가하기");
				System.out.println("4. 한 달 기록 보기");
				System.out.println("0. 회원메뉴로 돌아가기");
				System.out.println();
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1 : selectAllRt(); break;
				case 2 : recordRt(); break;
				case 3 : updateRt(); break;
				case 4 : monthlyRt(); break;
				case 0 : System.out.println("\n[메인 메뉴로 이동합니다.]\n"); break;
				default : System.out.println("\n>>메뉴에 있는 번호만 입력해주세요<<\n");
				}
				System.out.println();
			} catch(InputMismatchException e) {
				System.out.println("\n>>입력 형식이 올바르지 않습니다<<\n");
				sc.nextLine();
			}
		} while(input != 0);
		
	}

	/**
	 * 1. 나의 루틴 조회
	 */
	private void selectAllRt() {		
		try {
			System.out.println("\n[나의 루틴 LIST]\n");
			
			List<Routine> routineList = service.selectAllRt(MainView.loginStudent.getStudentNo());
			
			if(!routineList.isEmpty()) {
				System.out.println("\n[" + MainView.loginStudent.getStudentName() + "님의 루틴 목록]\n");
				for(int i=0; i<routineList.size() ; i++) {
					System.out.print(i+1 + "번 ");
					System.out.println(routineList.get(i).getRoutineName());
				}
			} else {
				System.out.println(">>조회된 루틴이 없습니다.<<");				
			}
		} catch(Exception e) {
			System.out.println("\\n>>나의 루틴 조회 중 예외 발생<<\\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * 2. 하루 루틴 기록
	 */
	private void recordRt() {
		try {
			System.out.println("\n[오늘의 루틴을 기록하세요]\n");
			
			System.out.print("기록일(월/일) : ");
			String recordDate = sc.next();
			
			List<Routine> routineList = service.selectAllRt(MainView.loginStudent.getStudentNo());
			
			List<String> oxList = new ArrayList<>();
			
			if(!routineList.isEmpty()) {
				System.out.println("\n[" + MainView.loginStudent.getStudentName() + "님의 루틴 목록]\n");
				for(int i=0; i<routineList.size() ; i++) {
					System.out.print(i+1 + "번 ");
					System.out.print(routineList.get(i).getRoutineName());
									
					while(true) {
						System.out.print("(O/X) : ");
						String input = sc.next().toUpperCase();
						sc.nextLine();

						if(input.equals("O") || input.equals("X")) {
							oxList.add(input);
							break;
						} else { 
							System.out.println("\n>> O / X 중에 입력하세요.<<\n");
							System.out.print(routineList.get(i).getRoutineName());
							System.out.println();
						}						
					}
				}
				
				int result = service.recordRt(routineList, oxList, recordDate);
				
				if(result > 0) {
					System.out.println("\n[오늘의 루틴이 기록되었습니다.]\n");
				} else {
					System.out.println("\n>>루틴 기록 실패<<\n");					
				}	
			} else {
				System.out.println(">>조회된 루틴이 없습니다.<<");				
			}
			
			
		} catch (Exception e) {
			System.out.println("\\n>>나의 루틴 기록 중 예외 발생<<\\n");
			e.printStackTrace();
		} 
	}
	
	/**
	 * 3. 루틴 추가하기
	 */
	private void updateRt() {
		try {
			System.out.println("\n[나의 루틴 추가하기]\n");
			System.out.print("루틴 이름 : ");
			String routineName = sc.next();
			
			int result = service.updateRt(routineName, MainView.loginStudent.getStudentNo());
			
			if(result >0) {
				System.out.println("\n[루틴이 추가되었습니다.]\n");
			} else {
				System.out.println("\n>>루틴 추가 실패<<\n");					
			}	
			
		}catch(Exception e) {
			System.out.println("\\n>>루틴 생성 중 예외 발생<<\\n");
			e.printStackTrace();
		}
	}
	
	
	private void monthlyRt() {
		try {
			System.out.println("\n[나의 루틴 추가하기]\n");
			System.out.print("조회할 달 : ");
			int month = sc.nextInt();
			
			List<Map<String, String>> monthlyRt = service.monthlyRt(month, MainView.loginStudent.getStudentNo());
			
			if(monthlyRt.isEmpty()) {
				System.out.println("\n[조회된 루틴이 없습니다.]\n");
			} else {
				for(int i=0; i< monthlyRt.size(); i++) {
					LinkedHashMap<String, String> map = new LinkedHashMap<>(monthlyRt.get(i));
					
					for(String key : map.keySet()) {
						System.out.println(key + " : " + map.keySet());
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("\\n>>한달 기록 조회 중 예외 발생<<\\n");
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
}
