package tech.reliab.course.artemovaka.bank.utils;

public enum Status {
    NOT_WORK("Не работает"),
    WORK("Работает"),
    NOT_MONEY("Нет денег");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

