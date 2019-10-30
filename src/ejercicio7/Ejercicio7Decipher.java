package ejercicio7;

import java.io.*;
import java.util.Scanner;

public class Ejercicio7Decipher {
    public static void main(String[] args){
        Scanner kbd = new Scanner(System.in);
        String password, path;
        File file;
        FileReader fr = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        BufferedReader br = null;

        do{
            System.out.println("Write the path of the file you want to deciphe");
            path = kbd.nextLine();
            file = new File(path);

            if(!file.exists() || !file.isFile()){
                System.out.println("Can't find the file you selected");
            }
        }while(!file.exists());

        System.out.println("Write the password to deciphe the file");
        password = kbd.nextLine();

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        char[] buffer = new char[password.length()];
        char character;
        String ciphedText = "";
        int count = 0;

        try{
            while((count = br.read(buffer)) != -1){
                for(int i=0; i<count; i++){
                    character = (char)(buffer[i]-password.charAt(i));
                    ciphedText += character;
                }
            }
            fw = new FileWriter(file);
            fw.write(ciphedText);

            br.close();
            fr.close();
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
