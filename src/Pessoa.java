import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Pessoa {
	private static AtomicInteger nextID = new AtomicInteger(1);

    private int id;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String cel;
    private String email;
    private String endereco;
    private String equipamento;
    private LocalDate data;
    private String garantia;
    private String servico;

    Pessoa(int id, String nome, String sobrenome, String telefone, String cel, String email, String endereco, String equipamento, LocalDate data, String garantia, String servico) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone; 
        this.cel = cel;
        this.email = email;
        this.endereco = endereco;
        this.equipamento = equipamento;
        this.data = data;
        this.garantia = garantia;
        this.servico = servico;
    }

    Pessoa(String nome, String sobrenome, String telefone, String cel, String email, String endereco, String equipamento, LocalDate data, String garantia, String servico) {
        this(nextID.getAndIncrement(), nome, sobrenome, telefone, cel, email, endereco, equipamento, data, garantia, servico);
    }

    int getID() {
        return id;
    }

    String getNome() {
        return nome;
    }

    String getSobrenome() {
        return sobrenome;
    }

    String getTelefone() {
        return telefone;
    }

    String getCel() {
        return cel;
    }

    String getEmail() {
        return email;
    }

    String getEndereco() {
        return endereco;
    }

    String getEquipamento() {
        return equipamento;
    }

    LocalDate getData() {
        return data;
    }

    String getGarantia() {
        return garantia;
    }

    String getServico() {
        return servico;
    }

    @Override
    public String toString() {
        return "\n\nID: " + getID() + "\nNome: " + getNome() + "\nSobrenome: " + getSobrenome() + "\nTelefone: " + getTelefone() + "\nCelular: " + getCel() + "\nEmail: " +
                getEmail() + "\nEndereço: " + getEndereco() + "\nEquipamento: " + getEquipamento() + "\nData: " + getData() + "\nGarantia: " + getGarantia() + "\nServiço: " + getServico();
    }
}
