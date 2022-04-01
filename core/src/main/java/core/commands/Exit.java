package core.commands;

import core.commands.interfaces.Command;
import core.essentials.Vehicle;
import core.interact.Message;
import core.interact.UserInteractor;

import java.util.Stack;

/**
 * Класс команды завершения интерактивного режима.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class Exit implements Command {
    @Override
    public Message execute(Stack<Vehicle> stack) {
        return new Message("",false);
    }
}
