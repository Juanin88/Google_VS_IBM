package Google;

import com.google.cloud.speech.v1.LongRunningRecognizeRequest;
import com.google.cloud.speech.v1.LongRunningRecognizeResponse;
//Imports the Google Cloud client library
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class SpeechToTextGoogle {

	/**
	 * Demonstrates using the Speech API to transcribe an audio file.
	 */
	public String speechToTextFromFile(String fileName, String languaje, boolean debug) throws Exception {
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

		String transcription = "";

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

			RecognitionAudio audio = RecognitionAudio.newBuilder()
					.setContent(audioBytes).build();

			// for debug.
			if (debug) {
				return "texto demo google";
			}

			// Performs speech recognition on the audio file
			LongRunningRecognizeRequest request = LongRunningRecognizeRequest.newBuilder().setConfig(config)
					.setAudio(audio).build();
			LongRunningRecognizeResponse response = speechClient.longRunningRecognizeAsync(request).get();
			
			List<SpeechRecognitionResult> results = response.getResultsList();

			for (SpeechRecognitionResult result : results) {
				// There can be several alternative transcripts for a given chunk of speech.
				// Just use the
				// first (most likely) one here.
				SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);

				transcription = transcription + " " + alternative.getTranscript();
			}
		}

		return transcription;

	}

	public String googleSpeechToTextFromUri(String uri, String languaje, boolean debug) throws IOException, Exception {
		String transcription = "";

		// RecognizeResponse response = speechClient.recognize(config, audio);
		try (SpeechClient speechClient = SpeechClient.create()) {
			RecognitionConfig.AudioEncoding encoding = RecognitionConfig.AudioEncoding.LINEAR16;
			int sampleRateHertz = 8000;
			String languageCode = "es-MX";
			RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(encoding)
					.setSampleRateHertz(sampleRateHertz).setLanguageCode(languageCode).build();
			
			RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(uri).build();

			LongRunningRecognizeRequest request = LongRunningRecognizeRequest.newBuilder().setConfig(config)
					.setAudio(audio).build();
			LongRunningRecognizeResponse response = speechClient.longRunningRecognizeAsync(request).get();

			List<SpeechRecognitionResult> results = response.getResultsList();

			for (SpeechRecognitionResult result : results) {
				// There can be several alternative transcripts for a given chunk of speech.
				// Just use the
				// first (most likely) one here.
				SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);

				transcription = transcription + " " + alternative.getTranscript();
			}
		}

		return transcription;
	}
}