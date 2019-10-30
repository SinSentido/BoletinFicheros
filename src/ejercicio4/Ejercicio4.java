package ejercicio4;

import java.io.File;
import java.util.Scanner;

public class Ejercicio4 {
    public static void main(String[] args){
        Scanner kbd = new Scanner(System.in);
        String directorio, extension;
        String[] ficheros;

        System.out.println("Indica el directorio donde quieres buscar:");
        directorio = kbd.nextLine();

        System.out.println("Indica la extensión del archivo que quieres buscar:");
        extension = kbd.nextLine();

        File dir = new File(directorio);
        ficheros = dir.list();

        for(int i=0; i<ficheros.length; i++){
            if(ficheros[i].matches(".*[.]" + extension)){
                System.out.println(ficheros[i]);
            }
        }
    }
}
