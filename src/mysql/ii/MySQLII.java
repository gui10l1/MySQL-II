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

    public static void main(String[] args) {
        //Classes

        //Variáveis
        String email;
        String senha;
        String op;
        String keep;

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
                    System.out.print("Insira o novo email: ");
                    email = sc.nextLine();

                    System.out.print("Insira a nova senha: ");
                    senha = sc.nextLine();

                    pegarId(email, senha);

                    usuarioLogado.setEmail(email);
                    usuarioLogado.setSenha(senha);

                    inserirUsuario(email, senha);
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

                    logarUsuario(email, senha);
                    break;

                case "3":
                    running = false;
                    System.out.println("Finalizando o programa...");
                    break;
            }

            System.out.println("Aperte enter para continuar");
            keep = sc.nextLine();
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

            linhas = stmnt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Usuário logado com sucesso!");

            } else {
                System.out.println("Usuário e/ou senha incorretos!");
            }

        } catch (Exception e) {
            System.out.println("Algum erro aconteceu!");
        }
    }

    static void listarTarefas() {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_tarefas");

            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_tarefa");
                String assunto = rs.getString("des_assunto");
                boolean finalizada = rs.getBoolean("sta_finalizada");

                if (finalizada) {
                    System.out.print("finalizada");
                } else {
                    System.out.print("não finalizada");
                }

                System.out.println("Tarefa: " + id);
                System.out.println("Assunto: " + assunto);
                System.out.println("Finalizada: " + finalizada);
            }

        } catch (Exception e) {

        }
    }

    static void listarTarefasFinalizadas() {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_tarefas where sta_finalizada = 1");

            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_tarefa");
                String assunto = rs.getString("des_assunto");
                boolean finalizada = rs.getBoolean("sta_finalizada");

                if (finalizada) {
                    System.out.print("finalizada");
                } else {
                    System.out.print("não finalizada");
                }

                System.out.println("Tarefa: " + id);
                System.out.println("Assunto: " + assunto);
                System.out.println("Finalizada: " + finalizada);
            }
        } catch (Exception e) {

        }
    }

    static void listarTarefasNaoFinalizadas() {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select * from lista_de_tarefas.tb_tarefas where sta_finalizada = 0");

            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_tarefa");
                String assunto = rs.getString("des_assunto");
                boolean finalizada = rs.getBoolean("sta_finalizada");

                if (finalizada) {
                    System.out.print("finalizada");
                } else {
                    System.out.print("não finalizada");
                }

                System.out.println("Tarefa: " + id);
                System.out.println("Assunto: " + assunto);
                System.out.println("Finalizada: " + finalizada);
            }
        } catch (Exception e) {

        }
    }

    static void adicionarTarefa(String assunto) {
        try {
            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("insert into lista_de_tarefas.tb_tarefas (des_assunto) values (?)");

            stmnt.setString(1, assunto);

            linhas = stmnt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Tarefa adicionada com sucesso!");
            } else {
                System.out.println("Tarefa não adicionada, algum erro aconteceu!");
            }
        } catch (Exception e) {

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
                System.out.println("Tarefa não finalizada. Algum erro aconteceu!");
            }

        } catch (Exception e) {

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

        }
    }

    static void pegarId(String email, String senha) {
        try {

            Driver driver = new Driver();

            DriverManager.registerDriver(driver);

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

            PreparedStatement stmnt = conn.prepareStatement("select id_usuario from tb_usuario where des_email = ? and enc_senha = ?");

            stmnt.setString(1, email);
            stmnt.setString(2, senha);

            ResultSet rs = stmnt.executeQuery();

            int id = rs.getInt("id_usuario");

            usuarioLogado.setId(id);

        } catch (Exception e) {

        }

    }

    static void segundoMenu(boolean entrar) {
        //Classes
        ArrayList<Tarefa> tarefas = new ArrayList();
        Usuario u = new Usuario();

        //Variáveis
        String op;
        String assunto;
        String keep;

        int idTarefa;
        boolean finalizada = false;

        while (entrar) {
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
            op = sc.nextLine();

            switch (op) {
                case "1":
                    System.out.println("");
                    System.out.println("||================||");
                    System.out.println("|| LISTAR TAREFAS ||");
                    System.out.println("||================||");
                    listarTarefas();
                    break;

                case "2":
                    System.out.println("");
                    System.out.println("||============================||");
                    System.out.println("|| LISTAR TAREFAS FINALIZADAS ||");
                    System.out.println("||============================||");
                    listarTarefasFinalizadas();
                    break;

                case "3":
                    System.out.println("");
                    System.out.println("||================================||");
                    System.out.println("|| LISTAR TAREFAS NÃO FINALIZADAS ||");
                    System.out.println("||================================||");
                    listarTarefasNaoFinalizadas();
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
                        System.out.println("Tarefa asicionada com sucesso!");
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
                    for (int i = 0; i < tarefas.size(); i++) {
                        boolean f = tarefas.get(i).getFinalizada();
                        String nome = tarefas.get(i).getAssunto();
                        Tarefa n = tarefas.get(i);

                        if (f) {
                            System.out.print("finalizada");
                        } else {
                            System.out.print("não finalizada");
                        }

                        System.out.println("Tarefa[" + n + "]: " + nome + " " + f);
                    }

                    System.out.println("Insira o identificador da tarefa para finalizar: ");
                    idTarefa = sc.nextInt();

                    finalizarTarefa(idTarefa);
                    break;

                case "6":
                    System.out.println("");
                    System.out.println("||====================||");
                    System.out.println("|| REMOVER UMA TAREFA ||");
                    System.out.println("||====================||");
                    for (int i = 0; i < tarefas.size(); i++) {
                        boolean f = tarefas.get(i).getFinalizada();
                        String nome = tarefas.get(i).getAssunto();
                        Tarefa n = tarefas.get(i);

                        if (f) {
                            System.out.print("finalizada");
                        } else {
                            System.out.print("não finalizada");
                        }

                        System.out.println("Tarefa[" + n + "]: " + nome + " " + f);
                    }

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
