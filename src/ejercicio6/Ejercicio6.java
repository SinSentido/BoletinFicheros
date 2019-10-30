package ejercicio6;

import java.io.*;

public class Ejercicio6 {
    public static void main(String[] args){
        /*Variables declaration*/
        File originFile = new File("./filesEj6/origen.txt");
        File destiny1 = new File("./filesEj6/destino1.txt");
        File destiny2 = new File("./filesEj6/destino2.txt");
        File destiny3 = new File("./filesEj6/destino3.txt");
        File rebuildFile = new File("./filesEj6/rehacer.txt");
        BufferedWriter bw1 = null, bw2 = null, bw3 = null, bwRebuild = null;
        FileReader frOrigin = null, fr1 = null, fr2 = null, fr3 = null;
        FileReader[] fileReaders = new FileReader[3];
        boolean exit = false;
        int index;

        //Writers initialization
        try {
            bw1 = new BufferedWriter(new FileWriter(destiny1));
            bw2 = new BufferedWriter(new FileWriter(destiny2));
            bw3 = new BufferedWriter(new FileWriter(destiny3));
            bwRebuild = new BufferedWriter(new FileWriter(rebuildFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Readers initialization
        try {
            frOrigin = new FileReader(originFile);
            fr1 = new FileReader(destiny1);
            fr2 = new FileReader(destiny2);
            fr3 = new FileReader(destiny3);
            fileReaders[0] =  fr1;
            fileReaders[1] =  fr2;
            fileReaders[2] =  fr3;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        char[] buffer = new char[15];
        int count = 0;

        try {
            while(((count = frOrigin.read(buffer)) != -1)){
                if(count == buffer.length){
                    bw1.write(buffer, 0, 5);
                    bw2.write(buffer, 5, 5);
                    bw3.write(buffer, 10, 5);
                }
                else if(count > 10){
                    bw1.write(buffer, 0, 5);
                    bw2.write(buffer, 5, 5);
                    bw3.write(buffer, 10, count - 10);
                }
                else if(count > 5){
                    bw1.write(buffer, 0, 5);
                    bw2.write(buffer, 5, count - 5);
                }
                else if(count > 0){
                    bw1.write(buffer, 0, count);
                }
            }
            frOrigin.close();
            bw1.close();
            bw2.close();
            bw3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            exit = false;
            index = 0;
            int position = 0;
            while(!exit){
                for(int i = 0; i < fileReaders.length; i++){
                    count = fileReaders[i].read(buffer, index, 5);
                    if(count == 5){
                        bwRebuild.write(buffer, position, 5);
                        position += 5;
                    }
                    else if (count < 5 && count > 0) {
                        bwRebuild.write(buffer, position, count);
                        position += count;
                    }
                    else{
                        if(i == 0){
                            exit = true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Closing readers and buffers
        try {
            fr1.close();
            fr2.close();
            fr3.close();

            bwRebuild.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
