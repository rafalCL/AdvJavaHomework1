package b_Zadania_Domowe.a_Dzien_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main1 {
	public static void main(String[] args) {
		try {
			List<EmployeeDTO> employees = new ArrayList<>();
			File f = new File("b_Zadania_Domowe/a_Dzien_1/earnings.txt");
			Scanner s = new Scanner(f);
			while (s.hasNextLine()) {
				final String line = s.nextLine();
				EmployeeDTO e = getEmployee(line);
				if (e != null) {
					employees.add(e);
				}

			}
			employees.removeIf(p -> p.surname.matches("^Kowalsk[ia]$")); // using Lambda expression: remove boss and his family
			employees.sort(Comparator.comparingDouble((EmployeeDTO p) -> p.earnings).reversed()); // using Lambda expression: sort
			System.out.println("Endangered species ;-) :");
			System.out.println();
			int i = 0;
			for(EmployeeDTO e : employees) {
				System.out.println(e.name + " " + e.surname);
				if(i > 1) {
					break;
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static EmployeeDTO getEmployee(String text) {
		// below \p{L} matcher is used to match Unicode word characters (including Polish: ąęć, etc.)
		Pattern p = Pattern.compile("^(\\p{L}+)," // match surname
						+ "([\\p{L}\\s\\.]+)[;,]" // match name
						+ "([\\d\\s\\.]+) zł;" // match earnings
						+ "([\\p{L}\\s]+)$"); // match contract type
		Matcher m = p.matcher(text);
		EmployeeDTO result = null;
		if (m.matches()) { // all the regex must match for data to be collected
			// set data
			result = new EmployeeDTO();
			result.surname = m.group(1).trim();
			result.name = m.group(2).trim();
			result.earnings = Double.parseDouble(m.group(3));
			result.contractType = m.group(4).trim();
		}

		return result;
	}
}

class EmployeeDTO {
	public String name;
	public String surname;
	public double earnings;
	public String contractType;
}