package core.commands;

import core.commands.interfaces.IdCommand;
import core.commands.interfaces.Preprocessable;
import core.essentials.Vehicle;
import core.interact.Message;
import core.interact.UserInteractor;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Класс команды обновления элемента коллекции по id.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class Update extends Add implements IdCommand {
    private final String argument;

    public Update(boolean from_script, ArrayList<String> args) {
        super(from_script);
        this.argument = args.get(0);
    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        int index = idArgToIndex(argument, stack);
        if (index == -1) {
            return new Message("Неверный аргумент. Ожидается число (id). Или данного элемента не существует.", true);
        }
        stack.remove(index);
        stack.add(index, vehicle);
        return new Message("Элемент успешно обновлен.", true);
    }

}
