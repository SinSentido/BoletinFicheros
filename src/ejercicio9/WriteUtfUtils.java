package ejercicio9;

import ejercicio8.Contact;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class WriteUtfUtils {

    /*
     *Write UTF
     * identifier -> int 4B
     * exists -> boolean 1B
     * name -> string 12B
     * phone -> int 4B
     * address -> string 22B
     * zip code -> int 4B
     * birth date -> string 12B
     * debt -> boolean 1B
     * debt amount -> float 4B
     *
     * TOTAL BYTES PER CONTACT -> 64B
     *
     * n-1 *64
     * */

    public static void copyFromObjectsToBinaries(File objectsFile, File binFile){
        int identifier = 1;

        //objects to work with files
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        RandomAccessFile write = null;

        //object
        Contact contacto;
        String name, address, birthDate;

        try {
            fis = new FileInputStream(objectsFile);
            write = new RandomAccessFile(binFile, "rw");

            while(true){
                ois = new ObjectInputStream(fis);
                contacto = (Contact) ois.readObject();

                name = String.format("%10.10s", contacto.getName());
                address = String.format("%20.20s", contacto.getAddress());
                birthDate = String.format("%10.10s", contacto.getBirthDate());

                //Write the elements in the new random access file
                write.writeInt(identifier); //identifier
                write.writeBoolean(true); //exists
                write.writeUTF(name); //name
                write.writeInt(contacto.getPhone()); //phone
                write.writeUTF(address); //address
                write.writeInt(contacto.getZipCode()); //zip code
                write.writeUTF(birthDate); //birth date
                write.writeBoolean(contacto.isDebt()); //debt
                write.writeFloat(contacto.getDebtAmmount()); //debt amount
                identifier++;
            }

        }catch(EOFException e) {
            System.out.printf("Agenda loaded correctly.....%n%n");
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Method to show all contacts in the file*/
    public static void showAllContacts(File agenda){
        RandomAccessFile read = null;
        boolean exists;

        try {
            read = new RandomAccessFile(agenda, "r");

            while(true){
                System.out.println(read.readInt()); //id
                if(!(exists = read.readBoolean())){
                    System.out.println("This contact does not exist");
                    read.skipBytes(59);
                }
                else{
                    System.out.println(true); //exists
                    System.out.println(read.readUTF().trim()); //name
                    System.out.println(read.readInt()); //phone
                    System.out.println(read.readUTF().trim()); //address
                    System.out.println(read.readInt()); //zip code
                    System.out.println(read.readUTF()); //birth date
                    System.out.println(read.readBoolean());
                    System.out.println(read.readFloat());
                }
                System.out.println("\n|||||||||||||||||||\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e){
            try {
                read.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("End of reading");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Method to show a specific contact of the agenda identified by the id*/
    public static void showContact(int contactId, File agenda){
        RandomAccessFile read = null;
        int position = (contactId-1)*64;
        boolean exists;

        try {
            read = new RandomAccessFile(agenda, "r");
            read.seek(position);

            if(position >= read.length()){
                System.out.println("The contact does not exist");
            }
            else{
                System.out.println(read.readInt()); //id
                if(!(exists = read.readBoolean())){
                    System.out.println("This contact does not exist");
                }
                else{
                    System.out.println(true); //exists
                    System.out.println(read.readUTF().trim()); //name
                    System.out.println(read.readInt()); //phone
                    System.out.println(read.readUTF().trim()); //address
                    System.out.println(read.readInt()); //zip code
                    System.out.println(read.readUTF()); //birth date
                    System.out.println(read.readBoolean()); //debt
                    System.out.println(read.readFloat()); //debt amount
                }
                read.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to add a contact
    public static void addContact(File agenda){
        RandomAccessFile write = null;
        Scanner scn = new Scanner(System.in);

        String name, address;
        int phone, zipCode;
        float debtAmmount;
        boolean debt;
        Date birthDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        System.out.print("|||||||||ADD NEW CONTACT|||||||||");
        System.out.printf("%nName: ");
        name = String.format("%10.10s", scn.nextLine());

        System.out.printf("%nPhone: ");
        phone = scn.nextInt();

        scn.nextLine(); //buffer cleaning
        System.out.printf("%nAddress: ");
        address = String.format("%20.20s", scn.nextLine());

        System.out.printf("%nZip code: ");
        zipCode = scn.nextInt();
        scn.nextLine(); //buffer cleaning

        System.out.printf("%nBirth date: ");
        while(birthDate == null){
            try {
                birthDate = sdf.parse(scn.nextLine());
            } catch (ParseException e) {
                System.out.println("wrong date try again");
            }
        }

        System.out.printf("%nDebt: ");
        debt = scn.nextBoolean();

        System.out.printf("%nDebt ammmount (if contact doesn't debt write 0): ");
        debtAmmount = scn.nextFloat();

        try {
            write = new RandomAccessFile(agenda, "rw");
            write.seek(write.length());

            write.writeInt((int)write.length()/64+1);
            write.writeBoolean(true);
            write.writeUTF(name);
            write.writeInt(phone);
            write.writeUTF(address);
            write.writeInt(zipCode);
            write.writeUTF(sdf.format(birthDate));
            write.writeBoolean(debt);
            write.writeFloat(debtAmmount);

            write.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeContact(int id, File agenda){
        RandomAccessFile write;
        int position = (id-1)*64;

        try {
            write = new RandomAccessFile(agenda, "rw");

            if(position > write.length()){
                System.out.println("The contact does not exist");
            }else{
                write.seek(position + 4);

                write.writeBoolean(false);
            }

            write.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void modifyContactDebt(int id, File agenda){
        Scanner scn = new Scanner(System.in);
        RandomAccessFile write;
        int position = ((id-1)*64)+59;//Added 59 to pos in the debt field
        boolean debt;
        float debtAmount;

        System.out.println("The contact debts? Write true or false:");
        debt = scn.nextBoolean();
        System.out.println("How much does the contact debts? (write 0 if the last steps was false):");
        debtAmount = scn.nextFloat();

        try {
            write = new RandomAccessFile(agenda, "rw");
            write.seek(position);

            write.writeBoolean(debt);
            write.writeFloat(debtAmount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compactFile(File fileToCompact){
        File compactedFile = new File("./filesEj9/agendaBinaries2.bin");
        RandomAccessFile read;
        RandomAccessFile write;
        int totalContacts = 0, position = 0;

        try {
            read = new RandomAccessFile(fileToCompact, "r");
            write = new RandomAccessFile(compactedFile, "rw");

            totalContacts = (int)read.length()/64;

            for(int i=0; i<totalContacts; i++){
                position = i*64;

                //Checking if the contact is disabled
                read.seek(position+4);

                if(read.readBoolean()){
                   read.seek(position);

                   read.readInt();
                   write.writeInt(i+1); //id
                   write.writeBoolean(read.readBoolean()); //exists
                   write.writeUTF(String.format("%10.10s", read.readUTF())); //name
                   write.writeInt(read.readInt()); //phone
                   write.writeUTF(String.format("%20.20s", read.readUTF()));
                   write.writeInt(read.readInt());
                   write.writeUTF(read.readUTF());
                   write.writeBoolean(read.readBoolean());
                   write.writeFloat(read.readFloat()); //debt amount
                }else{
                    System.out.println("Eliminado contacto");
                }
            }
            read.close();
            write.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileToCompact.delete();
        compactedFile.renameTo(new File(fileToCompact.getAbsolutePath()));
    }
}
