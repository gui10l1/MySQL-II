package mysql.ii;

import java.util.ArrayList;

public class Usuario {
    private String email;
    private String senha;
    private int id;
    private ArrayList<Tarefa> tarefas;
    
    //Email
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    //Senha
    public void setSenha(String senha){
        this.senha = senha;
    }
    
    public String getSenha(){
        return senha;
    }
    
    //ID
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    //ArrayList
    public void setTarefas(ArrayList<Tarefa> tarefas){
        this.tarefas = tarefas;
    }
    
    public ArrayList<Tarefa> getTarefas(){
        return tarefas;
    }
}
