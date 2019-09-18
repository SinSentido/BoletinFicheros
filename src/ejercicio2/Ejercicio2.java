package ejercicio2;

import java.io.File;
import java.io.IOException;

public class Ejercicio2 {
    public static void main(String[] args){
        File directorio = new File(".", "directorio");
        File fichero1 = new File(directorio, "fichero1.txt");
        File fichero2 = new File(directorio, "fichero2.txt");
        File fichero3 = new File(directorio, "fichero3.txt");
        File subdirectorio = new File(directorio, "subdirectorio");
        File ficheroSubdirectorio = new File(subdirectorio, "ficheroSub.txt");

        directorio.mkdir();
        try {
            fichero1.createNewFile();
            fichero2.createNewFile();
            fichero3.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fichero1.delete();
        fichero2.renameTo(fichero1);

        subdirectorio.mkdir();
        try{
            ficheroSubdirectorio.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
