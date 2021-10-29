
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.Collections;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font; 
import javafx.scene.Group;
import javafx.stage.Stage;


public class Text_Processor extends Application implements EventHandler<ActionEvent>
{
	Button button, button2, button3;
	VBox vbox;
	
	  public static void main(String[] args) throws Exception
	  {	  
		  launch (args);
	  }
	  
	  @Override
	  public void start(Stage primaryStage) throws Exception{
	  primaryStage.setTitle("Word Analyzer");
	  
	  Text text = new Text();
	  text.setText("Welcome to The Word Analyzer Wizard");
	  text.setX(50);
	  text.setY(50);
	  text.setFont(Font.font("verdana", FontWeight.BOLD,FontPosture.ITALIC,20));
	  Group root = new Group(text);
	  button = new Button();
	  button.setText("Click First to Fetch The Raven Text");
	  button.setOnAction(this);
	  button2 = new Button();
	  button2.setText("Click Here Next to parse the file");
	  button2.setOnAction(this);
	  button3 = new Button();
	  button3.setText("Click Here Last to display results");
	  button3.setOnAction(this);
	  vbox = new VBox(20, root, button, button2, button3);
	  vbox.setSpacing(50);
	  vbox.setAlignment(Pos.CENTER);
	  
	  Scene scene = new Scene(vbox,500,500);
	  primaryStage.setScene(scene);
	  primaryStage.show();
	  }
	  
	  @Override
	  public void handle(ActionEvent event)
	  {
		  if(event.getSource()==button);
		  {
		  	System.out.println("Fetching Text From Source https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		  	URL url;
		 	try
		 	{
		 		url = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm"); //Create object for URL.
				BufferedReader readr = new BufferedReader(new InputStreamReader(url.openStream())); //Open a reader to stream the file
				BufferedWriter writer = new BufferedWriter(new FileWriter("Test_Download.txt")); //Create file to store the downloaded info.
				String line;
	            while ((line = readr.readLine()) != null) //Read the html source code from the website.
	             {
	                writer.write(line); //Write the html code to a local file.
				 }
				readr.close();
	            writer.close();
		 	}
		 	catch
		 	(IOException  e)
		 	{
	        System.out.println("Error: " + e.getMessage());
	        e.printStackTrace();
		 	}
		  	
		  }
		  
		   if(event.getSource()==button2);
		  {
			  System.out.println("Parsing text of your downloaded file...");
			  try
			  {
				  File input = new File("Test_Download.txt"); //Open the newly written file containing the HTML from The Raven.
				  Document doc = Jsoup.parse(input, "UTF-8"); //Parse the file to eliminate the tags
				  String body = doc.body().text(); //Save the parsed words to a string.
				  BufferedWriter writer = new BufferedWriter(new FileWriter("Test_Download.txt"));//rewrite the file without the tags.
				  writer.write(body);
				  writer.close(); 
			  }
			  catch(IOException e)
			  {
			        System.out.println("Error: " + e.getMessage());
			        e.printStackTrace();
			  }
			  
		  }
		  
		  if(event.getSource()==button3);
		  {
			  try
			  {
				  File file = new File("Test_Download.txt"); //Open the file we have written for a word count analysis.
			        Scanner sc = new Scanner(file); //Create a scanner to read the rewritten text file.
			        int i = 0, indexOfWord = 0, count = 0;
			        List<String> words = new ArrayList<String>(); //Create two arrays to store the words and word count.
			        List<Integer> wordCount = new ArrayList<Integer>();
			        
			        while (sc.hasNext()) //Starting here we read each individual word of the file.
			        {
			         String word = sc.next();
			         word = word.replaceAll("\\p{Punct}", " "); //This removes the punctuation and any non letters.;
			           if(words.contains(word))//Here if the word already exist we just increment a counter for that word.
			           {
			            indexOfWord = words.indexOf(word);
			            count = wordCount.get(indexOfWord);
			            count = count+1;
			            wordCount.add(indexOfWord, count);        
			           }
			           else
			           {
			            words.add(i,word);
			            wordCount.add(i,1);
			            i++;
			           }
			          }sc.close();
			         

			         
			         
			         Integer max = Collections.max(wordCount); //At this point we go through the array and sort it by max occurrences of words
			         System.out.println("max word occurence is:" + max);// and then display them in descending order.
			         System.out.println("|Count|Word");
			   	  	 System.out.println("|-----|-----------------|");
			   	  	 
			   	  int no_of_elements = words.size();
			      
			      while(max != 0)
			      {
			       for(int j = 0; j < no_of_elements; j++)
			       {       	       	
			      	 String word = words.get(j);
			      	 count = wordCount.get(j);
			      	 if(count == max)
			      	 {
			      	 //if(word.isEmpty()){continue;}// I put this in because the program was displaying random blanks for some reason.
			      	 System.out.printf("|%-4d", count);
			      	 System.out.printf(" |" + word + "\n");
			      	 }
					
			       }
			       max--;
			      }
			  }
			  catch(IOException e)
			  {
			        System.out.println("Error: " + e.getMessage());
			        e.printStackTrace();
			  }
		  
		  }
	  }
}


