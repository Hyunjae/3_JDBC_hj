package score.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import main.view.MainView;
import score.model.service.ScoreService;
import score.vo.Score;

public class ScoreView {
	
	private Scanner sc = new Scanner(System.in);
	
	private ScoreService service = new ScoreService();
	
	public void scoreMenu() {
		
		int input = -1;
		
		do {
			try {
				System.out.println("\n<< 성적 관리 >>\n");
				System.out.println("1. 내 성적 입력");
				System.out.println("2. 내 성적 조회");
				System.out.println("0. 회원메뉴로 이동");
				System.out.println();

				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();
				System.out.println();
				
				switch (input) {
				case 1 : updateScore(); break;
				case 2 : selectScore(); break;
				case 0 : System.out.println("\n* 회원 메뉴로 이동합니다 *"); break;
				default : System.out.println("\n** 메뉴에 작성된 번호만 입력해주세요.");
				}
				System.out.println();
			} catch(InputMismatchException e) {
				System.out.println("\n※ 입력 형식이 올바르지 않습니다 ※");
				sc.nextLine();
			}
		} while (input != 0);
		
	}

	private void updateScore() {
		
		try {
			System.out.println("\n[성적 입력]\n");
						
			System.out.print("시험일(월/일) : ");
			String testDate = sc.next();
			System.out.print("\n시험 성적(100점 만점) : ");
			int testScore = sc.nextInt();
			
			Score score = new Score();
			score.setTestDate(testDate);
			score.setTestScore(testScore);
			score.setStudentNo(MainView.loginStudent.getStudentNo());
			
			String stdName = MainView.loginStudent.getStudentName();
			
			int result = service.updateScore(score);
			
			if(result > 0) {
				System.out.println("\n* 성적이 입력되었습니다 *\n");
				if(testScore == 100) {
					System.out.println(stdName + "님 완벽해요! ^0^\n");
				} else if(testScore > 70) {
					System.out.println(stdName + "님 노력하셨군요~ :)\n");
				} else if(testScore > 40) {
					System.out.println(stdName + "님 조금 더 분발하셔야겠어요 ^^;\n");					
				} else {
					System.out.println(stdName + "님 노력이 필요합니다 ㅜ0ㅜ\n");
				}
			} else {
				System.out.println("\n** 성적 입력 실패\n");
			}
			
			
		} catch(Exception e) {
			System.out.println("\n※ 성적 입력 중 예외 발생 ※\n");
			e.printStackTrace();
		}
	}

	private void selectScore() {
		try {
			System.out.println("\n[성적 조회]\n");
			
			List<Score> allScore = service.selectScore(MainView.loginStudent.getStudentNo());
			
			if(!allScore.isEmpty()) {
				System.out.println("  시험일      성적");
				System.out.println("-------------------");
				for(Score s : allScore) {
					System.out.printf("%s :  %d점\n", s.getTestDate(), s.getTestScore());
				}
			} else { 
				System.out.println("\n** 조회 된 성적이 없습니다.\n");
			}
		} catch(Exception e) {
			System.out.println("\n※ 성적 조회 중 예외 발생 ※\n");
			e.printStackTrace();
		}
	}

}
