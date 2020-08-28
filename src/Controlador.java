import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Controlador {
    private enum Action {
        ADDCLIENTE,
        PROCURARCLIENTES,
        MOSTRARCLIENTES,
        SAIR
    }

    private enum OpcaoFiltrada {
    	NOME,
    	SOBRENOME
    }

    private Arquivo arquivo;
    private Scanner in;
    private List<Pessoa> pessoas;

    public Controlador(Arquivo arquivo) {
        in = new Scanner(System.in);
        this.arquivo = arquivo;
        try {
            pessoas = arquivo.loadAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Controlador(String nomeArquivo) {
        this(new Arquivo(nomeArquivo));
    }

    public Controlador(File arquivo) {
        this(new Arquivo(arquivo));
    }

    private List<Pessoa> buscarPessoa(String busca, OpcaoFiltrada filter) {
        switch (filter) {
            case NOME: return pessoas.stream().filter(person -> person.getNome().equals(busca)).collect(Collectors.toList());
            case SOBRENOME: return pessoas.stream().filter(person -> person.getSobrenome().equals(busca)).collect(Collectors.toList());
            default:
                System.out.println("Opção de filtro inválida");
                return new ArrayList<>();
        }
    }

    public void run() {
        while(true) {
            Action acao = mostrarMenu();
            switch (acao) {
                case ADDCLIENTE:
                    Pessoa pessoa = getInfoPessoa();
                    arquivo.save(pessoa);
                    pessoas.add(pessoa);
                    break;
                case PROCURARCLIENTES:
                    OpcaoFiltrada filtroSelecionado = mostrarOpcoesFiltro();
                    System.out.print("Nome: ");
                    String busca = in.nextLine();
                    List<Pessoa> pessoaFiltrada = buscarPessoa(busca, filtroSelecionado);
                    if (pessoaFiltrada.size() == 0) {
                        System.out.println("Sem resultados");
                    } else {
                        for (Pessoa p : pessoaFiltrada)
                            System.out.println(p);
                    }
                    break;
                case MOSTRARCLIENTES:
                    System.out.println(this.pessoas);
                    System.out.println();
                    break;
                case SAIR:
                    System.out.println("Exiting Program");
                    System.exit(0);
                    break;
            }
        }
    }

    private Action mostrarMenu() {
        System.out.println("1. Adicionar cliente");
        System.out.println("2. Pesquisar cliente");
        System.out.println("3. Mostrar todos os clientes");
        System.out.println("4. Fechar programa");
        String escolha;
        do {
            escolha = in.nextLine();
            switch (escolha) {
                case "1": return Action.ADDCLIENTE;
                case "2": return Action.PROCURARCLIENTES;
                case "3": return Action.MOSTRARCLIENTES;
                case "4": return Action.SAIR;
                default: System.out.println("Digite um número válido");
            }
        } while (!escolha.equals("4"));
        return null; //should never reach here
    }

    private Pessoa getInfoPessoa() {
        System.out.println("Nome: ");
        String nome = in.nextLine();
        System.out.println("Sobrenome: ");
        String sobrenome = in.nextLine();
        System.out.println("Telefone: ");
        String telefone = in.nextLine();
        System.out.println("Celular: ");
        String cel = in.nextLine();
        System.out.println("E-mail: ");
        String email = in.nextLine();
        System.out.println("Endereço: ");
        String endereco = in.nextLine();
        System.out.println("Equipamento: ");
        String equipamento = in.nextLine();
        System.out.println("Data: ");
        LocalDate data = LocalDate.parse(in.nextLine());
        System.out.println("Garantia: ");
        String garantia = in.nextLine();
        System.out.println("Serviço: ");
        String servico = in.nextLine();
        return new Pessoa(nome, sobrenome, telefone, cel, email, endereco, equipamento, data, garantia, servico);
    }

    private OpcaoFiltrada mostrarOpcoesFiltro() {
        System.out.println("1. Procurar por nome");
        System.out.println("2. Procurar por sobrenome");
        System.out.println();
        String escolha;
        do {
            escolha = in.nextLine();
            switch (escolha) {
                case "1": return OpcaoFiltrada.NOME;
                case "2": return OpcaoFiltrada.SOBRENOME;
                default: System.out.print("Escolha uma opção válida");
            }
        } while (!escolha.equals("1") && !escolha.equals("2"));
        return null; //should never reach here
    }
}