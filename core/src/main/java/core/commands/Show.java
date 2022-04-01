package core.commands;

import core.commands.interfaces.Command;
import core.essentials.Vehicle;
import core.interact.Message;
import core.interact.UserInteractor;

import java.util.Stack;

/**
 * Класс команды показа элементов.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class Show implements Command {

    public Show() {

    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        StringBuilder str = new StringBuilder();
        if (stack.size() == 0) {
            return new Message("В коллекции нет элементов.", true);
        } else {
            str = new StringBuilder("Все элементы коллекции:\n");
        }
        for (Vehicle vehicle : stack) {
            str.append(vehicle.toString()+"\n");
        }
        return new Message(str + "Всего: " + stack.size() + ".\n", true);
    }
}
