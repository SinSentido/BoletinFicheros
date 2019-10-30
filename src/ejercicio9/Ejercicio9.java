package ejercicio9;

import java.io.*;
import java.util.Scanner;

public class Ejercicio9 {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        File agendaOriginal = new File("./filesEj8/agendaObjects.txt");
        File agendaAleaotios = new File("./filesEj9/agendaBinaries.bin");
        File agendaAleaotiosChars = new File("./filesEj9/agendaBinariesChars.bin");
        int choose = 0, id=0;
        boolean exit = false;

        WriteUtfUtils.copyFromObjectsToBinaries(agendaOriginal, agendaAleaotios);
        WriteCharsUtils.copyFromObjectsToBinaries(agendaOriginal, agendaAleaotiosChars);

        while(!exit){
            System.out.printf("WELLCOME TO YOUR AGENDA APPLICATION:%n%n" +
                    "1. SHOW ALL CONTACTS%n" +
                    "2. SHOW ONE CONTACT (by id)%n" +
                    "3. ADD CONTACT%n" +
                    "4. REMOVE CONTACT (by id)%n" +
                    "5. MODIFY CONTACT'S DEBT%n" +
                    "6. COMPACT FILE%n");

            choose = scn.nextInt();

            switch (choose){
                case 1: //show all contacts
//                    WriteUtfUtils.showAllContacts(agendaAleaotios);
                    WriteCharsUtils.showAllContacts(agendaAleaotiosChars);
                    break;
                case 2: //show one contact
                    System.out.println("Write the id of the contact you want to show: ");
                    id = scn.nextInt();
//                    WriteUtfUtils.showContact(id, agendaAleaotios);
                    WriteCharsUtils.showContact(id, agendaAleaotiosChars);
                    break;
                case 3: //add one contact
//                    WriteUtfUtils.addContact(agendaAleaotios);
                    WriteCharsUtils.addContact(agendaAleaotiosChars);
                    break;
                case 4: //remove contact
                    System.out.println("Write the id of the contact you want to delete: ");
                    id = scn.nextInt();
//                    WriteUtfUtils.removeContact(id, agendaAleaotios);
                    WriteCharsUtils.removeContact(id, agendaAleaotiosChars);
                    break;
                case 5: //Modify the contact's debt
                    System.out.println("Write the id of the contact you want to modify the debt: ");
                    id = scn.nextInt();
//                    WriteUtfUtils.modifyContactDebt(id, agendaAleaotios);
                    WriteCharsUtils.modifyContactDebt(id, agendaAleaotiosChars);
                    break;
                case 6: //Compact file
//                    WriteUtfUtils.compactFile(agendaAleaotios);
                    WriteCharsUtils.compactFile(agendaAleaotiosChars);
                    break;
                default:
                    System.out.println("Agenda is closing. Bye bye.");
                    exit = true;
                    break;
            }

        }
    }
}
