package ejercicio8;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Ejercicio8 {
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        File file = new File("./filesEj8/agenda.bin");
        FileInputStream fis = null;
        FileOutputStream fos = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        Contact contact = null;
        String name, address, birthDate;
        int phone, zipCode;
        boolean debt;
        float debtAmmount;


        //First we create the contact we want to save in the agenda.
        System.out.printf("|||||||||ADD NEW CONTACT|||||||||");
        System.out.printf("%nName: ");
        name = scn.nextLine();
        System.out.printf("%nPhone: ");
        phone = scn.nextInt();
        scn.nextLine(); //buffer cleaning
        System.out.printf("%nAddress: ");
        address = scn.nextLine();
        System.out.printf("%nZip code: ");
        zipCode = scn.nextInt();
        scn.nextLine(); //buffer cleaning
        System.out.printf("%nBirth date: ");
        birthDate = scn.nextLine();
        System.out.printf("%nDebt: ");
        debt = scn.nextBoolean();
        System.out.printf("%nDebt ammmount (if contact doesn't debt write 0): ");
        debtAmmount = scn.nextFloat();

        try {
            contact = new Contact(name, phone, address, zipCode, sdf.parse(birthDate), debt, debtAmmount);
        } catch (ParseException e) {
            System.out.println("Wrong date format. Try again (dd/mm/yyyy)");
        }

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);

            fos = new FileOutputStream(file, true);
            dos = new DataOutputStream(fos);

            //We do write the data of the contact in the file
            dos.writeUTF(contact.getName()); //name
            dos.writeInt(contact.getPhone()); //phone
            dos.writeUTF(contact.getAddress()); //address
            dos.writeInt(contact.getZipCode()); //zip code
            dos.writeUTF(contact.getBirthDate()); //birth date
            dos.writeBoolean(contact.isDebt()); //debt
            dos.writeFloat(contact.getDebtAmmount()); //debt ammount

            while(true){
                name = dis.readUTF();
                phone = dis.readInt();
                address = dis.readUTF();
                zipCode = dis.readInt();
                birthDate = dis.readUTF();
                debt = dis.readBoolean();
                debtAmmount = dis.readFloat();

                System.out.printf("Name: %s%n" +
                        "Phone: %d%n" +
                        "Address: %s%n" +
                        "Zip code: %d%n" +
                        "Birth date: %s%n" +
                        "Debt: %b%n" +
                        "Debt ammount: %.2f%n%n%n", name, phone, address, zipCode, birthDate, debt, debtAmmount);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e){
            try {
                fis.close();
                fos.close();
                dis.close();
                dos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
