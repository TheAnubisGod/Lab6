package core.commands;

import core.commands.interfaces.Command;
import core.essentials.Vehicle;
import core.interact.Message;
import core.interact.UserInteractor;

import java.util.Stack;

/**
 * Класс команды удаления первого элемента.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class RemoveFirst implements Command {

    public RemoveFirst() {

    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        if (stack.size() == 0) {
            return new Message("В коллекции нет элементов.", true);
        }
        stack.remove(0);
        return new Message("Элемент успешно удален.", true);
    }
}
