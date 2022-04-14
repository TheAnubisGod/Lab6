package server.commands;

import core.precommands.ObjectIdPrecommand;
import core.precommands.Precommand;
import server.commands.interfaces.IdCommand;
import core.essentials.Vehicle;
import core.interact.Message;

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

    public Update(Precommand precommand){
        super(precommand);
        ObjectIdPrecommand objectIdPrecommand = (ObjectIdPrecommand) precommand;
        this.argument = objectIdPrecommand.getId();
    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        if (this.vehicle.getName() == null | this.vehicle.getCoordinates() == null| this.vehicle.getCreationDate() == null
                | this.vehicle.getType() == null | this.vehicle.getEnginePower() <= 0){
            return new Message("Ошибка передачи объекта (недопустимые значения полей).", false);
        }
        int index = idArgToIndex(argument, stack);
        if (index == -1) {
            return new Message("Неверный аргумент. Ожидается число (id). Или данного элемента не существует.", true);
        }
        stack.remove(index);
        vehicle.generateId(Integer.parseInt(argument));
        stack.add(index, vehicle);
        return new Message("Элемент успешно обновлен.", true);
    }

}
