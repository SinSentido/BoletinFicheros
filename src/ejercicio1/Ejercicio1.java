package ejercicio1;

import java.io.File;
import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String fichero;

        System.out.println("Introduce un fichero del que quieras ver los datos");
        fichero = keyboard.nextLine();

        File f = new File(fichero);

        if(f.exists()){
            System.out.printf("Nombre: %s%n" +
                    "Ejecutable: %b%n" +
                    "Oculto: %b%n" +
                    "Ruta relativa: %s%n" +
                    "Ruta absoluta: %s%n" +
                    "Tamaño: %d%n", f.getName(), f.canExecute(), f.isHidden(), f.getPath(), f.getAbsolutePath(),
                    f.length());
        }
        else{
            System.out.println("El archivo no existe");
        }
    }
}
