import java.util.concurrent.atomic.AtomicInteger;

public class Pessoa {
	private static AtomicInteger nextID = new AtomicInteger(1);

    private int id;
    private String prmnome;
    private String surname;
    private String phoneNumber;
    private String email;
    private String address;
    private String equipamento;
    private String data;
    private String garantia;
    private String serviço;

    Person(int id, String firstName, String surname, String phoneNumber, String email, String address) {
        this.id = id;
        this.prmnome = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    Person(String firstName, String surname, String phoneNumber, String email, String address) {
        this(nextID.getAndIncrement(), firstName, surname, phoneNumber, email, address);
    }

    int getID() {
        return id;
    }

    String getFirstName() {
        return prmnome;
    }

    String getSurname() {
        return surname;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }

    String getEmail() {
        return email;
    }

    String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "\n\nID: " + getID() + "\nName: " + getFirstName() + "\nSurname: " + getSurname() + "\nPhone number: " + getPhoneNumber() + "\nEmail: " +
                getEmail() + "\nAddress: " + getAddress();
    }
}
