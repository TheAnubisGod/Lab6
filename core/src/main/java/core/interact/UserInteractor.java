package core.interact;

/**
 * Получение данных от пользователя.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public interface UserInteractor {
    String getData();

    void broadcastMessage(String msg, boolean newLine);
}
