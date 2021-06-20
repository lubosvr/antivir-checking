package cz.partners.checker.antivir;

public interface Antivir {

    boolean check(byte[] content) throws CanNotCheckException;
}
