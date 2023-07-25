package todo.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import todo.controller.ToDoController;
import todo.model.vo.ToDo;

public class ToDoView {
	Scanner sc = new Scanner(System.in);
	
	private ToDoController controller;
	
	private ToDo toDoArr[]; // 할일 목록 배열
	private ToDo doneToDoArr[]; // 완료 목록 배열
	private int listIndex; // 할일누적용 인덱스
	private int doneIndex; // 완료 배열 인덱스
	
	private SimpleDateFormat trans;
	private Calendar calendar;
	
	public ToDoView() {
		controller = new ToDoController();
		
		toDoArr = new ToDo[100];
		doneToDoArr = new ToDo[100];
		listIndex = 0;
		doneIndex = 0;
		trans = new SimpleDateFormat("yyyy/MM/dd");
		calendar = Calendar.getInstance();
	}
	
	public void startProgram() {
		List<ToDo> tdList = null;
		ToDo toDo = null;
		int result = 0;
		
		finish:
			while(true) {
				int choice = printMenu();
				
				switch(choice) {
					case 1:
						inputToDoTitle();
						toDo = inputToDo();
						result = controller.insertToDo(toDo);
						break;
					case 2:
						deleteToDoTitle();
						tdList = controller.selectToDo();
						showLeftToDo();
						restToDo(tdList);
						break;
					case 3:
						showLeftToDo();
						break;
					case 4:
						showDoneToDo();
						break;
					case 5:
						if(endProgram() == true)
							break finish;
						else
							break;
					default:
						System.out.println("잘못 입력하셨습니다. (1 ~ 5 중 선택)");
						System.out.println();
				}
			}
	}

	private int printMenu() {
		System.out.println();
		System.out.println("┌───────── ◈ 할 일 기록하기 ◈ ───────────┐");
		System.out.println("│                                        │");
		System.out.println("│      1. 할 일 추가하기                 │");
		System.out.println("│      2. 완료한 한 일 삭제하기          │");
		System.out.println("│      3. 남은 할 일 목록 환인하기       │");
		System.out.println("│      4. 완료한 할 일 목록 확인하기     │");
		System.out.println("│      5. 종료하기                       │");
		System.out.println("│                                        │");
		System.out.println("└────────────────────────────────────────┘");
		System.out.println();
		System.out.print("▷▶ 메뉴 선택 : ");
		int choicenum = 0;
		try {
			choicenum = sc.nextInt();
		} catch (InputMismatchException e) { // 숫자 외 입력 시 예외처리
			sc.next();
		}
		
		return choicenum;
	}

	public void inputToDoTitle() {
			System.out.println();
			System.out.println("┌────────────────────────────────────────┐");
			System.out.println("│        ◈ 할 일을 추가해 주세요 ◈       │");
			System.out.println("└────────────────────────────────────────┘");
	//		System.out.println("초기화면으로 돌아가려면 * 를 입력해주세요.");
			System.out.println();
		}

	private ToDo inputToDo() {
			ToDo toDo = null;
			
			sc.nextLine();
			while(true) {
				System.out.print("▶ 할 일 : ");
				String list = sc.nextLine();
				
				System.out.print("▷▷ 마감일 (yyyy/MM/dd) : ");
				String due = sc.nextLine();
				
				// 마감일 날짜 형식으로 만들어서 저장하기
				long dueDate = 0;
				try {
					Date date = trans.parse(due);
					calendar.setTime(date);
					dueDate = calendar.getTimeInMillis(); 
					
					long today = System.currentTimeMillis(); // 오늘 날짜
					int dDay = (int)((dueDate - today) / 1000 / 60 / 60 / 24) + 1; // d-day 계산 (일)
					
	//				toDo = new ToDo((listIndex + 1), list, due, dDay);
					toDo = new ToDo(list, due, dDay);			
					System.out.println();
					break;
				} catch (ParseException e) {
					System.out.println("마감일은 yyyy/MM/dd의 형식으로 입력해주세요.");
					System.out.println();
					continue;
				}
			}
			return toDo;
		}


