import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Contact{
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private int zip;
    private long phoneNumber;
    private String email;

    Contact(String firstName, String lastName, String address, String city, String state, int zip, long phoneNumber, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public String getFirstname() { return firstName; }
    public String getLastname() { return lastName; }
    public String getCity(){
        return city;
    }
    public String getState(){
        return state;
    }
    public int getZip() {
        return zip;

    }
    public long getPhoneNumber(){
        return phoneNumber;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }

    @Override
    public String toString(){
        return "Contact Details: "+
                "\nName: "+ firstName + " "+lastName+
                "\nAddress: "+address+
                "\nCity: "+city+
                "\nState: "+state+
                "\nZip: "+zip+
                "\nPhone Number: "+phoneNumber+
                "\nEmail: "+email;

    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contact contact = (Contact) obj;
        return firstName.equalsIgnoreCase(contact.firstName) && lastName.equalsIgnoreCase(contact.lastName);
    }

    @Override
    public int hashCode(){
        return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase());
    }

    public void updatedContact(String address, String city, String state, int zip, long phone_number, String email){
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phone_number;
        this.email = email;
    }

    public void displayContact(){
        System.out.println("Contact Details: ");
        System.out.println("Name: "+ firstName+" "+lastName);
        System.out.println("Address: "+address);
        System.out.println("City: "+city);
        System.out.println("State: "+state);
        System.out.println("Zip: "+zip);
        System.out.println("Phone Number: "+phoneNumber);
        System.out.println("Email: "+email);
    }
}

class AddressBook {
    private List<Contact> contactList;
    private Map<String, List<Contact>> cityMap;
    private Map<String, List<Contact>> stateMap;

    public AddressBook() {
        this.contactList = new ArrayList<>();
        this.cityMap = new HashMap<>();
        this.stateMap = new HashMap<>();
    }

    public void addContact() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String addMore;

