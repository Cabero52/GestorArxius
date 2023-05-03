package domain.classes.exceptions;

public class DocumentNoExisteix extends Exception{
    String auxA;
    String auxT;
    public DocumentNoExisteix(String autor,String titol) {
        super("El document amb autor: " +autor+ "amb titol: "+ titol + " NO EXISTEIX");
        auxA = autor;
        auxT = titol;
    }

    public String toString() {
        return "El document amb autor: " + auxA + " i titol: "+ auxT + " NO EXISTEIX";
    }
}