	public void deleteToDoTitle() {
		System.out.println();
		System.out.println("┌────────────────────────────────────────┐");
		System.out.println("│   ◈ 완료한 할 일 번호를 적어주세요 ◈   │");
		System.out.println("└────────────────────────────────────────┘");
		System.out.println();
		
//		// 저장된 남은 할 일 목록 출력
//		restToDo();
//		
//		System.out.println();
//		System.out.println("초기화면으로 돌아가려면 * 를 입력해주세요.");
//		System.out.println();
//		
//		// 완료한 할 일 선택
//		for(int i = 0; i < listIndex; i++) {
//			System.out.print("▶ ");
//			String doneList = sc.next();
//			
//			// 초기화면
//			if(doneList.equals("*")) { 
//				System.out.println();
//				System.out.println("초기화면으로 돌아갑니다.");
//				System.out.println();
//				break;
//			}
//			
//			try {
//				// 선택한 할 일 삭제(초기화)
//				for(int j = 0; j < listIndex; j++) { 
//					if(toDoArr[j].getIndex() == Integer.parseInt(doneList)) {
//						doneToDoArr[doneIndex] = new ToDo(toDoArr[j].getIndex(), toDoArr[j].getList(), toDoArr[j].getDue(), toDoArr[j].getdDay());
//						doneIndex++;
//						toDoArr[j] = new ToDo();
//					}			
//				}
//				
//			} catch (NumberFormatException e) {
//				System.out.println("번호를 입력해주세요.");
//				System.out.println();
//				i--;
//				continue;
//			}
//		}
	}

	public void showLeftToDo() {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("┌────────────────────────────────────────┐");
		System.out.println("│           ◈ 남은 할 일 목록 ◈          │");
		System.out.println("└────────────────────────────────────────┘");
		System.out.println();
	}

	public void showDoneToDo() {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("┌────────────────────────────────────────┐");
		System.out.println("│          ◈ 완료한 할 일 목록 ◈         │");
		System.out.println("└────────────────────────────────────────┘");
		System.out.println();

		for(int i = 0; i < doneIndex; i++) {
			System.out.printf("▶ %d. %s%15s\tD-%d\n", doneToDoArr[i].getIndex(),doneToDoArr[i].getList(),doneToDoArr[i].getDue(),doneToDoArr[i].getdDay());
		}
		System.out.println();
	}

	public boolean endProgram() {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("┌────────────────────────────────────────┐");
		System.out.println("│              ◈ 종료하기 ◈              │");
		System.out.println("└────────────────────────────────────────┘");
		while(true) {
			System.out.println();
			System.out.println("종료 시 목록이 초기화됩니다. 종료하시겠습니까? (Y/N)\n");
			System.out.print("▷▶ 확인 : ");
			char endChoice = sc.next().charAt(0);
			
			if(endChoice == 'Y') {
				System.out.println();
				System.out.println("작동을 종료합니다.");
				System.out.println();
				return true;
			} else if(endChoice == 'N') {
				System.out.println();
				System.out.println("메뉴 선택 화면으로 돌아갑니다.");
				System.out.println();
				return false;
			} else {
				System.out.println();
				System.out.println("잘못입력하셨습니다. 다시 입력해주세요.");
			}			
		}
		
	}
	
	// 남은 할 일 목록
	public void restToDo(List<ToDo> tdList) {
		System.out.println("할 일 목록 : ");
		System.out.println("번호. 목록     마감일      D-day     등록일");
		for(ToDo toDo : tdList) { 
			System.out.printf("%d. %-15s%10s\tD-%d\t%tY/%tm/%td\n"
									, toDo.getIndex()   // 번호
									, toDo.getList()    // 목록
									, toDo.getDue()     // 마감일 
									, toDo.getdDay()    // D-day
									, toDo.getToday()   // year
									, toDo.getToday()   // month
									, toDo.getToday()); // date
		}
		System.out.println();
	}
}
