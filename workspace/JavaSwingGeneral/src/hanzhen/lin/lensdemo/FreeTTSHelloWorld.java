package hanzhen.lin.lensdemo;

/**
 * Copyright 2003 Sun Microsystems, Inc.
 * 
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL 
 * WARRANTIES.
 */
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.JavaClipAudioPlayer;

/**
 * Simple program to demonstrate the use of the FreeTTS speech
 * synthesizer.  This simple program shows how to use FreeTTS
 * without requiring the Java Speech API (JSAPI).
 */
public class FreeTTSHelloWorld {

    /**
     * Example of how to list all the known voices.
     */

    public static void main(String[] args) {

        String voiceName ="kevin16";
        

        /* The VoiceManager manages all the voices for FreeTTS.
         */
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice helloVoice = voiceManager.getVoice(voiceName);

        if (helloVoice == null) {
            System.err.println(
                "Cannot find a voice named "
                + voiceName + ".  Please specify a different voice.");
            //System.exit(1);
        }
        
        /* Allocates the resources for the voice.
         */
        helloVoice.allocate();
        
        /* Synthesize speech.
         */
        helloVoice.speak("Thank you for giving me a voice. "
                         + "I'm so glad to say hello to this world.");

        /* Clean up and leave.
         */
        helloVoice.deallocate();
        System.exit(0);
    }
}
/*Content left from LensDemoTutorial.java 
 * 
 * 
 	public void dispose(){
	//overwrite the dispose() method, so that resource of TTS engine is released;
		super.dispose();
		if (ttsVoice != null){ttsVoice.deallocate();}//deallocate resource.
	}
	class TTSThread extends Thread{
		String textToBeSpoken;
		TTSThread(String textToBeSpoken){
			this.textToBeSpoken=textToBeSpoken;
		}
		public void run(){
			//ttsVoice.endBatch();
			//ttsVoice.getAudioPlayer().pause();
			//ttsVoice.getAudioPlayer().cancel();
			//ttsVoice.getAudioPlayer().reset();
			
			//ttsVoice.allocate();
			
			ttsVoice.speak(textToBeSpoken);
			
			//ttsVoice.getAudioPlayer().resume();
			//ttsVoice.deallocate();
			
			//ttsVoice.startBatch();
		}
	}
//TTSThread ttsThread=new TTSThread(text[newSceneNum]);
			//ttsThread.start();
			if (ttsVoice != null){
				ttsVoice.allocate();
				ttsVoice.speak(text[newSceneNum]);
				ttsVoice.deallocate();
			}
			//use TTS engine to read the explanatory text.
 * 
 * 
 * 
 * 
*/