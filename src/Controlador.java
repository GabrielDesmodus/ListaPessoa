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
        ATUALIZARCLIENTE,//AÇÃO DE ATUALIZAR CLIENTE ADICIONADA
        DELETECLIENTE,//DELETAR CLIENTE
        PROCURARCLIENTES,
        MOSTRARCLIENTES,
        SAIR
    }

    private enum OpcaoFiltrada {
    	NOMEANDSOBRENOME,
    	NOME,
    	SOBRENOME,
    	TELEFONE,
    	CEL,
    	EMAIL,
    	ENDERECO
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

    private List<Pessoa> buscarPessoas(String busca, OpcaoFiltrada filter) {
        switch (filter) {
            case NOME: return pessoas.stream().filter(person -> person.getNome().equals(busca)).collect(Collectors.toList());
            case SOBRENOME: return pessoas.stream().filter(person -> person.getSobrenome().equals(busca)).collect(Collectors.toList());
            case TELEFONE: return pessoas.stream().filter(person -> person.getTelefone().equals(busca)).collect(Collectors.toList());
            case CEL: return pessoas.stream().filter(person -> person.getCel().equals(busca)).collect(Collectors.toList());
            case EMAIL: return pessoas.stream().filter(person -> person.getEmail().equals(busca)).collect(Collectors.toList());
            case ENDERECO: return pessoas.stream().filter(person -> person.getEndereco().equals(busca)).collect(Collectors.toList());

            default:
                System.out.println("Opção de filtro inválida");
                return new ArrayList<>();
        }
    }
    
    //FUNÇÃO QUE RETORNA TODOS OS ELEMENTOS DA LISTA QUE TEM O MESMO NOME E SOBRENOME, É UM OVERLOAD DA FUNÇÃO ACIMA
    private List<Pessoa> buscarPessoas(String nome, String sobrenome) {
        return pessoas.stream().filter(person -> (person.getNome().equals(nome)) || person.getSobrenome().equals(sobrenome)).collect(Collectors.toList());
    }
    
    //FUNÇÃO QUE RETORNA A POSIÇÃO DO ELEMENTO NA LISTA QUE TEM O ID DESEJADO, TAMBÉM É UM OVERLOAD
    private int buscarPessoas(int id) {
        for(Pessoa p : pessoas) {
        	if(p.getID()==id) {
        		return pessoas.indexOf(p);
        	}
        }   
        System.out.println("Erro ao ");
        return 0;
        
    }
    
    public void run() {
        while(true) {
            Action acao = mostrarMenu();
            switch (acao) {
                case ADDCLIENTE:
                    Pessoa pessoa = getInfoPessoa();
                    
                    pessoas.add(pessoa);
                    break;
                    
                case ATUALIZARCLIENTE: //AÇÃO PRA CASO PRECISE MODIFICAR AS INFORMAÇÕES DE UM CLIENTE
                	OpcaoFiltrada filtroSelecionadoA = mostrarOpcoesFiltro();
                	List<Pessoa> pessoasFiltradasA;
                	if(filtroSelecionadoA == OpcaoFiltrada.NOMEANDSOBRENOME) {
                		System.out.print("Nome: ");
                        String nome = in.nextLine();
                        System.out.print("Sobrenome: ");
                        String sobrenome = in.nextLine(); 
                        pessoasFiltradasA = buscarPessoas(nome, sobrenome);
                	}else {
                		System.out.print("Busca: ");
                		String busca = in.nextLine();
                		pessoasFiltradasA = buscarPessoas(busca, filtroSelecionadoA);
                	}
                	
                     // POR ENQUANTO EU SÓ FIZ A OPÇÃO PRA PROCURAR POR NOME E SOBRENOME
                    if (pessoasFiltradasA.size() == 0) { // SE N TIVER NINGUÉM COM O NOME
                        System.out.println("Sem resultados"); 
                    }else if(pessoasFiltradasA.size() == 1){ //SE SÓ TIVER UMA PESSOA COM O NOME JÁ FAZ O BAGULHO TODO DIRETO
                    	System.out.println("Tem certeza que quer atualizar?");
                    	System.out.println("1. Sim");
                        System.out.println("2. Não");
                        String escolha;
                        do {
                            escolha = in.nextLine();
                            switch (escolha) {
                                case "1": 
                                	Pessoa pessoaA = getInfoPessoa(); //PEDE A INFO PRA ATUALIZAR
                                	Pessoa p = pessoasFiltradasA.get(0); //PEGA O PRIMEIRO(E ÚNICO) FILTRADO PRA COMPARAR NA LISTA DE CLIENTES
                                    pessoas.set(buscarPessoas(p.getID()), pessoaA); //ENCONTRA NA LISTA DE CLIENTES A POSIÇÃO DO ELEMENTO COM ID IGUAL E FAZ A MODIFICAÇÃO COM A INFO DADA
                                    break;
                                
                                case "2": 
                                	break;
                                default: System.out.print("Escolha uma opção válida");
                            }
                        } while (!escolha.equals("1") && !escolha.equals("2"));
                        
                    }else if(pessoasFiltradasA.size() > 1){ //SE TIVER MAIS DE UMA PESSOA COM MESMO NOME E SOBRENOME
                    	System.out.println("Existem mais de uma pessoa com com a mesma informação ");
                    	int i=0;
                    	for(Pessoa p : pessoasFiltradasA) {
                    		i++;
                    		System.out.println("\n"+i + " "+ p.getServico()); //RODA O FOR PRA MOSTRAR TODAS AS PESSOAS FILTRADAS E ALGUMA INFO PRA DIFERENCIAR ELAS
                    	}
                    	System.out.println("Tem certeza que quer atualizar?");
                    	System.out.println("1. Sim");
                        System.out.println("2. Não");
                        String escolha;
                        do {
                            escolha = in.nextLine();
                            switch (escolha) {
                                case "1": 
                                	System.out.println("Escolha qual pessoa você deseja atualizar as informações");
                                    int escolha2;
                                    escolha2 = in.nextInt();
                                    Pessoa pessoaA = getInfoPessoa();
                                	Pessoa p = pessoasFiltradasA.get(escolha2);
                                    pessoas.set(buscarPessoas(p.getID()), pessoaA);
                                
                                case "2": 
                                	break;
                                default: System.out.print("Escolha uma opção válida");
                            }
                        } while (!escolha.equals("1") && !escolha.equals("2"));   
                    }
                	break;
                	
                case DELETECLIENTE: //NOVA AÇÃO
                	OpcaoFiltrada filtroSelecionadoC = mostrarOpcoesFiltro();
                	List<Pessoa> pessoasFiltradasC;
                	if(filtroSelecionadoC == OpcaoFiltrada.NOMEANDSOBRENOME) {
                		System.out.print("Nome: ");
                        String nome = in.nextLine();
                        System.out.print("Sobrenome: ");
                        String sobrenome = in.nextLine(); 
                        pessoasFiltradasC = buscarPessoas(nome, sobrenome);
                	}else {
                		System.out.print("Busca: ");
                		String busca = in.nextLine();
                		pessoasFiltradasC = buscarPessoas(busca, filtroSelecionadoC);
                	}
                	
                    if (pessoasFiltradasC.size() == 0) {
                        System.out.println("Sem resultados"); 
                    }else if(pessoasFiltradasC.size() == 1){ 
                    	System.out.println("Tem certeza que quer deletar esse cliente?");
                    	System.out.println("1. Sim");
                        System.out.println("2. Não");
                        String escolha;
                        do {
                            escolha = in.nextLine();
                            switch (escolha) {
                                case "1": 
                                	Pessoa p = pessoasFiltradasC.get(0);
                                    pessoas.remove(buscarPessoas(p.getID())); 
                                    break;
                                
                                case "2": 
                                	break;
                                default: System.out.print("Escolha uma opção válida");
                            }
                        } while (!escolha.equals("1") && !escolha.equals("2"));
                        
                    }else if(pessoasFiltradasC.size() > 1){ 
                    	System.out.println("Existem mais de uma pessoa com a mesma informação: ");
                    	int i=0;
                    	for(Pessoa p : pessoasFiltradasC) {
                    		i++;
                    		System.out.println("\n"+i + " "+ p.getServico()); //RODA O FOR PRA MOSTRAR TODAS AS PESSOAS FILTRADAS E ALGUMA INFO PRA DIFERENCIAR ELAS
                    	}
                    	System.out.println("Tem certeza que quer deletar?");
                    	System.out.println("1. Sim");
                        System.out.println("2. Não");
                        String escolha;
                        do {
                            escolha = in.nextLine();
                            switch (escolha) {
                                case "1": 
                                	System.out.println("Escolha qual pessoa você deseja deletar");
                                    int escolha2;
                                    escolha2 = in.nextInt();
                                	Pessoa p = pessoasFiltradasC.get(escolha2);
                                    pessoas.remove(buscarPessoas(p.getID()));
                                
                                case "2": 
                                	break;
                                default: System.out.print("Escolha uma opção válida");
                            }
                        } while (!escolha.equals("1") && !escolha.equals("2"));
                    }
                	break;
                	
                case PROCURARCLIENTES:
                    OpcaoFiltrada filtroSelecionadoB = mostrarOpcoesFiltro();
                    System.out.print("Busca: ");
                    String busca = in.nextLine();
                    List<Pessoa> pessoaFiltradaB = buscarPessoas(busca, filtroSelecionadoB);
                    if (pessoaFiltradaB.size() == 0) {
                        System.out.println("Sem resultados");
                    } else {
                        for (Pessoa p : pessoaFiltradaB)
                            System.out.println(p);
                    }
                    break;
                    
                case MOSTRARCLIENTES:
                    System.out.println(this.pessoas);
                    System.out.println();
                    break;
                    
                case SAIR:
                	arquivo.saveAll(pessoas);//AGORA ELE SÓ SALVA NO FINAL
                    System.out.println("Exiting Program");
                    System.exit(0);
                    break;
            }
        }
    }

    private Action mostrarMenu() {
        System.out.println("1. Adicionar cliente");
        System.out.println("2. Pesquisar cliente"); 
        System.out.println("3. Atualizar cliente"); //MENU MODIFICADO
        System.out.println("4. Mostrar todos os clientes");
        System.out.println("5. Fechar programa");
        String escolha;
        do {
            escolha = in.nextLine();
            switch (escolha) {
                case "1": return Action.ADDCLIENTE;
                case "2": return Action.PROCURARCLIENTES;
                case "3": return Action.ATUALIZARCLIENTE; //OPÇÃO ADICIONADA
                case "4": return Action.MOSTRARCLIENTES;
                case "5": return Action.SAIR;
                default: System.out.println("Digite um número válido");
            }
        } while (!escolha.equals("5"));
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
        System.out.println("3. Procurar por telefone");
        System.out.println("4. Procurar por cel");
        System.out.println("5. Procurar por email");
        System.out.println("6. Procurar por endereço");
        String escolha;
        do {
            escolha = in.nextLine();
            switch (escolha) {
                case "1": return OpcaoFiltrada.NOME;
                case "2": return OpcaoFiltrada.SOBRENOME;
                case "4": return OpcaoFiltrada.TELEFONE;//OPÇÃO ADICIONADA
                case "5": return OpcaoFiltrada.CEL;//OPÇÃO ADICIONADA
                case "6": return OpcaoFiltrada.EMAIL;//OPÇÃO ADICIONADA
                case "7": return OpcaoFiltrada.ENDERECO;//OPÇÃO ADICIONADA
                default: System.out.print("Escolha uma opção válida");
            }
        } while (!escolha.equals("1") && !escolha.equals("2"));
        return null; //should never reach here
    }
}