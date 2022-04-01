package core.commands;

import core.commands.interfaces.Command;
import core.essentials.Vehicle;
import core.interact.Message;
import core.interact.UserInteractor;

import java.util.Collections;
import java.util.Stack;

/**
 * Класс команды изменения направления коллекции.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class Reorder implements Command {


    public Reorder() {

    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        Collections.reverse(stack);
        return new Message("Порядок коллекции инвертирован.", true);
    }
}
