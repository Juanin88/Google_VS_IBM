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
import javazoom.jl.converter.Converter;

// Imports the Google Cloud client library

public class QuickstartSample {

	public static void main(String... args) throws Exception {

		// speechToTextGoogleAndIBM();
		googleSpeechTest();
		ibmSpeechTest();
	}

	public static void speechToTextGoogleAndIBM() throws IOException {
		SpeechToTextGoogle speechToTextGoogle = new SpeechToTextGoogle();
		SpeechToTextIBM speechToTextIBM = new SpeechToTextIBM();
		System.out.println("Inicia Procesamiento.");

		File folder = new File("resources/");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && !listOfFiles[i].isHidden()) {
				System.out.println("File " + listOfFiles[i].getName());

				// new Converter().convert("mp3/" + listOfFiles[i].getName(),
				// "resources/" + listOfFiles[i].getName() + ".wav");

				// Poner como falso para ejecutar la consulta con los servicios de google e ibm.
				boolean debug = false;

				// Google SpeechToText Start
				System.out.println("Inicia Google SpeechToText");

				try {

					String GoogleTxt = speechToTextGoogle.speechToTextFromFile("resources/" + listOfFiles[i].getName(),
							"es-MX", debug);

					try (Writer writer = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream("processedFiles/" + listOfFiles[i].getName() + ".google.txt"),
							"utf-8"))) {
						writer.write(GoogleTxt);
					}

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				System.out.println("Termina Google SpeechToText");

				// IBM SpeechToText Start
				System.out.println("Inicia IBM SpeechToText");

				try {
					String IBMTxt = speechToTextIBM.speechToTextFromFile("resources/" + listOfFiles[i].getName(),
							"es-ES_NarrowbandModel", debug);

					try (Writer writer = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream("processedFiles/" + listOfFiles[i].getName() + ".ibm.txt"),
							"utf-8"))) {
						writer.write(IBMTxt);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				System.out.println("Termina IBM SpeechToText");

				Path sourcePath = Paths.get("resources/" + listOfFiles[i].getName());
				Path destinationPath = Paths.get("processedFiles/" + listOfFiles[i].getName());

				// try {
				// Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
				// } catch (IOException e) {
				// // moving file failed.
				// e.printStackTrace();
				// }

			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		System.out.println("Fin Procesamiento.");

	}

	public static void googleSpeechTest() {

		SpeechToTextGoogle speechToTextGoogle = new SpeechToTextGoogle();

		// Google SpeechToText Start
		System.out.println("Inicia Google SpeechToText");

		try {

			String GoogleTxt = speechToTextGoogle.googleSpeechToTextFromUri(
					"gs://cognitiva_gs/5436922V2.mp3.wav",
					"es-MX",
					false);

			// String GoogleTxt = speechToTextGoogle.speechToTextFromFile(
			// "resources/5436922V2.mp3.wav"
			// , "es-MX", false);

			try (Writer writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("log/google_5436922V2.txt"), "utf-8"))) {
				writer.write(GoogleTxt);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Termina Google SpeechToText");
	}

	public static void ibmSpeechTest() throws IOException {
		SpeechToTextIBM speechToTextIBM = new SpeechToTextIBM();

		// IBM SpeechToText Start
		System.out.println("Inicia IBM SpeechToText");

		try {
			String IBMTxt = speechToTextIBM.speechToTextFromFile(
					"resources/5436922V2.mp3.wav",
					"es-MX", 
					false);

			try (Writer writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(
							"log/ibm_5436922V2.txt"), "utf-8"))) {
				writer.write(IBMTxt);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Termina IBM SpeechToText");
	}
}