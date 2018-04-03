package IBM;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

public class SpeechToTextIBM {

	public CredentialsLoader credentials;
	
	public SpeechToTextIBM () throws IOException {
		credentials = new CredentialsLoader();
	}
	
	public String speechToTextFromFile(String fileName, String languaje , boolean debug ) throws LineUnavailableException, UnsupportedAudioFileException, IOException {

		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword(credentials.getSpeechToTextUser(), credentials.getSpeechToTextPassword());

		File audio = new File(fileName);

		System.out.println("IBM file:"+  fileName);
		
		RecognizeOptions options = new RecognizeOptions.Builder()
		  .audio(audio).model(languaje)
		  .contentType(HttpMediaType.AUDIO_WAV)
		  .build();

		if (debug) { 
			return "texto demo ibm";
		}
		
		SpeechRecognitionResults transcript = service.recognize(options).execute();
		
		String message = "";

		List<com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResult> transcrips  =   transcript.getResults();

		for(com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResult item : transcrips ){
			message = message + " " + item.getAlternatives().get(0).getTranscript() ;
		}

		return message.trim();

	}
}
