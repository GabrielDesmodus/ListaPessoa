import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Arquivo {
    private File file;

    public Arquivo(String fileName) {
        this.file = new File(fileName);
    }

    public Arquivo(File file) {
        this.file = file;
    }

    public void save(Pessoa pessoa) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(pessoa.getFirstName()+"\r\n" + pessoa.getSurname() + "\r\n" + pessoa.getPhoneNumber() + "\r\n" + pessoa.getEmail() +
                    "\r\n" + pessoa.getAddress() + "\r\n\r\n");
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public List<Pessoa> loadAll() throws IOException {
        List<Pessoa> people = new ArrayList<Pessoa>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String name = null;
            while((name = reader.readLine()) != null) {
                Pessoa pessoa = new Pessoa(name, reader.readLine(), reader.readLine(), reader.readLine(), reader.readLine());
                people.add(pessoa);
                reader.readLine();
            }
        }
        catch ( IOException e) {
            System.out.println(e);
        }

        return people;
    }
}
