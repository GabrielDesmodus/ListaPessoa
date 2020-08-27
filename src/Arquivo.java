
public class Arquivo {
    private File file;

    public DataFile(String fileName) {
        this.file = new File(fileName);
    }

    public DataFile(File file) {
        this.file = file;
    }

    public void save(Person person) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(person.getFirstName()+"\r\n" + person.getSurname() + "\r\n" + person.getPhoneNumber() + "\r\n" + person.getEmail() +
                    "\r\n" + person.getAddress() + "\r\n\r\n");
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public List<Person> loadAll() throws IOException {
        List<Person> people = new ArrayList<Person>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String name = null;
            while((name = reader.readLine()) != null) {
                Person person = new Person(name, reader.readLine(), reader.readLine(), reader.readLine(), reader.readLine());
                people.add(person);
                reader.readLine();
            }
        }
        catch ( IOException e) {
            System.out.println(e);
        }

        return people;
    }
}
