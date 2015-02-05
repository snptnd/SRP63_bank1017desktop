package edu.pitt.bank;

public class Tester {

	public static void main(String[] args) {
		Security s = new Security();
		s.validateLogin("nmarcus", 8125);
		Customer c = s.validateLogin("nmarcus", 8125);
		System.out.println(c); 

	}

}
