package domain.classes.exceptions;

public class NullException extends Exception {

    public NullException() {
        super("L'atribut esta buit!");
    }

    public String toString() {
        return "L'atribut esta buit!";
    }
}