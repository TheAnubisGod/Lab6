package core.interact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

/**
 * Класс получения данных из консоли.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class ConsoleInteractor implements UserInteractor {

    @Override
    public String getData() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (NoSuchElementException | IOException e) {
            try {
                System.in.reset();
            } catch (IOException ex) {
                broadcastMessage("Завершение работы.", true);
                return "exit";
            }
            return "";
        }
    }

    @Override
    public void broadcastMessage(String msg, boolean newLine) {
        if (newLine) {
            System.out.println(msg);
        } else {
            System.out.print(msg);
        }
    }

}
