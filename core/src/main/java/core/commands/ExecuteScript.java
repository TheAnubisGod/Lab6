package core.commands;

import core.commands.interfaces.Command;
import core.essentials.Vehicle;
import core.interact.Message;
import core.interact.ScriptInteractor;
import core.interact.UserInteractor;
import core.main.CommandRouter;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс команды выполнения скрипта.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class ExecuteScript implements Command {
    private final String argument;

    public ExecuteScript(ArrayList<String> args) {
        this.argument = args.get(0);
    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        File f = new File(argument);

        Scanner fileScanner;
        try {
            fileScanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            return new Message("Такого файла не существует.", true);
        }
        int line_num = 1;
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }
            try {
                Command command = CommandRouter.getCommand(line, true, new ScriptInteractor(fileScanner));
                if (command == null) {
                    continue;
                }
//                command.execute(stack, interactor);
            } catch (Exception e) {
//                interactor.broadcastMessage("Возникла ошибка при выполнении " + line_num + " строки:\n" + line, true);
                break;
            }
            line_num++;
        }
        return new Message("", false);
    }
}
