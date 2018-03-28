import java.io.File;

import Google.SpeechToTextGoogle;
import IBM.SpeechToTextIBM;

// Imports the Google Cloud client library


public class QuickstartSample {

	public static void main(String... args) throws Exception {
		
		SpeechToTextGoogle speechToTextGoogle = new SpeechToTextGoogle();
		SpeechToTextIBM speechToTextIBM = new SpeechToTextIBM();
		System.out.println("Inicia Procesamiento.");
		
		File folder = new File("resources/");
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        System.out.println("File " + listOfFiles[i].getName());
	        
			System.out.println( speechToTextGoogle.speechToTextFromFile("resources/" + listOfFiles[i].getName(), "es-MX") );

			System.out.println( speechToTextIBM.speechToTextFromFile("resources/" + listOfFiles[i].getName(), "es-MX") );

	        
	      } else if (listOfFiles[i].isDirectory()) {
	        System.out.println("Directory " + listOfFiles[i].getName());
	      }
	    }
		
		//System.out.println( speechToTextGoogle.speechToTextFromFile("resources/hello_world.wav", "es-MX") );
		
	}
	
}