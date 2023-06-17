package model;

public class ReadFileException extends Exception{
    public ReadFileException(String msg) {
        super(msg);
    }
    public ReadFileException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
