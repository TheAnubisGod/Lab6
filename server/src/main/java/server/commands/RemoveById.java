package server.commands;

import server.commands.interfaces.Command;
import server.commands.interfaces.IdCommand;
import core.essentials.Vehicle;
import core.interact.Message;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Класс команды удаления элемента по id.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class RemoveById implements Command, IdCommand {
    private final String argument;

    public RemoveById(ArrayList<String> args) {
        this.argument = args.get(0);
    }

    public RemoveById(String arg) {
        this.argument = arg;
    }


    @Override
    public Message execute(Stack<Vehicle> stack) {
        int index = idArgToIndex(argument, stack);
        if (index == -1) {
            return new Message("Неверный аргумент. Ожидается число (id). Или данного элемента не существует.", true);
        }

        stack.remove(index);
        return new Message("Элемент успешно удален.", true);
    }
}
