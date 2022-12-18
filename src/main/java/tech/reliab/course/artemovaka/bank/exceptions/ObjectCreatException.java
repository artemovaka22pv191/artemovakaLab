package tech.reliab.course.artemovaka.bank.exceptions;

public class ObjectCreatException extends Exception {
    public ObjectCreatException(String obj1, String obj2) {
        super("Нельзя создавать объект " + obj1 + " без объекта " + obj2);
    }
}
