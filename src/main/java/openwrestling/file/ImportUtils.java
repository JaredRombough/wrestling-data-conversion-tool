package openwrestling.file;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImportUtils {
    static int hexStringToInt(String hexValueString) {
        return Integer.parseInt(hexValueString, 16);
    }

    static String hexStringToLetter(String hexValueString) {
        //take the characters in two positions, since they combine to make
        //up one hex value that we have to translate
        String letter = "";
        //translate the hex value string to an int value
        int intLetter = hexStringToInt(hexValueString);

        //only keep numbers that translate to an ascii alphabet value
        //otherwise just put a blank in our string
        //this will need to be more complex if we import more than just names
        if (intLetter >= 0 && intLetter <= 499) {
            letter += String.valueOf((char) (intLetter));
        } else {
            letter += " ";
        }

        return letter;
    }

    static String getFileString(File importFolder, String fileName) {
        Path path = Paths.get(importFolder.getPath() + "\\" + fileName + ".dat");
        byte[] data;

        byte[] altData;
String data2;
String data1;

String wrestlerAString;
        String wrestlerBString;
        try {
            data = Files.readAllBytes(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile()), "Cp1252"));
            data2 = reader.lines().collect(Collectors.joining());
            data1 = DatatypeConverter.printHexBinary(data);



            //temp
            Path wrestlerA = Paths.get("test_data\\wrestlerA.dat");
            Path wrestlerB = Paths.get("test_data\\wrestlerB.dat");

            BufferedReader readerA = new BufferedReader(new InputStreamReader(new FileInputStream(wrestlerA.toFile()), "Cp1252"));
            BufferedReader readerB = new BufferedReader(new InputStreamReader(new FileInputStream(wrestlerB.toFile()), "Cp1252"));

            wrestlerAString = readerA.lines().collect(Collectors.joining());
            wrestlerBString = readerB.lines().collect(Collectors.joining());

            altData =  Files.readAllBytes(wrestlerB);
            //



        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        List<Integer> different = new ArrayList<>();
        List<Character> differentA = new ArrayList<>();
        List<Character> differentB = new ArrayList<>();

        for(int i = 0; i < wrestlerAString.length(); i++) {
            if(wrestlerAString.charAt(i) != wrestlerBString.charAt(i)) {
                different.add(i);
                differentA.add(wrestlerAString.charAt(i));
                differentB.add(wrestlerBString.charAt(i));
            }
        }


        return DatatypeConverter.printHexBinary(data);
      //  return DatatypeConverter.printHexBinary(altData);
    }

    static List<List<String>> getHexLines(File importFolder, String fileName, int lineSize) {
        String fileString = getFileString(importFolder, fileName);
        List<List<String>> hexLines = new ArrayList<>();
        List<String> hexLine = new ArrayList<>();
        int counter = 0;

        for (int i = 0; i < fileString.length(); i += 2) {
            String hexValueString = String.valueOf(fileString.charAt(i)) + fileString.charAt(i + 1);
            hexLine.add(hexValueString);
            counter++;
            if (counter == lineSize) {
                hexLines.add(hexLine);
                hexLine = new ArrayList<>();
                counter = 0;
            }
        }
        return hexLines;
    }

    static String hexLineToTextString(List<String> hexLine) {
        List<String> toLetters = hexLine.stream().map(ImportUtils::hexStringToLetter).collect(Collectors.toList());
        return StringUtils.join(toLetters, null);
    }

}
