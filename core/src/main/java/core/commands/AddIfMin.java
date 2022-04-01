package core.commands;

import core.essentials.Vehicle;
import core.interact.Message;
import core.interact.UserInteractor;

import java.util.Collections;
import java.util.Stack;

/**
 * Класс управления коллекцией с помощью командной строки.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class AddIfMin extends Add {
    public AddIfMin(boolean from_script) {
        super(from_script);
    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        this.vehicle.generateId();
        if (stack.isEmpty() || this.vehicle.compareTo(Collections.min(stack)) < 0) {
            stack.add(this.vehicle);
            return  new Message("Элемент успешно добавлен.", true);
        } else {
            return  new Message("Элемент не минимальный.", true);
        }

    }
}
