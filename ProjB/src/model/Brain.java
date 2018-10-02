package model;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import rosterXml.Course;
import rosterXml.Students;

public class Brain {
	public static final double BITS_PER_DIGIT = 3.0;
	public static final Random RNG = new Random();
	public static final String TCP_SERVER = "red.eecs.yorku.ca";
	public static final int TCP_PORT = 12345;
	public static final String DB_URL = "jdbc:derby://red.eecs.yorku.ca:64413/EECS;user=student;password=secret";
	public static final String HTTP_URL = "https://www.eecs.yorku.ca/~roumani/servers/4413/f18/World.cgi";
	public static final String ROSTER_URL = "https://www.eecs.yorku.ca/~roumani/servers/4413/f18/Roster.cgi";

	public String doTime() {
		ZonedDateTime currTime = ZonedDateTime.now();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("E MMM d HH:mm:ss z yyyy");

		String formattedTime = currTime.format(timeFormat);
		return formattedTime;
	}

	public String doPrime(int digits) {
		int bitsToDec = (int) (BITS_PER_DIGIT * digits);

		BigInteger bigPrime = BigInteger.probablePrime(bitsToDec, new Random());
		String prime = bigPrime.toString();

		return prime;
	}

	public String doTCP(int digits) throws UnknownHostException, IOException {
		Socket client = new Socket(TCP_SERVER, TCP_PORT);
		PrintStream clientOut = new PrintStream(client.getOutputStream());
		Scanner clientIn = new Scanner(client.getInputStream());

		clientOut.println("Prime " + digits);
		String returnedPrime = clientIn.nextLine();

		client.close();
		clientIn.close();
		clientOut.close();

		return returnedPrime;
	}

	public String doDb(String itemNo) throws Exception {
		Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
		Connection con = DriverManager.getConnection(DB_URL);
		Statement s = con.createStatement();
		s.executeUpdate("set schema roumani");

		String query = "SELECT name, price FROM item WHERE number = ?";
		PreparedStatement preS = con.prepareStatement(query);
		preS.setString(1, itemNo);

		ResultSet r = preS.executeQuery();
		String result = "";
		if (r.next()) {
			result = "$" + r.getDouble("PRICE") + " - " + r.getString("NAME");
		} else {
			throw new Exception(itemNo + " not found!");
		}

		r.close();
		s.close();
		preS.close();
		con.close();

		return result;
	}

	public String doHttp(String country, String query) throws IOException {

		StringBuilder queryURLBuild = new StringBuilder(HTTP_URL);
		queryURLBuild.append("?");
		queryURLBuild.append("country=");
		queryURLBuild.append(country);
		queryURLBuild.append("&");
		queryURLBuild.append("query=");
		queryURLBuild.append(query);

		String queryURL = queryURLBuild.toString();

		URL httpURL = new URL(queryURL);
		URLConnection httpConnection = httpURL.openConnection();

		Scanner httpInput = new Scanner(httpConnection.getInputStream());
		String result = httpInput.nextLine();

		httpInput.close();

		return result;
	}

	public String doRoster(String course) throws IOException {

		StringBuilder rosterURLBuild = new StringBuilder(ROSTER_URL);
		rosterURLBuild.append("?");
		rosterURLBuild.append("course=");
		rosterURLBuild.append(course);

		String rosterURLString = rosterURLBuild.toString();

		URL rosterURL = new URL(rosterURLString);
		URLConnection rosterConnection = rosterURL.openConnection();

		Scanner rosterInput = new Scanner(rosterConnection.getInputStream());
		String xml = rosterInput.nextLine();

		System.out.println(xml);

		Course roster = null;
		try {
			roster = XMLtoHTML(xml);
		} catch (JAXBException e) {
			return "XML Conversion Error";
		} finally {
			rosterInput.close();
		}

		return makeRosterTable(roster);
	}

	private Course XMLtoHTML(String xml) throws JAXBException {

		JAXBContext jc = JAXBContext.newInstance(Course.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();

		StringReader reader = new StringReader(xml);

		Course theCourse = (Course) unmarshaller.unmarshal(reader);

		return theCourse;

	}

	private String makeRosterTable(Course roster) {
		StringBuilder htmlTableBuild = new StringBuilder("<table border=\"1\">");
		htmlTableBuild.append("<tr><th>Course ID</th><td colspan=\"3\">" + roster.getNumber() + "</td>");
		htmlTableBuild.append("<th>Status</th><td colspan=\"2\">" + roster.getStatus() + "</td></tr>");

		List<Students> classStudents = roster.getStudents();

		htmlTableBuild.append("<tr><th>ID</th>" + "<th>Last Name</th>" + "<th>First Name</th>" + "<th>City</th>"
				+ "<th>Program</th>" + "<th>Hours</th>" + "<th>GPA</th></tr>");

		for (Students student : classStudents) {
			htmlTableBuild.append("<tr><td>" + student.getId() + "</td>");
			htmlTableBuild.append("<td>" + student.getLastName() + "</td>");
			htmlTableBuild.append("<td>" + student.getFirstName() + "</td>");
			htmlTableBuild.append("<td>" + student.getCity() + "</td>");
			htmlTableBuild.append("<td>" + student.getProgram() + "</td>");
			htmlTableBuild.append("<td>" + student.getHours() + "</td>");
			htmlTableBuild.append("<td>" + student.getGpa() + "</td></tr>");
		}

		htmlTableBuild.append("</table>");
		return htmlTableBuild.toString();
	}

}