        do {
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

            Contact newContact = new Contact(firstName, lastName, address, city, state, zip, phone_number, email);
            if (contactList.contains(newContact)) {
                System.out.println("Contact added Successfully");
            } else {
                contactList.add(newContact);
                System.out.println("Contact added Successfully");
                cityMap.computeIfAbsent(city.toLowerCase(), k -> new ArrayList<>()).add(newContact);
                stateMap.computeIfAbsent(state.toLowerCase(), k-> new ArrayList<>()).add(newContact);
            }

            System.out.println("Do you want to add another contact? (yes/no)");
            addMore = br.readLine();
        } while (addMore.equalsIgnoreCase("yes"));
    }

    public void displaySortedContacts(){
        List<Contact> sortedContacts = contactList.stream()
                .sorted(Comparator.comparing(Contact::getFirstname).thenComparing(Contact::getLastname))
                .collect(Collectors.toList());

        if (sortedContacts.isEmpty()){
            System.out.println("No contacts to display.");
        }else {
            System.out.println("\nSorted Address Book Contacts:");
            sortedContacts.forEach(System.out::println);
        }
    }

    public void displayContactByCity(String city){
        List<Contact>  contacts = cityMap.get(city.toLowerCase());
        if (contacts!=null && !contacts.isEmpty()){
            System.out.println("Contacts in "+city+":");
            contacts.forEach(Contact::displayContact);
        }else {
            System.out.println("No contacts found in "+city+".");
        }
    }

    public void displayContactByState(String state){
        List<Contact> contacts = stateMap.get(state.toLowerCase());
        if (contacts!=null && !contacts.isEmpty()){
            System.out.println("Contacts in "+state+":");
            contacts.forEach(Contact::displayContact);
        }else {
            System.out.println("No contacts found in "+state+".");
        }
    }

    public long countContactsByCity(String city){
        return cityMap.getOrDefault(city.toLowerCase(), Collections.emptyList()).size();
    }

    public long countContactsByState(String state){
        return stateMap.getOrDefault(state.toLowerCase(), Collections.emptyList()).size();
    }

    public void displaySortedContactByCity(){
        List<Contact> sortedContacts = contactList.stream()
                .sorted(Comparator.comparing(Contact::getCity)
                        .thenComparing(Contact::getFirstname)
                        .thenComparing(Contact::getLastname))
                .collect(Collectors.toList());

        if (sortedContacts.isEmpty()){
            System.out.println("No contacts to display.");
        }else{
            System.out.println("\nSorted Address Book Contacts by City: ");
            sortedContacts.forEach(System.out::println);
        }
    }

    public void displaySortedContactByState(){
        List<Contact> sortedContacts = contactList.stream()
                .sorted(Comparator.comparing(Contact::getState)
                        .thenComparing(Contact::getFirstname)
                        .thenComparing(Contact::getLastname)).
                collect(Collectors.toList());

        if (sortedContacts.isEmpty()){
            System.out.println("No contacts to display.");
        }else {
            System.out.println("\nSorted Address Book Contacts by State: ");
            sortedContacts.forEach(System.out::println);
        }
    }

    public void displayContactsBySortedZip(){
        List<Contact> sortedContacts = contactList.stream()
                .sorted(Comparator.comparingInt(Contact::getZip)
                        .thenComparing(Contact::getFirstname)
                        .thenComparing(Contact::getLastname))
                .collect(Collectors.toList());

        if (sortedContacts.isEmpty()){
            System.out.println("No contacts to display.");
        }else {
            System.out.println("\nSorted Address Book Contacts by Zip: ");
            sortedContacts.forEach(System.out::println);
        }
    }

    public List<Contact> getContact () {
        return contactList;
    }
    public void displayContact() {
        if (contactList.isEmpty()) {
            System.out.println("No contacts to display.");
        } else {
            System.out.println("\nAddress Book Contacts:- ");
            for (Contact contact : contactList) {
                contact.displayContact();
            }
        }
    }

    public void editContact() {
        Scanner sc = new Scanner((System.in));
        System.out.println("Editing contact First Name: ");
        String firstname = sc.nextLine();
        System.out.println("Editing contact Last Name: ");
        String lastname = sc.nextLine();

        for (Contact contact : contactList) {
            if (contact.getFirstname().equalsIgnoreCase(firstname) && contact.getLastname().equalsIgnoreCase(lastname)) {
                System.out.println("Enter New Address: ");
                String address = sc.nextLine();
                System.out.println("Enter new City: ");
                String city = sc.nextLine();
                System.out.println("Enter new state: ");
                String state = sc.nextLine();
                System.out.println("Enter zip code: ");
                int zip = sc.nextInt();
                System.out.println("Enter new Phone Number: ");
                long phone_number = sc.nextLong();
                System.out.println("Enter new Mail Id: ");
                String mail = sc.nextLine();

                contact.updatedContact(address, city, state, zip, phone_number, mail);
                System.out.println("Contact updated Successfully");
                return;
            }
        }
        System.out.println("Contact not found.");

    }

    public void deleteContact() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the First name of the contact to delete: ");
        String firstname = sc.nextLine();
        System.out.println("Enter the Last name of the contact to delete: ");
        String lastname = sc.nextLine();

        for (int i = 0; i < contactList.size(); i++) {
            Contact contact = contactList.get(i);
            if (contact.getFirstname().equalsIgnoreCase(firstname) && contact.getLastname().equalsIgnoreCase(lastname)) {
                contactList.remove(i);
                System.out.println("Contact deleted successfully.");
                return;
            }
        }
        System.out.println("Contacts not found.");

    }

    public void writeContactsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Contact contact : contactList) {
                writer.write(contact.getFirstname() + "," + contact.getLastname() + "," + contact.getAddress() + "," + contact.getCity() + "," + contact.getState() + "," + contact.getZip() + "," + contact.getPhoneNumber() + "," + contact.getEmail());
                writer.newLine();
            }
            System.out.println("Contacts written to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file: "+ e.getMessage());
        }
    }

    public void readContactsFromFile(String filename) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {
                    Contact contact = new Contact(data[0], data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), Long.parseLong(data[6]), data[7]);
                    contactList.add(contact);
                    cityMap.computeIfAbsent(data[3].toLowerCase(), k -> new ArrayList<>()).add(contact);
                    stateMap.computeIfAbsent(data[4].toLowerCase(), k -> new ArrayList<>()).add(contact);
                }
            }
            System.out.println("Contacts read from file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading from file: "+ e.getMessage());
        }
    }

    public void writeContactsToCSVFile(String fileName) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            String[] header = {"First Name", "Last Name", "Address", "City", "State", "Zip", "Phone Number", "Email"};
            writer.writeNext(header);
            for (Contact contact : contactList) {
                String[] data = {contact.getFirstname(), contact.getLastname(), contact.getAddress(), contact.getCity(), contact.getState(), String.valueOf(contact.getZip()), String.valueOf(contact.getPhoneNumber()), contact.getEmail()};
                writer.writeNext(data);
            }
            System.out.println("Contacts written to CSV file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to CSV file: "+ e.getMessage());
        }
    }

    public void writeContactsToJSONFile(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            objectMapper.writeValue(new File(fileName), contactList);
            System.out.println("Contacts written to JSON file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to JSON file: "+ e.getMessage());
        }
    }
    
    public void readContactsFromCSVFile(String filename) {
        try(CSVReader reader = new CSVReader(new FileReader(filename))) {
            String[] nextline;
            reader.readNext();
            while ((nextline = reader.readNext()) != null) {
                if (nextline.length == 8) {
                Contact contact = new Contact(nextline[0], nextline[1], nextline[2], nextline[3], nextline[4], Integer.parseInt(nextline[5]), Long.parseLong(nextline[6]), nextline[7]);
                contactList.add(contact);
                cityMap.computeIfAbsent(nextline[3].toLowerCase(), k -> new ArrayList<>()).add(contact);
                stateMap.computeIfAbsent(nextline[4].toLowerCase(), k -> new ArrayList<>()).add(contact);
            }
        }
            System.out.println("Contacts read from CSV file successfully.");
        } catch (IOException | CsvValidationException e) {
            System.out.println("An error occurred while reading from CSV file: "+ e.getMessage());
        }
    }
    
    public void readContactsFromJSONFile(String filename) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Contact[] contacts = objectMapper.readValue(new File(filename), Contact[].class);
            contactList.clear();
            cityMap.clear();
            stateMap.clear();
            for (Contact contact : contacts) {
                contactList.add(contact);
                cityMap.computeIfAbsent(contact.getCity().toLowerCase(), k -> new ArrayList<>()).add(contact);
                stateMap.computeIfAbsent(contact.getState().toLowerCase(), k -> new ArrayList<>()).add(contact);
            }
            System.out.println("Contacts read from JSON file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading from JSON file: "+ e.getMessage());
        }
    }
}

