package IBM;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

public class SpeechToTextIBM {

	public CredentialsLoader credentials;
	
	public SpeechToTextIBM () throws IOException {
		credentials = new CredentialsLoader();
	}
	
	public String speechToTextFromFile(String fileName, String languaje) throws LineUnavailableException, UnsupportedAudioFileException, IOException {

		String language = "es-ES_NarrowbandModel";

		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword(credentials.getSpeechToTextUser(), credentials.getSpeechToTextPassword());

		File audio = new File(fileName);

//		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audio);

		RecognizeOptions options = new RecognizeOptions.Builder()
		  .audio(audio).model(language)
		  .contentType(HttpMediaType.AUDIO_WAV)
		  .build();

		if (true) { 
			return "texto demo ibm";
		}
		
		SpeechRecognitionResults transcript = service.recognize(options).execute();
		
		String message = transcript.getResults().get(0).getAlternatives().get(0).getTranscript();
		
		return message;

	}

	
}
