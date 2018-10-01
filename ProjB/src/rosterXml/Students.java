package rosterXml;

public class Students {

	private int id;
	private String lastName;
	private String firstName;
	private String city;
	private String program;
	private int hours;
	private float gpa;

	public Students(int id, String lastName, String firstName, String city, String program, int hours, float gpa) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.city = city;
		this.program = program;
		this.hours = hours;
		this.gpa = gpa;
	}

	public Students() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public float getGpa() {
		return gpa;
	}

	public void setGpa(float gpa) {
		this.gpa = gpa;
	}

	@Override
	public String toString() {
		return "Students [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", city=" + city
				+ ", program=" + program + ", hours=" + hours + ", gpa=" + gpa + "]";
	}

}
