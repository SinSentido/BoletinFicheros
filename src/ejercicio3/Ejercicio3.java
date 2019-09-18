package ejercicio3;

import java.io.File;
import java.util.Scanner;

public class Ejercicio3 {
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        String dir;
        File directory;

        System.out.println("Introduce el directorio que quieres mostrar: ");
        dir = scn.nextLine();
        directory = new File(dir);

        if(directory.exists()){
            showFiles(directory);
        }
    }

    private static void showFiles (File directory){
        boolean thereAreDirectories = false;
        File[] files = directory.listFiles();

        for(int i=0; i<files.length; i++){
            if(files[i].isDirectory()){
                thereAreDirectories = true;
            }
        }

        if(!thereAreDirectories){
            for(int i=0; i<files.length; i++){
                System.out.printf("Nombre: %s%n" +
                        "Fichero: %b%n" +
                        "Directorio: %b%n%n", files[i].getName(), files[i].isFile(), files[i].isDirectory());
            }
        }
        else{
            for(int i=0; i<files.length; i++){
                System.out.printf("Nombre: %s%n" +
                        "Fichero: %b%n" +
                        "Directorio: %b%n%n", files[i].getName(), files[i].isFile(), files[i].isDirectory());

                if(files[i].isDirectory()){
                    showFiles(files[i]);
                }
            }
        }
    }
}
