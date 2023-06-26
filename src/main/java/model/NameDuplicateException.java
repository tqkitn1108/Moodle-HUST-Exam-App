package model;

public class NameDuplicateException extends Exception{
    public NameDuplicateException(String msg) {super(msg);}

    public NameDuplicateException(String msg, Throwable cause) {super(msg,cause);}
}
