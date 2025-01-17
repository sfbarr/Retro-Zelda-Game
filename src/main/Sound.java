package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip, clip2, clip3;


    // Might have to make two clips to handle multiple sounds


    URL[] soundURL = new URL[40];

    public Sound(){

        soundURL[0] = getClass().getResource("/sound/8bitTheme.wav");
        soundURL[1] = getClass().getResource("/sound/doorOpen.wav");
        soundURL[2] = getClass().getResource("/sound/unlock.wav");
        soundURL[3] = getClass().getResource("/sound/pickUpKey.wav");
        soundURL[4] = getClass().getResource("/sound/pickUpItem.wav");
        soundURL[5] = getClass().getResource("/sound/pickUpHeart.wav");
        soundURL[6] = getClass().getResource("/sound/powerUp.wav");
        soundURL[7] = getClass().getResource("/sound/marioCoin.wav");
        soundURL[8] = getClass().getResource("/sound/hardAttack.wav");
        soundURL[9] = getClass().getResource("/sound/fanfare.wav");
        soundURL[10] = getClass().getResource("/sound/OOT_Fanfare.wav");
        soundURL[11] = getClass().getResource("/sound/portal.wav");
        soundURL[12] = getClass().getResource("/sound/portal.wav");
        soundURL[13] = getClass().getResource("/sound/OOTJump.wav");
        soundURL[14] = getClass().getResource("/sound/jumpSynth.wav");
        soundURL[15] = getClass().getResource("/sound/WW_openTextBox.wav");
        soundURL[16] = getClass().getResource("/sound/WW_boneRattle.wav");
        soundURL[17] = getClass().getResource("/sound/WW_turnPage.wav");
        soundURL[18] = getClass().getResource("/sound/OOT_menuSelect.wav");
        soundURL[19] = getClass().getResource("/sound/OOT_menuSelect2.wav");
        soundURL[20] = getClass().getResource("/sound/OOT_PressStart.wav");
        soundURL[21] = getClass().getResource("/sound/morseBeep.wav");
        soundURL[22] = getClass().getResource("/sound/WW_dialogueBlip.wav");
        soundURL[23] = getClass().getResource("/sound/dialogueGlitch.wav");
        soundURL[24] = getClass().getResource("/sound/dialogueTink.wav");
        soundURL[25] = getClass().getResource("/sound/waterSplash.wav");
        soundURL[26] = getClass().getResource("/sound/MM_Link_Fall.wav");
        soundURL[27] = getClass().getResource("/sound/MM_Slime_Attack.wav");
        soundURL[28] = getClass().getResource("/sound/MM_Slime_Death_Quiet.wav");
        soundURL[29] = getClass().getResource("/sound/swordSlash_Quiet.wav");
        soundURL[30] = getClass().getResource("/sound/swordSlash_Loud.wav");
        soundURL[31] = getClass().getResource("/sound/OOT_DarkLink_Hit1.wav");
        soundURL[32] = getClass().getResource("/sound/OOT_DarkLink_Hit2.wav");

    }

    public void setFile(int i){

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setSE(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip2 = AudioSystem.getClip();
            clip2.open(ais);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        if (!clip.isRunning()) {
            clip.start();
        }
    }

    public void playSE(){
        if (!clip2.isRunning()) {
            clip2.start();
        }
    }
    public void play3() {
        if (!clip3.isRunning()) {
            clip3.start();
        }
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    public void stop(){
        clip.stop();
    }

    public void stop2(){
        clip2.stop();
    }

}
