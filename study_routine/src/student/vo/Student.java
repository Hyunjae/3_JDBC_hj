package student.vo;

public class Student {
	
	private int studentNo;
	private String studentId;
	private String studentPw;
	private String studentName;
	private String studentGender;
	private String enrollDate;
	private String secessionFlag;
	
	public Student() {}

	
	
	public Student(String studentId, String studentPw, String studentName, String studentGender) {
		super();
		this.studentId = studentId;
		this.studentPw = studentPw;
		this.studentName = studentName;
		this.studentGender = studentGender;
	}



	public int getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(int studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentPw() {
		return studentPw;
	}

	public void setStudentPw(String studentPw) {
		this.studentPw = studentPw;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(String studentGender) {
		this.studentGender = studentGender;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public String getSecessionFlag() {
		return secessionFlag;
	}

	public void setSecessionFlag(String secessionFlag) {
		this.secessionFlag = secessionFlag;
	}
	
}
