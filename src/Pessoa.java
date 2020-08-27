
public class Pessoa {
	public class Person {
	    private static AtomicInteger nextID = new AtomicInteger(1);

	    private int id;
	    private String firstName;
	    private String surname;
	    private String phoneNumber;
	    private String email;
	    private String address;

	    Person(int id, String firstName, String surname, String phoneNumber, String email, String address) {
	        this.id = id;
	        this.firstName = firstName;
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
	        return firstName;
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
}
