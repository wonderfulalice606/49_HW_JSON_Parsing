package core;

import org.json.simple.*;
import java.io.*;

public class JSON_Generate {

	static String firstName = "Anastasiia";
	static String lastName = "Kotova";
	static Integer age = 36;

	static String city = "Dnipro";
	static String university = "Prydniprovska State Academy of Civil Engineering and Architecture";
	static String faculty = "Civil Engineering";
	static String profession= "Civil Engineer";

	static String start_date = "09/01/2003";
	static String finish_date = "12/01/2006";

	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		JSONObject json = new JSONObject();
		json.put("firstName", firstName);
		json.put("lastName", lastName);
		json.put("age", age);

		JSONObject education = new JSONObject();
		education.put("city", city);
		education.put("university", university);
		education.put("faculty", faculty);
		education.put("specialty", profession);

		json.put("education", education);

		JSONArray term_of_study = new JSONArray();
		term_of_study.add(start_date);
		term_of_study.add(finish_date);

		json.put("term_of_study", term_of_study);

		FileWriter file = new FileWriter("./test.json");
		file.write(json.toJSONString());
		file.flush();
		file.close();

		System.out.println(json);

	}

}
