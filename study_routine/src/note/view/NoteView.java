package note.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import note.model.service.NoteService;
import note.vo.Note;

public class NoteView {
	
	private Scanner sc = new Scanner(System.in);
	
	private NoteService service = new NoteService();

	public void noteMenu() {
		
		int input = -1;
		
		do {
			try {
				System.out.println("\n<<필기 공유>>\n");
				
				System.out.println("1) 목차 조회");
				System.out.println("2) 단원별 모든 필기 조회");
				System.out.println("3) 내용 검색(주제+내용)");
				System.out.println("4) 새로운 필기 작성");
				System.out.println("0) 회원 메뉴로 돌아가기");
				System.out.println();
				
				System.out.print("번호 입력 : ");
				input = sc.nextInt();
				System.out.println();
				switch(input) {
				case 1 : selectIndex(); break;
				case 2 : selectAllNote(); break;
				case 3 : searchNote(); break;
				case 4 : insertNote(); break;
				case 0 : System.out.println("\n* 회원 메뉴로 이동합니다 *"); break;
				default : System.out.println("\n** 메뉴에 작성된 번호만 입력해주세요.");
				}
				System.out.println();
			} catch(InputMismatchException e) {
				System.out.println("\n※ 입력 형식이 올바르지 않습니다 ※");
				sc.nextLine();
			}
		} while(input != 0) ;
	}

	/**
	 * 1. 목차 조회
	 */
	private void selectIndex() {
		try {
			System.out.println("\n[목차]\n");
			//과목 조회(selectSubject)
			List<Note> subjectList = service.selectSubject();
			
			System.out.println("번호 | 과목명 ");
			System.out.println("-----------------");
			for(Note s : subjectList) {
				System.out.printf("%3d  | %s\n", s.getSubjectNo(), s.getSubjectName());				
			}
			//과목 선택
			System.out.print("\n과목 번호 선택 : ");
			int subjectNo = sc.nextInt();

			List<Note> chapterList = service.selectChapter(subjectNo);
			if(!chapterList.isEmpty()) {
				System.out.println("\n[단원 목차]\n");
				System.out.println("번호 | 단원순서 | 단원이름 \n");
				System.out.println("-----------------------------");
				for(Note c : chapterList) {
					System.out.printf("%3d  | %2s 단원  | %s\n", c.getChapterId(), c.getChapterNo(), c.getChapterName());
				}
			} else {
				System.out.println("\n** 메뉴에 있는 번호만 입력해주세요");
			}
		} catch(Exception e) {
			System.out.println("\n※ 목차 조회 중 예외 발생 ※");
			e.printStackTrace();
		}
	}

	/**
	 * 2. 단원별 모든 필기 조회
	 */
	private void selectAllNote() {
		try {
			selectIndex();

			System.out.print("\n단원 번호 선택 : ");
			int chapterId = sc.nextInt();
			
			List<Note> noteList = service.selectAllNote(chapterId);
			

			if(!noteList.isEmpty()) {
				System.out.println("\n 번호 | 주제 : 내용 ");
				System.out.println("-----------------------------");
				for(Note n : noteList) {
					System.out.printf("%4d  | %s : %s\n", n.getNoteNo(), n.getNoteTitle(), n.getNoteContent());
				} 		
			} else { 
				System.out.println("\n** 조회 결과가 없습니다.");
			}
				
		} catch(Exception e) {
			System.out.println("\n※ 단원 조회 중 예외 발생 ※");
			e.printStackTrace();
		}
	}

	/**
	 * 3. 필기 내용 검색(주제+내용)
	 */
	private void searchNote() {
		try {
			System.out.println("\n[필기 검색]\n");

			System.out.println("1) 주제");
			System.out.println("2) 내용");
			System.out.println("3) 주제 + 내용");
			
			System.out.print("\n검색 조건 선택 : ");
			int condition = sc.nextInt();
			sc.nextLine();
			
			if(condition >=1 &&  condition <= 3) {
				System.out.print("\n검색어 입력 : ");
				String query = sc.nextLine();
				
				List<Note> searchList = service.searchNote(condition, query);
				
				if(!searchList.isEmpty()) {
					System.out.println("\n* 검색 결과 *\n");
					for(Note n : searchList) {
						System.out.println("-----------------------------------------------------");
						System.out.printf("[과목] %d번 %s  [단원] %d번 %s\n", 
								n.getSubjectNo(), n.getSubjectName(), n.getChapterId(), n.getChapterName());
						System.out.println();
						System.out.printf("%s : %s", n.getNoteTitle(), n.getNoteContent());
						System.out.println("\n-----------------------------------------------------");
					}
					
					
				} else {
					System.out.println("\n** 검색 결과가 없습니다.");
				}
			} else {
				System.out.println("\n** 1~4번 사이의 숫자를 입력해주세요.");
			}
		} catch(Exception e) {
			System.out.println("\n※ 내용 검색 중 예외 발생 ※");
			e.printStackTrace();
		}
	}

	/**
	 * 4. 필기 작성
	 */
	private void insertNote() {
		try {
			selectIndex();
			
			System.out.print("\n단원 번호 선택 : ");
			int chapterId = sc.nextInt();
			sc.nextLine();
			
			System.out.println("\n[새로운 필기 작성]\n");
			
			System.out.print("주제 작성 : ");
			String noteTitle = sc.nextLine();
			
			System.out.print("\n내용 작성 : ");
			String noteContent = sc.nextLine();
			
			int result = service.insertNote(chapterId, noteTitle, noteContent);
			
			if(result>0) {
				System.out.println("\n* 새로운 필기가 작성되었습니다. *");
				
				List<Note> noteList = service.selectAllNote(chapterId);
				
				System.out.println("\n 번호 | 주제 : 내용 ");
				System.out.println("-----------------------------");
				for(Note n : noteList) {
					System.out.printf("%4d  | %s : %s\n", n.getNoteNo(), n.getNoteTitle(), n.getNoteContent());
				}
				
			} else {
				System.out.println("\n** 새로운 필기 작성 실패...");
			}
		} catch(Exception e) {
			System.out.println("\n※ 필기 작성 중 예외 발생 ※");
			e.printStackTrace();
		}
	}
	
}
