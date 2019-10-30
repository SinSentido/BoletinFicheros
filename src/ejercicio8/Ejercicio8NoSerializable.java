package ejercicio8;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Ejercicio8NoSerializable {
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        Date birthDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        File file = new File("./filesEj8/agendaObjects.txt");
        FileInputStream fis = null;
        FileOutputStream fos;
        ObjectInputStream ois = null;
        ObjectOutputStream oos;

        Contact contact = null;
        Contact contactReaded;
        String name, address;
        int phone, zipCode;
        boolean debt;
        float debtAmmount;


        //First we create the contact we want to save in the agenda.
        System.out.print("|||||||||ADD NEW CONTACT|||||||||");
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

        contact = new Contact(name, phone, address, zipCode, birthDate, debt, debtAmmount);

        try {
            fis = new FileInputStream(file);

            fos = new FileOutputStream(file, true);
            oos = new ObjectOutputStream(fos);

            //We do write the data of the contact in the file
            oos.writeObject(contact);

            fos.close();
            oos.close();

            while(true){
                ois = new ObjectInputStream(fis);
                contactReaded = (Contact)ois.readObject();

                System.out.printf("Name: %s%n" +
                        "Phone: %d%n" +
                        "Address: %s%n" +
                        "Zip code: %d%n" +
                        "Birth date: %s%n" +
                        "Debt: %b%n" +
                        "Debt ammount: %.2f%n%n", contactReaded.getName(), contactReaded.getPhone(), contactReaded.getAddress(),
                        contactReaded.getZipCode(), contactReaded.getBirthDate(), contactReaded.isDebt(), contactReaded.getDebtAmmount());
            }
        } catch (EOFException e){
            try {
                ois.close();
                fis.close();
            } catch (IOException ex) {
                e.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
