package routine.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import main.view.MainView;
import routine.model.service.RoutineService;
import routine.model.vo.Routine;

public class RoutineView {

	private Scanner sc = new Scanner(System.in);
	private RoutineService rtService = new RoutineService();
	private MainView mainView = new MainView();
	
	public void routineMenu() {
		
		int input = -1;
		
		do {
			try {
				System.out.println("<< 나만의 루틴 만들기 >>\n");
				
				System.out.println("1. 나의 루틴 목록");
				System.out.println("2. 오늘의 기록");
				System.out.println("3. 나의 루틴 추가하기");
				System.out.println("4. 한 달 달성 현황보기");
				System.out.println("0. 회원메뉴로 돌아가기");
				System.out.println();
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1 : selectAllRt(); break;
				case 2 :  break;
				case 3 :  break;
				case 4 :  break;
				case 0 : mainView.mainMenu(); break;
				default : System.out.println("\n>>메뉴에 있는 번호만 입력해주세요<<\n");
				}
				System.out.println();
			} catch(InputMismatchException e) {
				System.out.println("\n>>입력 형식이 올바르지 않습니다<<\n");
				sc.nextLine();
			}
		} while(input != 0);
		
	}

	private void selectAllRt() {
		
		List<Routine> routineList = new ArrayList<>();
		
		try {
			routineList = rtService.selectAllRt();
			
			if(routineList.isEmpty()) {
				System.out.println(">>조회된 루틴이 없습니다.<<");
			} else {
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
}
