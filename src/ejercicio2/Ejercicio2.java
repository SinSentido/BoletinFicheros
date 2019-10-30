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
        File[] contenidoDirectorio;
        File[] contenidoSubdirectorio;

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

        contenidoDirectorio = directorio.listFiles();

        System.out.printf("Ruta: %s%n" +
                "Nombre: %s%n%n", directorio.getAbsolutePath(), directorio.getName());

        for(int i=0; i<contenidoDirectorio.length; i++){
            System.out.printf("Ruta: %s%n" +
                    "Nombre: %s%n%n", contenidoDirectorio[i].getAbsolutePath(), contenidoDirectorio[i].getName());

            if(contenidoDirectorio[i].isDirectory()){
                contenidoSubdirectorio = contenidoDirectorio[i].listFiles();
                for(int j=0; j<contenidoSubdirectorio.length; j++){
                    System.out.printf("Ruta: %s%n" +
                            "Nombre: %s%n%n", contenidoSubdirectorio[j].getAbsolutePath(),
                            contenidoSubdirectorio[j].getName());
                }
            }
        }
    }
}
