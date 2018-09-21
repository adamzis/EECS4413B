package model;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

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

		return returnedPrime;
	}

}
