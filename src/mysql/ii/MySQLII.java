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

                    pegarId(email, senha);

                    usuarioLogado.setEmail(email);
                    usuarioLogado.setSenha(senha);

                    inserirUsuario(email, senha);

                    System.out.println("Aperte enter para continuar");
                    keep = sc.nextLine();
                    break;

                case "2":
                    System.out.println("");
                    System.out.println("||===============||");
                    System.out.println("|| MENU DE LOGIN ||");
                    System.out.println("||===============||");
                    System.out.print("Insira o email: ");
                    email = sc.nextLine();

                    System.out.print("Insira a senha: ");
                    senha = sc.nextLine();

                    pegarId(email, senha);
                    logarUsuario(email, senha);
                    break;

                case "3":
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
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("insert into lista_de_tarefas.tb_usuario (des_email, enc_senha) values (?, ?)");

            stmnt.setString(1, email);
            stmnt.setString(2, senha);

            linhas = stmnt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Usuário cadastrado com sucesso!");
            } else {
                System.out.println("Usuário não cadastrado com sucesso!");
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    static void logarUsuario(String email, String senha) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_usuario where des_email = ? and enc_senha = ?");

            stmnt.setString(1, email);
            stmnt.setString(2, senha);

            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                String emailc = rs.getString("des_email");
                String senhac = rs.getString("enc_senha");

                if (emailc.equals(email) && senhac.equals(senha)) {
                    System.out.println("Usuário logado com sucesso");
                    
                    System.out.println("Aperte enter para continuar");
                    keep = sc.nextLine();
                    
                    boolean entrar = true;
                    
                    segundoMenu(entrar);
                } else {
                    System.out.println("Email/senha incorretos");
                }
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    static void listarTarefas(int id) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_tarefas where id_usuario = ?");
            
            stmnt.setInt(1, id);

            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int idTarefa = rs.getInt("id_tarefa");
                String assunto = rs.getString("des_assunto");
                boolean finalizada = rs.getBoolean("sta_finalizada");

                System.out.println("Tarefa: " + idTarefa);
                System.out.println("Assunto: " + assunto);
                System.out.println("Finalizada: " + finalizada);
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    static void listarTarefasFinalizadas(int id) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_tarefas where sta_finalizada = 1 and id_usuario = ?");
            
            stmnt.setInt(1, id);

            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int idTarefa = rs.getInt("id_tarefa");
                String assunto = rs.getString("des_assunto");
                boolean finalizada = rs.getBoolean("sta_finalizada");

                System.out.println("Tarefa: " + idTarefa);
                System.out.println("Assunto: " + assunto);
                System.out.println("Finalizada: " + finalizada);
            }
        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    static void listarTarefasNaoFinalizadas(int id) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_tarefas where sta_finalizada = 0 and id_usuario = ?");
            
            stmnt.setInt(1, id);

            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int idTarefa = rs.getInt("id_tarefa");
                String assunto = rs.getString("des_assunto");
                boolean finalizada = rs.getBoolean("sta_finalizada");

                System.out.println("Tarefa: " + idTarefa);
                System.out.println("Assunto: " + assunto);
                System.out.println("Finalizada: " + finalizada);
            }
        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    static void adicionarTarefa(String assunto, int id) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("insert into lista_de_tarefas.tb_tarefas (des_assunto, id_usuario) values (?, ?)");

            stmnt.setString(1, assunto);
            stmnt.setInt(2, id);

            linhas = stmnt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Tarefa adicionada com sucesso!");
            } else {
                System.out.println("Tarefa não adicionada!");
            }
        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    static void finalizarTarefa(int idTarefa) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("update lista_de_tarefas.tb_tarefas sta_finalizada set sta_finalizada = 1 where id_tarefa = ?");

            stmnt.setInt(1, idTarefa);

            linhas = stmnt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Tarefa finalizada com sucesso!");
            } else {
                System.out.println("Tarefa não finalizada.");
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    static void removerTarefa(int idTarefa) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("delete from lista_de_tarefas.tb_tarefas where id_tarefa = ?");

            stmnt.setInt(1, idTarefa);

            linhas = stmnt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Tarefa removida com sucesso!");
            } else {
                System.out.println("Tarefa não removida. Algum erro aconteceu!");
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    static void pegarId(String email, String senha) {
        try {

            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select id_usuario from lista_de_tarefas.tb_usuario where des_email = ? and enc_senha = ?");

            stmnt.setString(1, email);
            stmnt.setString(2, senha);

            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_usuario");

                usuarioLogado.setId(id);
            }
        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }

    }

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
            System.out.print("Selecione uma opção:");
            op = sc.nextLine();

            switch (op) {
                case "1":
                    System.out.println("");
                    System.out.println("||================||");
                    System.out.println("|| LISTAR TAREFAS ||");
                    System.out.println("||================||");
                    int id = usuarioLogado.getId();
                    listarTarefas(id);
                    break;

                case "2":
                    System.out.println("");
                    System.out.println("||============================||");
                    System.out.println("|| LISTAR TAREFAS FINALIZADAS ||");
                    System.out.println("||============================||");
                    id = usuarioLogado.getId();
                    listarTarefasFinalizadas(id);
                    break;

                case "3":
                    System.out.println("");
                    System.out.println("||================================||");
                    System.out.println("|| LISTAR TAREFAS NÃO FINALIZADAS ||");
                    System.out.println("||================================||");
                    id = usuarioLogado.getId();
                    listarTarefasNaoFinalizadas(id);
                    break;

                case "4":
                    System.out.println("");
                    System.out.println("||======================||");
                    System.out.println("|| ADICIONAR UMA TAREFA ||");
                    System.out.println("||======================||");
                    System.out.print("Insira o assunto da tarefa: ");
                    assunto = sc.nextLine();

                    Tarefa tarefa = new Tarefa();
                    tarefa.setAssunto(assunto);
                    tarefa.setFinalizada(finalizada);

                    if (tarefas.add(tarefa)) {
                        id = usuarioLogado.getId();
                        adicionarTarefa(assunto, id);
                    } else {
                        System.out.println("Tarefa não adicionada");
                    }

                    u.setTarefas(tarefas);

                    break;

                case "5":
                    System.out.println("");
                    System.out.println("||======================||");
                    System.out.println("|| FINALIZAR UMA TAREFA ||");
                    System.out.println("||======================||");

                    id = usuarioLogado.getId();
                    listarTarefas(id);

                    System.out.print("Insira o identificador da tarefa para finalizar: ");
                    idTarefa = sc.nextInt();

                    finalizarTarefa(idTarefa);
                    break;

                case "6":
                    System.out.println("");
                    System.out.println("||====================||");
                    System.out.println("|| REMOVER UMA TAREFA ||");
                    System.out.println("||====================||");
                    
                    id = usuarioLogado.getId();
                    listarTarefas(id);

                    System.out.println("Insira o identificador da tarefa para remover: ");
                    idTarefa = sc.nextInt();

                    removerTarefa(idTarefa);
                    break;

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
