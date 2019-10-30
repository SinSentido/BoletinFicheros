package ejercicio10;

import java.util.ArrayList;
import java.util.List;

import ejercicio8.Contact;

public class ContactList {
    List<Contact> contactList = new ArrayList<>();

    public ContactList(){}

    public void add(Contact contact){
        contactList.add(contact);
    }

    public List<Contact> getContactList(){
        return contactList;
    }
}
