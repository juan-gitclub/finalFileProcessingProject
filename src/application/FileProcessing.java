package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import static java.util.stream.Collectors.*;
import java.sql.*;


/**
 * 
 * @author jdalb
 * @version 1.0
 * 
 *
 */


public class FileProcessing {
	
	
public static void post() throws SQLException { // this was a test function. The actual insertion happens down bellow
	
	final String var = " ";
	
	try {
		
		Connection con = getConnection();
		PreparedStatement posted = con.prepareStatement("INSERT INTO word (word) VALUES ('"+var+"')");
		posted.execute();
		
	} catch (Exception e) {System.out.println(e);}
	
	finally {

		System.out.println("Insert completed.");
	}
}


	
	
public static Connection getConnection() throws SQLException {
		
	try {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/wordocurrences";
		String username = "root";
		String password = ""; //private
		Class.forName(driver);
		
		Connection conn = DriverManager.getConnection(url, username, password);
	System.out.println("connected");
	return conn;
		
	} catch (Exception e) {System.out.println(e);} 
	
		return null;
	}
	
	
	
	
	public ArrayList<String> readFileAsString(File selectedFile) {

		ArrayList<String> list = new ArrayList<String>();

		try {
			
			Scanner sc = new Scanner(selectedFile);

			while (sc.hasNext()) {

				list.add(sc.next());
			}

			list.replaceAll(String::toLowerCase); // Here I turned all the words from the list to lower cases so they could be sorted properly 

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return list;

	}
	
	/**
	 * 
	 * @param list it takes an Array of strings contained in a list
	 * @param temp1 it serves as a temporary variable to compare a string to the last one in the loop
	 * @param count it counts the amount of times a string has been repeated
	 * @param wordsAnalysis it contains both the words and its counts
	 *
	 * <p> analizeContent function takes an arraylist as a parameter 
	 * and analyzes all the strings within the list with a loop to determine whether 
	 * they are repeated or not.
	 * <p> Each word is added to a new hashmap as the key and the amount
	 * of times the word has been repeated as the value.
	 * 
	 *
	 */

	

	public void analizeContent(ArrayList<String> list) {

		Map<String, Integer> wordsAnalysis = new HashMap<>();

		String temp1 = " ";
		Integer count = 0;

		for (String temp : list) { // This loop would determine whether a word is repeated or not

			temp = temp.replaceAll("[”â€.,:;()?!\" \t\n\r\']+", ""); // Here I replaced all the unwanted characters and cleaned the inserted words

			if (temp1.equals(temp)) {

				count++;

			} else {
				try {
					
					Connection con = getConnection();
					PreparedStatement posted = con.prepareStatement("INSERT INTO word (word) VALUES ('"+temp+"')");
					posted.execute();
					
				} catch (Exception e) {System.out.println(e);}
				
				finally {

					System.out.println("Insert completed.");
				}
			
				wordsAnalysis.put(temp1, count);
				temp1 = temp;
				count = 1;
			}

		}

		Map<String, Integer> sortedMap = wordsAnalysis.entrySet().stream() // Here I sorted all the values per key from higher to lower
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				   .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		 sortedMap.entrySet().forEach(entry->{System.out.println("The word " + entry.getKey() + " was mentioned: " + entry.getValue() + " times.");  }); 
	
		 

	}
}

