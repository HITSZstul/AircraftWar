package videos;

import java.io.*;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

public class MusicThread extends Thread {


    //音频文件名
    private String filename;
//    private AudioFormat audioFormat;
//    private byte[] samples;
    private static boolean stop = false;
    public boolean isStop(){
        return stop;
    }
    private boolean loop;
//    public void setLoop(boolean loop){
//        this.loop = loop;
//    }
    public MusicThread(String filename,boolean loop) {
        //初始化filename
        this.filename = filename;
        this.loop = loop;
//        reverseMusic();
    }
//
//    public void reverseMusic() {
//        try {
//            //定义一个AudioInputStream用于接收输入的音频数据，使用AudioSystem来获取音频的音频输入流
//            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
//            //用AudioFormat来获取AudioInputStream的格式
//            audioFormat = stream.getFormat();
//            samples = getSamples(stream);
//        } catch (UnsupportedAudioFileException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public byte[] getSamples(AudioInputStream stream) {
//        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
//        byte[] samples = new byte[size];
//        DataInputStream dataInputStream = new DataInputStream(stream);
//        try {
//            dataInputStream.readFully(samples);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return samples;
//    }


//    public void play(InputStream source) {
//            int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
//            byte[] buffer = new byte[size];
//            //源数据行SoureDataLine是可以写入数据的数据行
//            SourceDataLine dataLine = null;
//            //获取受数据行支持的音频格式DataLine.info
//            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
//            try {
//                dataLine = (SourceDataLine) AudioSystem.getLine(info);
//                dataLine.open(audioFormat, size);
//            } catch (LineUnavailableException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
////            System.out.println(stop);
//            dataLine.start();
//            try {
//                int numBytesRead = 0;
//                System.out.println(stop);
//                while (numBytesRead != -1) {
//                    if(stop){
//                        dataLine.stop();
//                        dataLine.flush();
//                        break;
//                    }
//                    //从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
////                    System.out.println(stop);
//                    numBytesRead =
//                            source.read(buffer, 0, buffer.length);
//                    //通过此源数据行将数据写入混频器
//                    if (numBytesRead != -1) {
//                        dataLine.write(buffer, 0, numBytesRead);
//                    }
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//
//            dataLine.drain();
//            dataLine.close();
//
//
//    }

    public void stopping(boolean stop){
        MusicThread.stop = stop;
    }

    public void run(){
        File wavFile = new File(filename);
        try{
            AudioInputStream audioInputStream = AudioSystem .getAudioInputStream(wavFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if(loop){
                while (!stop){
                    clip.loop(1);
                }
                clip.stop();
                clip.close();
            }
            else{
                clip.start();
            }
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void run() {
////        System.out.println(loop);
//        while(loop&&!stop) {
////            System.out.println("b");
//            InputStream stream = new ByteArrayInputStream(samples);
//            play(stream);
//        }
//        if(!stop){
////            System.out.println("c");
//            InputStream stream = new ByteArrayInputStream(samples);
//            play(stream);
//        }
//    }
}