class AddressBookManager {
    private Map<String, AddressBook> addressBooks;

    public AddressBookManager() {
        this.addressBooks = new HashMap<>();
    }

    public void createAddressBook(String name) {
        if (!addressBooks.containsKey(name)) {
            addressBooks.put(name, new AddressBook());
            System.out.println("Address Book " + name + " created successfully.");
        } else {
            System.out.println("Address Book with this name already exists.");
        }
    }

    public AddressBook getAddressBooks(String name) {
        return addressBooks.get(name);
    }

    public void displayAddressBooks() {
        if (addressBooks.isEmpty()) {
            System.out.println("No Address Books available.");
        } else {
            System.out.println("Available Address Books:");
            for (String name : addressBooks.keySet()) {
                System.out.println("- " + name);
            }
        }
    }

    public List<Contact> searchByCityOrState(String city, String state) {
        return addressBooks.values().stream()
                .flatMap(addressBook -> addressBook.getContact().stream())
                .filter(Contact -> Contact.getCity().equalsIgnoreCase(city) || Contact.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Address Book Program");
        AddressBookManager manager = new AddressBookManager();
        Scanner scanner = new Scanner(System.in);
        String option;

        do {
            System.out.println("\n*** Address Book Manager ***");
            System.out.println("1. Create Address Book");
            System.out.println("2. Add Contact to Address Book");
            System.out.println("3. Display Contacts in Address Book");
            System.out.println("4. Edit Contact in Address Book");
            System.out.println("5. Delete Available Address Books");
            System.out.println("6. Search contacts by City or State");
            System.out.println("7. Display Contacts by City");
            System.out.println("8. Display Contacts by State");
            System.out.println("9. Count Contacts by City");
            System.out.println("10. Count Contacts by State");
            System.out.println("11. Display Sorted Contacts");
            System.out.println("12. Display Sorted Contacts by City,State,Zip");
            System.out.println("13. Write Contacts to File");
            System.out.println("14. Read Contacts from File");
            System.out.println("15. Write Contacts to CSV File");
            System.out.println("16. Read Contacts from CSV File");
            System.out.println("17. Write Contacts to JSON File");
            System.out.println("18. Read Contacts from JSON File");
            System.out.println("19. Display Available Address Books");
            System.out.println("20. Exit");
            System.out.println("Choose an option: ");
            option = scanner.nextLine();


            switch (option) {
                case "1":
                    System.out.println("Enter Address Book name: ");
                    String bookName = scanner.nextLine();
                    manager.createAddressBook(bookName);
                    break;

                case "2":
                    System.out.println("Enter Address Book name: ");
                    String addBookName = scanner.nextLine();
                    AddressBook addBook = manager.getAddressBooks(addBookName);
                    if (addBook != null) {
                        addBook.addContact();
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;

                case "3":
                    System.out.println("Enter Address Book name: ");
                    String displayBookName = scanner.nextLine();
                    AddressBook displayBook = manager.getAddressBooks(displayBookName);
                    if (displayBook != null) {
                        displayBook.displayContact();
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;

                case "4":
                    System.out.println("Enter Address Book name: ");
                    String editBookName = scanner.nextLine();
                    AddressBook editBook = manager.getAddressBooks(editBookName);
                    if (editBook != null) {
                        editBook.editContact();
                    } else {
                        System.out.println("Address Book not found");
                    }
                    break;

                case "5":

                    System.out.println("Enter Address Book name: ");
                    String deleteBookName = scanner.nextLine();
                    AddressBook deleteBook = manager.getAddressBooks(deleteBookName);
                    if (deleteBook != null) {
                        deleteBook.deleteContact();
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;

                case "6":
                    System.out.println("Enter city to search");
                    String searchCity = scanner.nextLine();
                    System.out.println("Enter state to search: ");
                    String searchState = scanner.nextLine();
                    List<Contact> searchResults = manager.searchByCityOrState(searchCity, searchState);
                    if (searchResults.isEmpty()) {
                        System.out.println("No contacts found in the specified city or state.");
                    } else {
                        System.out.println("Search Results");
                        searchResults.forEach(Contact::displayContact);
                    }
                    break;
                case "7":
                    System.out.println("Enter Address Book name: ");
                    String cityBookName = scanner.nextLine();
                    AddressBook cityBook = manager.getAddressBooks(cityBookName);
                    if (cityBook != null) {
                        System.out.println("Enter city to search: ");
                        String city = scanner.nextLine();
                        cityBook.displayContactByCity(city);
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;

                case "8":
                    System.out.println("Enter Address Book name: ");
                    String stateName = scanner.nextLine();
                    AddressBook stateBook = manager.getAddressBooks(stateName);
                    if (stateBook != null) {
                        System.out.println("Enter State to search: ");
                        String state = scanner.nextLine();
                        stateBook.displayContactByState(state);
                    } else {
                        System.out.println("Address Book not found");
                    }
                    break;
                case "9":
                    System.out.println("Enter Address Book name: ");
                    String countCityBookName = scanner.nextLine();
                    AddressBook countCityBook = manager.getAddressBooks(countCityBookName);
                    if (countCityBook != null) {
                        System.out.println("Enter city to count: ");
                        String countCity = scanner.nextLine();
                        long count = countCityBook.countContactsByCity(countCity);
                        System.out.println("Number of contacts in " + countCity + ": " + count);
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;
                case "10":
                    System.out.println("Enter Address Book name: ");
                    String countStateBookName = scanner.nextLine();
                    AddressBook countStateBook = manager.getAddressBooks(countStateBookName);
                    if (countStateBook != null) {
                        System.out.println("Enter state to count: ");
                        String countState = scanner.nextLine();
                        long count = countStateBook.countContactsByState(countState);
                        System.out.println("Number of contacts in " + countState + ": " + count);
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;

                case "11":
                    System.out.println("Enter the AddressBook name: ");
                    String sortBookName = scanner.nextLine();
                    AddressBook sortedName = manager.getAddressBooks(sortBookName);
                    if (sortedName!=null){
                        sortedName.displaySortedContacts();
                    }else {
                        System.out.println("Address Book not found.");
                    }
                    break;

                case "12":
                    System.out.println("Enter the AddressBook name: ");
                    String sortedBookName = scanner.nextLine();
                    AddressBook sortName = manager.getAddressBooks(sortedBookName);
                    if (sortedBookName!=null){
                        System.out.println("Choose sorting criteria: ");
                        System.out.println("1. Sort by City");
                        System.out.println("2. Sort by State");
                        System.out.println("3. Sort by Zip");
                        String sortOption = scanner.nextLine();
                        switch (sortOption){
                            case "1":
                                sortName.displaySortedContactByCity();
                                break;
                            case "2":
                                sortName.displaySortedContactByState();
                                break;
                            case "3":
                                sortName.displayContactsBySortedZip();
                                break;
                            default:
                                System.out.println("Invalid Option");
                        }
                    }else {
                        System.out.println("Address Book not found.");
                    }
                    break;

                case "13":
                    System.out.println("Enter the Address Book name: ");
                    String writeBookName = scanner.nextLine();
                    AddressBook writeBook = manager.getAddressBooks(writeBookName);
                    if (writeBook != null) {
                        System.out.println("Enter the file name: ");
                        String fileName = scanner.nextLine();
                        writeBook.writeContactsToFile(fileName);
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;

                case "14":
                    System.out.println("Enter the Address Book name: ");
                    String readBookName = scanner.nextLine();
                    AddressBook readBook = manager.getAddressBooks(readBookName);
                    if (readBook != null) {
                        System.out.println("Enter the file name: ");
                        String fileName = scanner.nextLine();
                        readBook.readContactsFromFile(fileName);
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;

                case "15":
                    System.out.println("Enter Address Book name to write to CSV: ");
                    String csvBookName = scanner.nextLine();
                    AddressBook csvBook = manager.getAddressBooks(csvBookName);
                    if (csvBook != null) {
                        System.out.println("Enter filename to write contacts to CSV: ");
                        String csvFileName = scanner.nextLine();
                        csvBook.writeContactsToCSVFile(csvFileName);
                    }else {
                        System.out.println("Address Book not found.");
                    }
                    break;
                    
                    case "16":
                    System.out.println("Enter the Address Book name: ");
                    String readCSVBookName = scanner.nextLine();
                    AddressBook readCSVBook = manager.getAddressBooks(readCSVBookName);
                    if (readCSVBook != null) {
                        System.out.println("Enter the file name: ");
                        String fileName = scanner.nextLine();
                        readCSVBook.readContactsFromCSVFile(fileName);
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;
                    
                case "17":
                    System.out.println("Enter Address Book name to write to JSON");
                    String jsonBookName = scanner.nextLine();
                    AddressBook jsonBook = manager.getAddressBooks(jsonBookName);
                    if (jsonBook != null) {
                        System.out.println("Enter filename to write contacts to JSON: ");
                        String jsonFilename = scanner.nextLine();
                        jsonBook.writeContactsToJSONFile(jsonFilename);
                    }else {
                        System.out.println("Address Book not found.");
                    }
                    break;
                    
                case "18":
                    System.out.println("Enter the Address Book name: ");
                    String readJSONBookName = scanner.nextLine();
                    AddressBook readJSONBook = manager.getAddressBooks(readJSONBookName);
                    if (readJSONBook != null) {
                        System.out.println("Enter the file name: ");
                        String fileName = scanner.nextLine();
                        readJSONBook.readContactsFromJSONFile(fileName);
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;
                    

                case "19":
                    manager.displayAddressBooks();
                    break;
                case "20":

                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid Input");
            }
        } while (true);
    }
}

