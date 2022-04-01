package core.main;

import core.commands.*;
import core.commands.interfaces.Command;
import core.interact.UserInteractor;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Класс обращения к командам.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public abstract class CommandRouter {
    public static Command getCommand(String input, boolean from_script, UserInteractor interactor) {
        input = input.trim();
        String[] commandParts = input.split("\\s+");
        String command = commandParts[0];
        ArrayList<String> Args = new ArrayList<>();
        for (int i = 1; i < commandParts.length; i++) {
            String arg = commandParts[i].replaceAll("\\s+", "");
            if (!arg.isEmpty()) {
                Args.add(arg);
            }
        }


        switch (command) {
            case "help":
                return new Help();
            case "info":
                return new Info();
            case "show":
                return new Show();
            case "add":
                return new Add(from_script);
            case "update":
                if (Args.size() == 0) {
                    interactor.broadcastMessage("Отсутствуют необходимые параметры.", true);
                    return null;
                }
                return new Update(from_script, Args);
            case "remove_by_id":
                if (Args.size() == 0) {
                    interactor.broadcastMessage("Отсутствуют необходимые параметры.", true);
                    return null;
                }
                return new RemoveById(Args);
            case "clear":
                return new Clear();
            case "execute_script":
                if (from_script) {
                    interactor.broadcastMessage("Запрещено выполнять скрипт из другого скрипта.", true);
                    return null;
                }
                if (Args.size() == 0) {
                    interactor.broadcastMessage("Отсутствуют необходимые параметры.", true);
                    return null;
                }
                return new ExecuteScript(Args);
            case "exit":
                return new Exit();
            case "remove_first":
                return new RemoveFirst();
            case "add_if_min":
                return new AddIfMin(from_script);
            case "reorder":
                return new Reorder();
            case "group_counting_by_id":
                return new GroupCountingById();
            case "filter_starts_with_name":
                if (Args.size() == 0) {
                    interactor.broadcastMessage("Отсутствуют необходимые параметры.", true);
                    return null;
                }
                String nameStart = Args.get(0);
                return new FilterStartsWithName(nameStart);
            case "print_unique_fuel_type":
                return new PrintUniqueFuelType();
            case "sort":
                return new Sort();

            case "info_by_id":
                if (Args.size() == 0) {
                    interactor.broadcastMessage("Отсутствуют необходимые параметры.", true);
                    return null;
                }
                return new InfoById(Args);
            case "":
                return null;
            default:
                interactor.broadcastMessage("Команды '" + command + "' не существует. " +
                        "Воспользуйтесь 'help' для получения списка команд.", true);
                return null;
        }

    }
}
