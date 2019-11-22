package mysql.ii;

public class Tarefa {
    private String assunto;
    private boolean finalizada;
    private int id;
    
    //Assunto
    public void setAssunto(String assunto){
        this.assunto = assunto;
    }
    
    public String getAssunto(){
        return assunto;
    }
    
    //Finalizada
    public void setFinalizada(boolean finalizada){
        this.finalizada = finalizada;
    }
    
    public boolean getFinalizada(){
        return finalizada;
    }
   
}
