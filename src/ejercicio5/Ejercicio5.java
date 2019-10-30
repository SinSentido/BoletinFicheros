package ejercicio5;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ejercicio5 {

    /*1. Ask the user for a origin and destiny file
    * 2. If the destiny file is a directory we create a file with the same name in that directory
    *   2.1 Write the content of the original file in the new file line by line
    * 3. If the destiny file is a file we do differente things deppending if the file exists or not and a
    *   boolean option
    *   3.1 If the option is true and the file exists, overwrite the content char by char.
    *   3.2 If the option is true and the file doesn't exists, launch an exception.
    *   3.3 If the option is false and the file exists, overwrite the content with a buffer with size of 20
    *   3.4 If the option is false and the file doens't exists, show error message to the user*/

    public static void main(String[] args){
        //Variables declaration
        Scanner kbd = new Scanner(System.in);
        FileWriter fw = null;
        FileReader fr = null;
        BufferedReader br = null;
        BufferedWriter bw;
        String originPath, destinyPath;
        File originFile, destinyFile;
        Stream<String> st;
        boolean option = false;

        //1. Ask the user for a origin file
        do {
            System.out.println("Write the origin path of the file:");
            originPath = kbd.nextLine();
            originFile = new File(originPath);
            if(!originFile.exists()){
                System.out.println("The file doesn't exist, please, try again:");
            }
        }while(!originFile.exists());

        //Ask the user for the destiny file
        System.out.println("Write the destiny path of the file:");
        destinyPath = kbd.nextLine();
        destinyFile = new File(destinyPath);

        try {
            fr = new FileReader(originFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //2. If the destiny file is a directory we create a file with the same name in that directory
        if(destinyFile.isDirectory()){
            File newFile = new File(destinyFile, destinyFile.getName());
            try {
                newFile.createNewFile();
                fw = new FileWriter(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            PrintWriter pw = new PrintWriter(fw);
            bw = new BufferedWriter(fw);
            br = new BufferedReader(fr);

            br.lines().forEach(l -> {
                try {
                    bw.write(l, 0, l.length());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        //3. If the destiny file is a file we do differente things deppending if the file exists or not and a
        //  boolean option
        else{
            if(option){
                //3.1 If the option is true and the file exists, overwrite the content char by char.
                if(destinyFile.exists()){
                    try {
                        fw = new FileWriter(destinyFile);
                        int i = 0;
                        while((i = fr.read()) != -1){
                            fw.write(i);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //3.2 If the option is true and the file doesn't exists, launch an exception.
                else{
                    throw new IllegalArgumentException("No existe el fichero");
                }
            }
            else{
                //3.3 If the option is false and the file exists, overwrite the content with a buffer with size of 20
                if(destinyFile.exists()){
                    int count;
                    char[] buffer = new char[20];
                    try {
                        fw = new FileWriter(destinyFile);
                        fr = new FileReader(originFile);

                        while((count = fr.read(buffer)) != -1){
                            if(count == buffer.length){
                                fw.write(buffer);
                            }
                            else{
                                fw.write(buffer, 0, count);
                            }

                        }

                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
                //3.4 If the option is false and the file doens't exists, show error message to the user
                else{
                    System.out.println("ERROR");
                    System.out.println("The option is set as false and the file you are trying to write in doesn't exists");
                }
            }

        }
        //Closing readers and writers
        try {
            fr.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        kbd.close();
    }
}
