package core.commands;

import core.commands.interfaces.Command;
import core.essentials.Vehicle;
import core.interact.Message;
import core.interact.UserInteractor;

import java.util.Stack;

/**
 * Класс команды очистки коллекции.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class Clear implements Command {
    public Clear() {

    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        stack.clear();
        return new Message("Коллекция очищена.", true);
    }
}
