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
				System.out.println("2. 나의 루틴 생성하기");
				System.out.println("3. 오늘의 기록");
				System.out.println("4. 루틴 기록 수정");
				System.out.println("5. 한 달 기록 보기");
				System.out.println("0. 회원메뉴로 돌아가기");
				System.out.println();
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1 : selectAllRt(); break;
				case 2 : insertRt(); break;
				case 3 : recordRt(); break;
				case 4 : updateRtRecord(); break;
				case 5 : monthlyRt(); break;
				case 0 : System.out.println("\n* 메인 메뉴로 이동합니다 *\n"); break;
				default : System.out.println("\n** 메뉴에 있는 번호만 입력해주세요\n");
				}
				System.out.println();
			} catch(InputMismatchException e) {
				System.out.println("\n※ 입력 형식이 올바르지 않습니다 ※\n");
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
				int input = 0;
				
				do {
					System.out.println("\n* " + MainView.loginStudent.getStudentName() + "님의 루틴 목록 *\n");
					System.out.println("순번 | 루틴no | 루틴이름 ");
					System.out.println("--------------------------");
					for(int i=0; i<routineList.size() ; i++) {
						System.out.print(i+1 + "번      ");
						System.out.print(routineList.get(i).getRoutineNo() + "      ");
						System.out.println(routineList.get(i).getRoutineName()); }
					
					System.out.println();
					input = subRtMenu(routineList);
					
				}while(input != 0);
				
			} else {
				System.out.println("\n** 조회된 루틴이 없습니다.\n");				
			}
		} catch(Exception e) {
			System.out.println("\n※ 나의 루틴 조회 중 예외 발생 ※\n");
			e.printStackTrace();
		}
	}
	
	/** 루틴 서브메뉴
	 * @param routineList
	 */
	private int subRtMenu(List<Routine> routineList) {
		int input = 0;
		
		try {
			System.out.println("1) 루틴 추가");
			System.out.println("2) 루틴 수정");
			System.out.println("3) 루틴 삭제");
			System.out.println("0) 메뉴로 돌아가기");
			
			System.out.print("\n번호 선택 : ");
			input = sc.nextInt();
			sc.nextLine();
			
			switch(input) {
			case 1 : insertRt();  break;
			case 2 : updateRt(routineList);  break;
			case 3 : deleteRt(routineList);  break;
			case 0 : System.out.println("\n* 루틴 메뉴로 돌아갑니다 *\n");break;
			default : System.out.println("\n** 메뉴에 작성된 번호만 입력 해주세요.\n");
			}
			
		} catch (InputMismatchException e) {
			System.out.println("\n※ 입력 형식이 올바르지 않습니다 ※\n");
			sc.nextLine();
		}
		return input;
	}


	/** 1-1) 루틴 추가
	 * @param studentNo
	 */
	private void insertRt() {
		try {
			System.out.println("\n[나의 루틴 생성하기]\n");
			
			System.out.print("\n루틴 이름 : ");
			String routineName = sc.next();
			
			int result = service.insertRt(routineName, MainView.loginStudent.getStudentNo());
			
			if(result >0) {
				System.out.println("\n* 루틴이 추가되었습니다 *\n");
			} else {
				System.out.println("\n** 루틴 추가 실패\n");					
			}	
			
		}catch(Exception e) {
			System.out.println("\n※ 루틴 생성 중 예외 발생 ※\n");
			e.printStackTrace();
		}
	}


	/** 1-2) 루틴 수정
	 * @param routineList
	 * @param studentNo
	 */
	private void updateRt(List<Routine> routineList) {
		try {
			System.out.println("\n[나의 루틴 수정]\n");
			
			System.out.print("\n수정할 루틴 번호 입력 : ");
			int rtNo = sc.nextInt();
			sc.nextLine();
			
			System.out.print("\n새로운 루틴 입력 : ");
			String rtName = sc.nextLine();
			
			
			boolean flag = true;
			for(Routine rt : routineList) {
				if(rt.getRoutineNo() == rtNo) {
					flag = false;
					int result = service.updateRt(rtNo, rtName);
					
					if(result>0) {
						System.out.println("\n* 루틴 수정 성공 *\n");
						
					} else { 
						System.out.println("\n** 루틴 수정 실패\n");						
					}
					break;
				}
			}
			if(flag) {
				System.out.println("\n** 번호가 일치하는 루틴이 없습니다.\n");
			}
		} catch (Exception e) {
			System.out.println("\n※ 루틴 수정 중 예외 발생 ※\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * 1-3) 루틴 삭제
	 */
	private void deleteRt(List<Routine> routineList) {
		try {
			
			System.out.println("\n[나의 루틴 삭제하기]\n");
			
			System.out.print("\n삭제할 루틴(번호 입력) : ");
			int rtNo = sc.nextInt();
			
			boolean flag = true;
			for(Routine rt : routineList) {
				if(rt.getRoutineNo() == rtNo) {
					flag = false;
					int result = service.deleteRt(rtNo);
					break;
				}
			}
			if(flag) {
				System.out.println("\n** 번호가 일치하는 루틴이 없습니다.\n");
			}
		} catch(Exception e) {
			System.out.println("\n※ 루틴 삭제 중 예외 발생 ※\n");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 3. 하루 루틴 기록
	 */
	private void recordRt() {
		try {
			System.out.println("\n[오늘의 루틴을 기록하세요]\n");
			
			System.out.print("\n기록일(월/일) : ");
			String recordDate = sc.next();
			
			List<Routine> routineList = service.selectAllRt(MainView.loginStudent.getStudentNo());
			
			List<String> oxList = new ArrayList<>();
			
			if(!routineList.isEmpty()) {
				System.out.println("\n* " + MainView.loginStudent.getStudentName() + "님의 루틴 목록 *\n");

				writeRt(routineList, oxList);
				
				int result = service.recordRt(routineList, oxList, recordDate);
				
				if(result > 0) {
					System.out.println("\n* 오늘의 루틴이 기록 완료! *\n");
				} else {
					System.out.println("\n** 루틴 기록 실패\n");					
				}	
			} else {
				System.out.println("\n ** 조회된 루틴이 없습니다.\n");				
			}
		} catch (Exception e) {
			System.out.println("\n※ 나의 루틴 기록 중 예외 발생 ※\n");
			e.printStackTrace();
		} 
	}
	
	/** 루틴 O/X 입력
	 * @param routineList
	 * @param oxList
	 */
	private void writeRt(List<Routine> routineList, List<String> oxList) {
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
					System.out.println("\n** O / X 중에 입력하세요.\n");
					System.out.print(routineList.get(i).getRoutineName());
					System.out.println();
				}						
			}
		}
	}
	
	/**
	 * 4. 루틴 기록 수정
	 */
	private void updateRtRecord() {
		try {
			System.out.println("\n[루틴 기록 수정]\n");
			
			System.out.print("\n수정할 날짜(월/일) : ");
			String recordDate = sc.next();
			
			List<Routine> routineList = service.selectAllRt(MainView.loginStudent.getStudentNo());
			
			List<String> oxList = new ArrayList<>();
			
			if(!routineList.isEmpty()) {
				System.out.println("\n* " + MainView.loginStudent.getStudentName() + "님의 루틴 목록 *\n");
	
				writeRt(routineList, oxList);
				
				int result = service.updateRtRecord(routineList, oxList, recordDate);
				
				if(result > 0) {
					System.out.println("\n* 루틴 수정 완료! *\n");
				} else {
					System.out.println("\n** 루틴 수정 실패\n");					
				}	
			} else {
				System.out.println("\n ** 조회된 루틴이 없습니다.\n");				
			}
		} catch (Exception e) {
			System.out.println("\n※ 나의 루틴 수정 중 예외 발생 ※\n");
			e.printStackTrace();
		}
	} 

	
	/**
	 * 5. 한 달 기록 보기
	 */
	private void monthlyRt() {
		try {
			System.out.println("\n[한 달 기록]\n");
			System.out.print("조회할 달 : ");
			int month = sc.nextInt();
			
			List<Map<String, String>> monthlyRt = service.monthlyRt(month, MainView.loginStudent.getStudentNo());
			
			if(monthlyRt.isEmpty()) {
				System.out.println("\n** 조회된 루틴이 없습니다.\n");
			} else {
				for(String key : monthlyRt.get(0).keySet()) {  // key만 제목으로 출력
	               System.out.print(key + "  ");
	            }
	            System.out.println();
	            
	            for(int i=0; i< monthlyRt.size(); i++) {
	               LinkedHashMap<String, String> map = new LinkedHashMap<>(monthlyRt.get(i));
	               
	               for(String key : map.keySet()) { // key value만 출력
	                  
	                  String check = map.get(key) == null ? " " : map.get(key);
	                  
	                  System.out.print(check  + "   ");
	               }
	               System.out.println();
	            }
			}
			
		} catch (Exception e) {
			System.out.println("\n※ 한달 기록 조회 중 예외 발생 ※\n");
			e.printStackTrace();
		}
	}
}
