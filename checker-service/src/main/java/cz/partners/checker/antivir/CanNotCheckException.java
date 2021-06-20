package cz.partners.checker.antivir;

public class CanNotCheckException extends Exception {
    public CanNotCheckException(Exception e) {
        super(e);
    }
}
