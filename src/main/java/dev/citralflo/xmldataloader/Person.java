package dev.citralflo.xmldataloader;

public class Person {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String pesel;

    private ContractType type;

    Person() {
    }

    public Person(String firstName, String lastName, String email, String phoneNumber, String pesel, ContractType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.type = type;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Person {\n" + "firstName=" + firstName + "\n- lastName=" + lastName + "\n- email=" + email + "\n- phoneNumber=" + phoneNumber + "\n- pesel=" + pesel + "\n- type=" + type + '}';
    }
}
