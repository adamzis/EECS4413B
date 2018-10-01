package rosterXml;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "course")
public class Course {

	private String number;
	private String status;

	private List<Students> students;

	public Course(String number, String status, List<Students> students) {
		super();
		this.number = number;
		this.status = status;
		this.students = students;
	}

	public Course() {

	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Students> getStudents() {
		return students;
	}

	public void setStudents(List<Students> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Course [number=" + number + ", status=" + status + ", students=" + students + "]";
	}

}
