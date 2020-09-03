import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
    private File file;

    public Arquivo(String fileName) {
        this.file = new File(fileName);
    }

    public Arquivo(File file) {
        this.file = file;
    }

    public void saveAll(List<Pessoa> people) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {//AGORA O WRITER COMEÇA PELO COMEÇO DO ARQUIVO
        	for(Pessoa pessoa : people) {//RODA O FOR ATÉ SALVAR TODAS AS PESSOAS NO ARQUIVO
            writer.write(pessoa.getNome()+"\r\n" + pessoa.getSobrenome() + "\r\n" + pessoa.getTelefone() + "\r\n" + pessoa.getCel() + "\r\n" + pessoa.getEmail() 
            + "\r\n" + pessoa.getEndereco() + "\r\n" + pessoa.getEquipamento() + "\r\n" + pessoa.getData() + "\r\n" + pessoa.getGarantia() + "\r\n" + pessoa.getServico()
                        + "\r\n\r\n");
        	}
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public List<Pessoa> loadAll() throws IOException {
        List<Pessoa> people = new ArrayList<Pessoa>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String name = null;
            while((name = reader.readLine()) != null) {
                Pessoa pessoa = new Pessoa(name, reader.readLine(), reader.readLine(), reader.readLine(), reader.readLine(), reader.readLine(), reader.readLine(), LocalDate.parse(reader.readLine()), reader.readLine(), reader.readLine());
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
