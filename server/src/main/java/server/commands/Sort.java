package server.commands;

import server.commands.interfaces.Command;
import core.essentials.Vehicle;
import core.interact.Message;

import java.util.Comparator;
import java.util.Stack;

/**
 * Класс команды сортировки коллекции.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class Sort implements Command {

    public Sort() {

    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        stack.sort(Comparator.naturalOrder());
        return new Message("Коллекция отсортирована.", true);
    }
}
