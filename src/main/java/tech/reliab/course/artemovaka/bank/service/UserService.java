package tech.reliab.course.artemovaka.bank.service;

import tech.reliab.course.artemovaka.bank.entity.User;

public interface UserService {

    /**
     * Создание пользователя
     */
    User create(User user);

    /**
     * Регистрация места работы. При этом подсчитывается кредитный рейтинг пользователя.
     */
    void jobRegistration(User user, String addressJob, double monthIncome);

    /**
     * Подсчет кредитного рейтинга пользователя (генерируется рандомно
     * исходя из ежемесячного дохода, от меньше 1 000 – 100, от 1 000 до 2 000 – 200 и т.д. вплоть до 10 000 )
     */
    void calculateRating(User user);
}
