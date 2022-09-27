package routine.model.vo;

import java.util.List;

public class Routine {

	private int routineNo;
	private String routineName;
	private int studentNo;
	private String secessionFlag;

	private List<Routine> routineList;

	public Routine() {}
	
	
	public String getSecessionFlag() {
		return secessionFlag;
	}

	public void setSecessionFlag(String secessionFlag) {
		this.secessionFlag = secessionFlag;
	}
	
	public List<Routine> getRoutineList() {
		return routineList;
	}

	public void setRoutineList(List<Routine> routineList) {
		this.routineList = routineList;
	}

	public int getRoutineNo() {
		return routineNo;
	}

	public void setRoutineNo(int routineNo) {
		this.routineNo = routineNo;
	}

	public String getRoutineName() {
		return routineName;
	}

	public void setRoutineName(String routineName) {
		this.routineName = routineName;
	}

	public int getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(int studentNo) {
		this.studentNo = studentNo;
	}
	
	
}
