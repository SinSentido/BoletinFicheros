package ejercicio8;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Contact implements Serializable {
    private String name, address;
    Date birthDate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private int phone, zipCode;
    private boolean debt;
    private float debtAmmount;

    public Contact(String name, int phone, String address, int zipCode, Date birthDate, boolean debt, float debtAmmount){
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.zipCode = zipCode;
        this.birthDate = birthDate;
        this.debt = debt;
        this.debtAmmount = debtAmmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDate() {
        return simpleDateFormat.format(birthDate);
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isDebt() {
        return debt;
    }

    public void setDebt(boolean debt) {
        this.debt = debt;
    }

    public float getDebtAmmount() {
        return debtAmmount;
    }

    public void setDebtAmmount(float debtAmmount) {
        this.debtAmmount = debtAmmount;
    }
}
