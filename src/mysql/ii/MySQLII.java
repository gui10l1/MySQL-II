package mysql.ii;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class MySQLII {

    static Scanner sc = new Scanner(System.in);
    static int linhas;
    static Usuario usuarioLogado = new Usuario();
    static String keep;

    public static void main(String[] args) {
        //Classes

        //Variáveis
        String email;
        String senha;
        String op;

        boolean running = true;

        while (running) {
            System.out.println("");
            System.out.println("||================||");
            System.out.println("|| MENU PRINCIPAL ||");
            System.out.println("||================||");
            System.out.println("[1] Fazer cadastro");
            System.out.println("[2] Fazer login");
            System.out.println("[3] Finalizar o programa");
            System.out.print("Selecione uma opção: ");
            op = sc.nextLine();

            switch (op) {
                case "1":
                    System.out.println("");
                    System.out.println("||==================||");
                    System.out.println("|| MENU DE CADASTRO ||");
                    System.out.println("||==================||");

                    //VEIRIFCAR SE O EMAIL CONTÉM @
                    boolean verificarEmail = true;
                    do {
                        System.out.print("Insira o novo email: ");
                        email = sc.nextLine();
                        if (email.contains("@")) {
                            verificarEmail = false;
                        } else {
                            System.out.println("o email deve conter @");
                        }
                    } while (verificarEmail);

                    //VERIFICAR SE A SENHA CONTÉM MAIS DE 6 OU MENOS DE 15 CARACTERES
                    boolean verificarSenha;
                    do {
                        System.out.print("Insira a nova senha: ");
                        senha = sc.nextLine();
                        if (senha.length() < 6 || senha.length() > 15) {
                            System.out.println("A senha deve conter no mínimo 6 ou no máximo 16 caracteres");
                            verificarSenha = false;
                        } else {
                            verificarSenha = true;
                        }
                    } while (!verificarSenha);

                    //CADASTRAR O USUARIO NO BANCO
                    pegarId(email, senha);

                    usuarioLogado.setEmail(email);
                    usuarioLogado.setSenha(senha);

                    inserirUsuario(email, senha);

                    //CONTINUAR A EXECUÇÃO DO SISTEMA
                    System.out.println("Aperte enter para continuar");
                    keep = sc.nextLine();
                    break;

                case "2":
                    System.out.println("");
                    System.out.println("||===============||");
                    System.out.println("|| MENU DE LOGIN ||");
                    System.out.println("||===============||");
                    //INSERIR EMAIL
                    System.out.print("Insira o email: ");
                    email = sc.nextLine();

                    //INSERIR SENHA
                    System.out.print("Insira a senha: ");
                    senha = sc.nextLine();

                    //PEGAR O ID DO USUÁRIO PARA DEMAIS MÉTODOS DO SISTEMA
                    pegarId(email, senha);
                    
                    //LOGAR O USUÁRIO POR MEIO DO BANCO DE DADOS
                    logarUsuario(email, senha);
                    break;

                case "3":
                    //FINALIZAR A EXECUÇÃO DO SISTEMA
                    running = false;
                    System.out.println("Finalizando o programa...");
                    System.out.println("Aperte enter para continuar");
                    keep = sc.nextLine();
                    break;
            }
        }

    }

    static void inserirUsuario(String email, String senha) {
        try {
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\

            //PREPARANDO A QUERY PARA SER EXECUTADA
            PreparedStatement stmnt = conn.prepareStatement("insert into lista_de_tarefas.tb_usuario (des_email, enc_senha) values (?, ?)");

            //INSERINDO OS DADOS QUE FALTAM NA QUERY
            stmnt.setString(1, email);
            stmnt.setString(2, senha);

            //EXECUTANDO A QUERY POR MEIO DO EXECUTEUPDATE();
            linhas = stmnt.executeUpdate();

            //VERIFICAÇÃO
            if (linhas > 0) {
                System.out.println("Usuário cadastrado com sucesso!");
            } else {
                System.out.println("Usuário não cadastrado com sucesso!");
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    //MÉTODO PARA LOGAR O USUÁRIO
    static void logarUsuario(String email, String senha) {
        try {
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\

            //PREPARANDO A QUERY PARA SER EXECUTADA
            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_usuario where des_email = ? and enc_senha = ?");

            //INSERINDO OS DADOS QUE FALTAM NA QUERY
            stmnt.setString(1, email);
            stmnt.setString(2, senha);

            //EXECUTANDO A QUERY POR MEIO DO RESULTSET();
            ResultSet rs = stmnt.executeQuery();

            //LAÇO DE REPETIÇÃO USADO PARA VERIFICAR SE O USUÁRIO É ELE MESMO
            while (rs.next()) {
                //VARIÁVEIS QUE SERÃO UTILIZADAS
                String emailc = rs.getString("des_email");
                String senhac = rs.getString("enc_senha");

                //VERIFICAÇÃO
                if (emailc.equals(email) && senhac.equals(senha)) {
                    System.out.println("Usuário logado com sucesso");
                    
                    System.out.println("Aperte enter para continuar");
                    keep = sc.nextLine();
                    
                    //VARIÁVEL REQUERIDA PARA ATIVAR O SEGUNDO MENU
                    boolean entrar = true;
                    
                    //MÉTODO PARA ATIVAR O SEGUNDO MENU
                    segundoMenu(entrar);
                } else {
                    System.out.println("Email/senha incorretos");
                }
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    //MÉTODO PARA LISTAR TAREFAS
    static void listarTarefas(int id) {
        try {
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            
            //PREPARANDO A QUERY PARA SER EXECUTADA
            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_tarefas where id_usuario = ?");
            
            //INSERINDO OS DADOS QUE FALTAM NA QUERY
            stmnt.setInt(1, id);

            //EXECUTANDO A QUERY POR MEIO DO RESULTSET();
            ResultSet rs = stmnt.executeQuery();

            //LAÇO DE REPETIÇÃO USADO PARA LISTAR AS TAREFAS (QUERY)
            while (rs.next()) {
                //VARIÁVEIS QUE SERÃO UTILIZADAS
                int idTarefa = rs.getInt("id_tarefa");
                String assunto = rs.getString("des_assunto");
                boolean finalizada = rs.getBoolean("sta_finalizada");

                //SAÍDA DE DADOS
                System.out.println("Tarefa: " + idTarefa);
                System.out.println("Assunto: " + assunto);
                System.out.println("Finalizada: " + finalizada);
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    //MÉTODO PARA LISTAR TAREFAS FINALIZADAS
    static void listarTarefasFinalizadas(int id) {
        try {
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            
            //PREPARANDO A QUERY PARA SER EXECUTADA
            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_tarefas where sta_finalizada = 1 and id_usuario = ?");
            
            //INSERINDO OS DADOS QUE FALTAM NA QUERY
            stmnt.setInt(1, id);

            //EXECUTANDO A QUERY POR MEIO DO RESULTSET();
            ResultSet rs = stmnt.executeQuery();

            //LAÇO DE REPETIÇÃO USADO PARA LISTAR AS TAREFAS QUE FORAM FINALIZADAS (QUERY)
            while (rs.next()) {
                //VARIÁVEIS QUE SERÃO UTILIZADAS
                int idTarefa = rs.getInt("id_tarefa");
                String assunto = rs.getString("des_assunto");
                boolean finalizada = rs.getBoolean("sta_finalizada");

                //SAÍDA DE DADOS
                System.out.println("Tarefa: " + idTarefa);
                System.out.println("Assunto: " + assunto);
                System.out.println("Finalizada: " + finalizada);
            }
        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    //MÉTODO PARA LISTAR TAREFAS NÃO FINALIZADAS
    static void listarTarefasNaoFinalizadas(int id) {
        try {
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\

            //PREPARANDO A QUERY PARA SER EXECUTADA
            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_tarefas where sta_finalizada = 0 and id_usuario = ?");
            
            //INSERINDO OS DADOS QUE FALTAM NA QUERY
            stmnt.setInt(1, id);

            //EXECUTANDO A QUERY POR MEIO DO RESULTSET();
            ResultSet rs = stmnt.executeQuery();

            //LAÇO DE REPETIÇÃO USADO PARA LISTAR AS TAREFAS QUE NÃO FORAM FINALIZADAS (QUERY)
            while (rs.next()) {
                //VARIÁVEIS QUE SERÃO UTILIZADAS
                int idTarefa = rs.getInt("id_tarefa");
                String assunto = rs.getString("des_assunto");
                boolean finalizada = rs.getBoolean("sta_finalizada");

                //SAÍDA DE DADOS
                System.out.println("Tarefa: " + idTarefa);
                System.out.println("Assunto: " + assunto);
                System.out.println("Finalizada: " + finalizada);
            }
        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    //MÉTODO PARA ADICIONAR UMA TAREFA
    static void adicionarTarefa(String assunto, int id) {
        try {
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            
            //PREPARANDO A QUERY PARA SER EXECUTADA
            PreparedStatement stmnt = conn.prepareStatement("insert into lista_de_tarefas.tb_tarefas (des_assunto, id_usuario) values (?, ?)");

            //INSERINDO OS DADOS QUE FALTAM NA QUERY
            stmnt.setString(1, assunto);
            stmnt.setInt(2, id);

            //EXECUTANDO A QUERY POR MEIO DO EXECUTEUPDATE();
            linhas = stmnt.executeUpdate();

            //VERIFICAÇÃO
            if (linhas > 0) {
                System.out.println("Tarefa adicionada com sucesso!");
            } else {
                System.out.println("Tarefa não adicionada!");
            }
            
        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    //MÉTODO PARA FINALIZAR UMA TAREFA
    static void finalizarTarefa(int idTarefa) {
        try {
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\

            //PREPARANDO A QUERY PARA SER EXECUTADA
            PreparedStatement stmnt = conn.prepareStatement("update lista_de_tarefas.tb_tarefas sta_finalizada set sta_finalizada = 1 where id_tarefa = ?");

            //INSERINDO OS DADOS QUE FALTAM NA QUERY
            stmnt.setInt(1, idTarefa);

            //EXECUTANDO A QUERY POR MEIO DO EXECUTEUPDATE();
            linhas = stmnt.executeUpdate();

            //VERIFICAÇÃO
            if (linhas > 0) {
                System.out.println("Tarefa finalizada com sucesso!");
            } else {
                System.out.println("Tarefa não finalizada.");
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    //MÉTODO PARA REMOVER UMA TAREFA
    static void removerTarefa(int idTarefa) {
        try {
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\

            //PREPARANDO A QUERY PARA SER EXECUTADA
            PreparedStatement stmnt = conn.prepareStatement("delete from lista_de_tarefas.tb_tarefas where id_tarefa = ?");

            //INSERINDO OS DADOS QUE FALTAM NA QUERY
            stmnt.setInt(1, idTarefa);

            //EXECUTANDO A QUERY POR MEIO DO EXECUTEUPDATE();
            linhas = stmnt.executeUpdate();

            //VERIFICAÇÃO
            if (linhas > 0) {
                System.out.println("Tarefa removida com sucesso!");
            } else {
                System.out.println("Tarefa não removida. Algum erro aconteceu!");
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    //MÉTODO PARA PEGAR O ID DO USUÁRIO PARA DEMAIS FUNCIONALIDADES
    static void pegarId(String email, String senha) {
        try {
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            //*****************************************************CONEXÃO COM O BANCO DA DADOS****************************************\\

            //PREPARANDO A QUERY PARA SER EXECUTADA
            PreparedStatement stmnt = conn.prepareStatement("select id_usuario from lista_de_tarefas.tb_usuario where des_email = ? and enc_senha = ?");

            //INSERINDO OS DADOS QUE FALTAM NA QUERY
            stmnt.setString(1, email);
            stmnt.setString(2, senha);

            //EXECUTANDO A QUERY POR MEIO DO RESULTSET();
            ResultSet rs = stmnt.executeQuery();

            //SELECIONANDO A LINHA AFETADA PELA EXECUÇÃO
            while (rs.next()) {
                //DECLARANDO A VARIÁVEL QUE IRÁ SER USADA EM UM OBJETO
                int id = rs.getInt("id_usuario");

                //MÉTODO PRIVADO PARA INSERIR O ID CORRESPONDIDO AO USUÁRIO EM QUESTÃO EM UM OBJETO
                usuarioLogado.setId(id);
            }
        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }

    }

    //MÉTODO PARA ATIVAR O SEGUNDO MENU
    static void segundoMenu(boolean entrar) {
        //Classes
        ArrayList<Tarefa> tarefas = new ArrayList();
        Usuario u = new Usuario();

        //Variáveis
        String op;
        String assunto;

        int idTarefa;
        boolean finalizada = false;

        while (entrar) {
            System.out.println("");
            System.out.println("||=================||");
            System.out.println("|| MENU DO USUÁRIO ||");
            System.out.println("||=================||");
            System.out.println("[1] Listar tarefas");
            System.out.println("[2] Listar tarefas finalizadas");
            System.out.println("[3] Listar tarefas não finalizadas");
            System.out.println("[4] Adicionar uma tarefa");
            System.out.println("[5] Finalizar tarefas");
            System.out.println("[6] Remover uma tarefa");
            System.out.println("[7] Fazer logout");
            
            //ENTRADA DE DADOS
            System.out.print("Selecione uma opção:");
            op = sc.nextLine();

            switch (op) {
                //EXECUÇÃO --> LISTAR TAREFAS
                case "1":
                    System.out.println("");
                    System.out.println("||================||");
                    System.out.println("|| LISTAR TAREFAS ||");
                    System.out.println("||================||");
                    
                    //PEGAR O ID DO USUARIO PARA A FUNCIONALIDADE DO PRÓXIMO MÉTODO 
                    int id = usuarioLogado.getId();
                    
                    //MÉTODO PARA LISTAR TAREFAS
                    listarTarefas(id);
                    break;

                //EXECUÇÃO --> LISTAR TAREFAS FINALIZADAS
                case "2":
                    System.out.println("");
                    System.out.println("||============================||");
                    System.out.println("|| LISTAR TAREFAS FINALIZADAS ||");
                    System.out.println("||============================||");
                    
                    //PEGAR O ID DO USUARIO PARA A FUNCIONALIDADE DO PRÓXIMO MÉTODO 
                    id = usuarioLogado.getId();
                    
                    //MÉTODO PARA LISTAR TAREFAS FINALIZADAS
                    listarTarefasFinalizadas(id);
                    break;

                // EXECUÇÃO --> LISTAR TAREFAS NÃO FINALIZADAS
                case "3":
                    System.out.println("");
                    System.out.println("||================================||");
                    System.out.println("|| LISTAR TAREFAS NÃO FINALIZADAS ||");
                    System.out.println("||================================||");
                    
                    //PEGAR O ID DO USUARIO PARA A FUNCIONALIDADE DO PRÓXIMO MÉTODO 
                    id = usuarioLogado.getId();
                    
                    //MÉTODO PARA LISTAR TAREFAS NÃO FINALIZADAS
                    listarTarefasNaoFinalizadas(id);
                    break;

                //EXECUÇÃO --> ADICIONAR UMA TAREFA    
                case "4":
                    System.out.println("");
                    System.out.println("||======================||");
                    System.out.println("|| ADICIONAR UMA TAREFA ||");
                    System.out.println("||======================||");
                    System.out.print("Insira o assunto da tarefa: ");
                    assunto = sc.nextLine();

                    //INSTANCIAR UM NOVO OBJETO(TAREFA)
                    Tarefa tarefa = new Tarefa();
                    
                    //INSERIR O ASSUNTO NO OBJETO
                    tarefa.setAssunto(assunto);
                    
                    //INSERIR A CHAMADA BOOLEAN NO OBJETO
                    tarefa.setFinalizada(finalizada);

                    //VERIFICAÇÃO SE A TAREFA FOI ADICIONADA
                    if (tarefas.add(tarefa)) {
                        //PEGAR O ID DO USUARIO PARA A FUNCIONALIDADE DO PRÓXIMO MÉTODO 
                        id = usuarioLogado.getId();
                        
                        //MÉTODO PARA ADICIONAR UMA TAREFA
                        adicionarTarefa(assunto, id);
                    } else {
                        System.out.println("Tarefa não adicionada");
                    }

                    //INSERIR O ARRAYLIST DE TAREFAS NO OBJETO USUÁRIO (ASSIM CADA USUÁRIO TEM SUA TAREFA NO ARRAY)
                    u.setTarefas(tarefas);

                    break;

                //EXECUÇÃO --> FINALIZAR UMA TAREFA    
                case "5":
                    System.out.println("");
                    System.out.println("||======================||");
                    System.out.println("|| FINALIZAR UMA TAREFA ||");
                    System.out.println("||======================||");

                    //PEGAR O ID DO USUARIO PARA A FUNCIONALIDADE DO PRÓXIMO MÉTODO 
                    id = usuarioLogado.getId();
                    
                    //LISTAR AS TAREFAS PARA O USUÁRIO DECIDIR QUAL FINALIZA-LA
                    listarTarefas(id);

                    //ENTRADA DE DADOS (ID TAREFA)
                    System.out.print("Insira o identificador da tarefa para finalizar: ");
                    idTarefa = sc.nextInt();

                    //MÉTODO PARA FINALIZADR UMA TAREFA
                    finalizarTarefa(idTarefa);
                    break;

                //EXECUÇÃO --> REMOVER UMA TAREFA
                case "6":
                    System.out.println("");
                    System.out.println("||====================||");
                    System.out.println("|| REMOVER UMA TAREFA ||");
                    System.out.println("||====================||");
                    
                    //PEGAR O ID DO USUARIO PARA A FUNCIONALIDADE DO PRÓXIMO MÉTODO  
                    id = usuarioLogado.getId();
                    
                    //LISTAR AS TAREFAS PARA O USUÁRIO DECIDIR QUAL REMOVER
                    listarTarefas(id);
                    
                    //ENTRADA DE DADOS (ID TAREFA)
                    System.out.println("Insira o identificador da tarefa para remover: ");
                    idTarefa = sc.nextInt();

                    //MÉTODO PARA REMOVER UMA TAREFA
                    removerTarefa(idTarefa);
                    break;

                //EXECUÇÃO --> FAZER LOGOUT
                case "7":
                    entrar = false;
                    System.out.println("Fazendo logout...");
                    break;
            }

            System.out.println("Aperte enter para continuar");
            keep = sc.nextLine();

        }
    }

}
