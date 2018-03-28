import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
	        
	        boolean debug = true;
	        
			String GoogleTxt =  speechToTextGoogle.speechToTextFromFile("resources/" + listOfFiles[i].getName(), "es-MX" , debug)  ;
			String IBMTxt = speechToTextIBM.speechToTextFromFile("resources/" + listOfFiles[i].getName(), "es-MX" , debug) ;
		
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("processedFiles/" + listOfFiles[i].getName() + ".google.txt"), "utf-8"))) {
						writer.write(GoogleTxt);
			}	        
			
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("processedFiles/" + listOfFiles[i].getName() + ".ibm.txt"), "utf-8"))) {
						writer.write(IBMTxt);
			}
			
			Path sourcePath      = Paths.get("resources/" + listOfFiles[i].getName());
			Path destinationPath = Paths.get("processedFiles/" + listOfFiles[i].getName());

			try {
			    Files.move(sourcePath, destinationPath,
			            StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
			    //moving file failed.
			    e.printStackTrace();
			}
			
	      } else if (listOfFiles[i].isDirectory()) {
	        System.out.println("Directory " + listOfFiles[i].getName());
	      }
	    }
		System.out.println("Fin Procesamiento.");

	}
	
}