package Google;

//Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.spi.AudioFileReader;

public class SpeechToTextGoogle {

	/**
	 * Demonstrates using the Speech API to transcribe an audio file.
	 */
	public String speechToTextFromFile(String fileName, String languaje) throws Exception {

		Boolean error = false;
		String errorMessage = "[ERROR] - ";

		if (languaje == "") {
			error = true;
			errorMessage = errorMessage + "No hay idioma definido. ";
		}

		if (fileName == "") {
			error = true;
			errorMessage = errorMessage + "No hay archivo definido. ";

		}

		if (error == true) {
			return errorMessage;
		}

		String transcription = null;

		// Instantiates a client
		try (SpeechClient speechClient = SpeechClient.create()) {
			// Reads the audio file into memory
			Path path = Paths.get(fileName);

			byte[] data = Files.readAllBytes(path);
			ByteString audioBytes = ByteString.copyFrom(data);

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName));

			// Builds the sync recognize request
			RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(AudioEncoding.LINEAR16)
					.setSampleRateHertz((int) audioInputStream.getFormat().getSampleRate()).setLanguageCode(languaje)
					.build();
			RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

			// for debug.
			if (true) {
				return "texto demo google";
			}

			// Performs speech recognition on the audio file
			RecognizeResponse response = speechClient.recognize(config, audio);
			List<SpeechRecognitionResult> results = response.getResultsList();

			for (SpeechRecognitionResult result : results) {
				// There can be several alternative transcripts for a given chunk of speech.
				// Just use the
				// first (most likely) one here.
				SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
				// System.out.printf("Transcription: %s%n", alternative.getTranscript());
				transcription = alternative.getTranscript();
			}
		}

		return transcription;

	}
}