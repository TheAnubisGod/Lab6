package core.interact;

import java.util.Scanner;

/**
 * Класс получения данных из скрипта.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class ScriptInteractor implements UserInteractor {
    private final Scanner scanner;

    public ScriptInteractor(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getData() {
        return scanner.nextLine();
    }

    @Override
    public void broadcastMessage(String msg, boolean newLine) {
        new ConsoleInteractor().broadcastMessage(msg, newLine);
    }
}
