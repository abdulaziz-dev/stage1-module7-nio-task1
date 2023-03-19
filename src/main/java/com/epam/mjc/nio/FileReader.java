package com.epam.mjc.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        String fullText = "";

        //Reading file content using NIO
        try(RandomAccessFile aFile = new RandomAccessFile(file, "r");
            FileChannel inChannel = aFile.getChannel(); )
        {
            ByteBuffer buffer = ByteBuffer.allocate(512);

            while (inChannel.read(buffer) > 0){
                buffer.flip();
                for (int i=0; i<buffer.limit(); i++){
                    fullText += (char) buffer.get();
                }
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Parsing string
        String[] keyValues = fullText.split(System.lineSeparator());
        for(int i=0; i < keyValues.length; i++){
            if (keyValues[i].split(": ").length > 1){
                keyValues[i] = keyValues[i].split(": ")[1];
            } else {
                keyValues[i] = "";
            }
        }
        String name = keyValues[0];
        Integer age = Integer.parseInt(keyValues[1]);
        String email = keyValues[2];
        Long phone = Long.parseLong(keyValues[3]);

        return new Profile(name, age, email, phone);
    }
}
