//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.*;

class Contact{
    private String first_name;
    private String last_name;
    private String address;
    private String city;
    private String state;
    private int zip;
    private long phone_number;
    private String email;

    Contact(String first_name, String last_name, String address, String city, String state, int zip, long phone_number, String email){
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone_number = phone_number;
        this.email = email;
    }
    public void displayContact(){
        System.out.println("Contact Details: ");
        System.out.println("Name: "+ first_name+" "+last_name);
        System.out.println("Address: "+address);
        System.out.println("City: "+city);
        System.out.println("State: "+state);
        System.out.println("Zip: "+zip);
        System.out.println("Phone Number: "+phone_number);
        System.out.println("Email: "+email);
    }
}
class AddressBook{
    private Contact contact;
    public void addContact() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(" Enter the First Name: ");
        String firstName = br.readLine();
        System.out.println("Last Name: ");
        String lastName = br.readLine();
        System.out.println("Enter Address: ");
        String address = br.readLine();
        System.out.println("Enter City Name: ");
        String city = br.readLine();
        System.out.println("State: ");
        String state = br.readLine();
        System.out.println("Enter Zip code: ");
        int zip = Integer.parseInt(br.readLine());
        System.out.println("Enter mail Id: ");
        String email = br.readLine();
        System.out.println("Enter Phone Number: ");
        long phone_number = Long.parseLong(br.readLine());

        contact = new Contact(firstName, lastName, address, city, state, zip, phone_number, email);
        System.out.println("Contact added Successfully");
    }
    public void displayContact() {
        if (contact != null) {
            contact.displayContact();
        } else {
            System.out.println("No contacts to display.");
        }
    }
}
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Address Book Program");
        AddressBook ab = new AddressBook();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n   2Menu");
            System.out.println("1. Add Contact");
            System.out.println("2. Display Contact");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    ab.addContact();
                    break;

                case 2:
                    ab.displayContact();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid Input");
            }
        }
    }
}