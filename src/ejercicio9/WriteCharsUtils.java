package ejercicio9;

import ejercicio8.Contact;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class WriteCharsUtils {
    /*
     *Write CHAR
     * identifier -> int 4B
     * exists -> boolean 1B
     * name -> string 20B
     * phone -> int 4B
     * address -> string 40B
     * zip code -> int 4B
     * birth date -> string 20B
     * debt -> boolean 1B
     * debt amount -> float 4B
     *
     * TOTAL BYTES PER CONTACT -> 98B
     *
     * (n - 1) * 98
     * */

    public static void copyFromObjectsToBinaries(File objectsFile, File binFile){
        int identifier = 1;

        //objects to work with files
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        RandomAccessFile write;

        //object
        Contact contacto;

        //buffers
        StringBuffer bufferName;
        StringBuffer bufferAddress;

        try {
            fis = new FileInputStream(objectsFile);
            write = new RandomAccessFile(binFile, "rw");

            while(true){
                ois = new ObjectInputStream(fis);
                contacto = (Contact) ois.readObject();

                bufferName = new StringBuffer(contacto.getName());
                bufferName.setLength(10);
                bufferAddress = new StringBuffer(contacto.getAddress());
                bufferAddress.setLength(20);

                //Write the elements in the new random access file
                write.writeInt(identifier); //identifier
                write.writeBoolean(true); //exists
                write.writeChars(bufferName.toString()); //name
                write.writeInt(contacto.getPhone()); //phone
                write.writeChars(bufferAddress.toString()); //address
                write.writeInt(contacto.getZipCode()); //zip code
                write.writeChars(contacto.getBirthDate()); //birth date
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
        String name, address, birthDate;

        try {
            read = new RandomAccessFile(agenda, "r");

            while(true){
                System.out.println(read.readInt()); //id
                if(!(exists = read.readBoolean())){
                    System.out.println("This contact does not exist");
                    read.skipBytes(59);
                }
                else{
                    name = "";
                    address = "";
                    birthDate = "";

                    System.out.println(true); //exists
                    for(int i=0; i<10; i++){
                        name += read.readChar();
                    }
                    System.out.println(name.trim());
                    System.out.println(read.readInt()); //phone
                    for(int i=0; i<20; i++){
                        address += read.readChar();
                    }
                    System.out.println(address.trim()); //address
                    System.out.println(read.readInt()); //zip code
                    for(int i=0; i<10; i++){
                        birthDate += read.readChar();
                    }
                    System.out.println(birthDate.trim()); //birth date
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
        int position = (contactId-1)*98;
        boolean exists;
        String name, address, birthDate;

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
                    name = "";
                    address = "";
                    birthDate = "";

                    System.out.println(true); //exists
                    for(int i=0; i<10; i++){
                        name += read.readChar();
                    }
                    System.out.println(name.trim());
                    System.out.println(read.readInt()); //phone
                    for(int i=0; i<20; i++){
                        address += read.readChar();
                    }
                    System.out.println(address.trim()); //address
                    System.out.println(read.readInt()); //zip code
                    for(int i=0; i<10; i++){
                        birthDate += read.readChar();
                    }
                    System.out.println(birthDate.trim()); //birth date
                    System.out.println(read.readBoolean());
                    System.out.println(read.readFloat());
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
        RandomAccessFile write;
        Scanner scn = new Scanner(System.in);

        StringBuffer nameBuffer, addressBuffer;
        int phone, zipCode;
        float debtAmmount;
        boolean debt;
        Date birthDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        System.out.print("|||||||||ADD NEW CONTACT|||||||||");
        System.out.printf("%nName: ");
        nameBuffer = new StringBuffer(scn.nextLine());
        nameBuffer.setLength(10);

        System.out.printf("%nPhone: ");
        phone = scn.nextInt();

        scn.nextLine(); //buffer cleaning
        System.out.printf("%nAddress: ");
        addressBuffer = new StringBuffer(scn.nextLine());
        addressBuffer.setLength(20);

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

            write.writeInt((int)write.length()/98+1);
            write.writeBoolean(true);
            write.writeChars(nameBuffer.toString());
            write.writeInt(phone);
            write.writeChars(addressBuffer.toString());
            write.writeInt(zipCode);
            write.writeChars(sdf.format(birthDate));
            write.writeBoolean(debt);
            write.writeFloat(debtAmmount);

            write.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Method to remova a contact from the agenda*/
    public static void removeContact(int id, File agenda){
        RandomAccessFile write;
        int position = (id-1)*98;

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
        int position = ((id-1)*98)+93;//Added 59 to pos in the debt field
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
        File compactedFile = new File("./filesEj9/agendaBinariesChars2.bin");
        RandomAccessFile read;
        RandomAccessFile write;
        int totalContacts = 0, position = 0;
        String name, address, birthDate;

        try {
            read = new RandomAccessFile(fileToCompact, "r");
            write = new RandomAccessFile(compactedFile, "rw");

            totalContacts = (int)read.length()/98;

            for(int i=0; i<totalContacts; i++){
                position = i*98;

                //Checking if the contact is disabled
                read.seek(position+4);

                if(read.readBoolean()){
                    read.seek(position);
                    name = "";
                    address = "";
                    birthDate = "";

                    read.readInt();
                    write.writeInt(i+1); //id
                    write.writeBoolean(read.readBoolean()); //exists
                    for(int j=0; j<10; j++){
                        name += read.readChar();
                    }
                    write.writeChars(name); //name
                    write.writeInt(read.readInt()); //phone
                    for(int j=0; j<20; j++){
                        address += read.readChar();
                    }
                    write.writeChars(address);
                    write.writeInt(read.readInt());
                    for(int j=0; j<10; j++){
                        birthDate += read.readChar();
                    }
                    write.writeChars(birthDate);
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
