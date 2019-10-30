package ejercicio10;

import com.thoughtworks.xstream.XStream;
import ejercicio8.Contact;

import java.io.*;

public class Ejercicio10 {
    public static void main(String[] args) {
        /*First of all we read from the agendaObjects file
        * We save the readed contacts in an object list
        * After that we write thath object list in an XML file.*/

        File file = new File("./filesEj8/agendaObjects.txt");
        FileInputStream fis = null;
        FileOutputStream agenda = null;
        ObjectInputStream ois = null;

        ContactList contactList = new ContactList();
        XStream xstream = new XStream();

        try{
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            agenda = new FileOutputStream("./filesEj10/agenda.xml");

            while(true){
                Contact contact = (Contact) ois.readObject();
                contactList.add(contact);
            }
        } catch(EOFException e){
            try {
                fis.close();
                ois.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        xstream.alias("Pepe", ContactList.class);
        xstream.alias("cacahuete", Contact.class);
        xstream.toXML(contactList, agenda);
    }
}
