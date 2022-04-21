package clashRoyale;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ClashRoyale {
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		// TODO Auto-generated method stub
		//GameFrame frame = new GameFrame();
		/*File file = new File("daft-punk-around-the-world-official-audio.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream); 
		clip.start();*/
		
		new GameFrame();

	}
}
