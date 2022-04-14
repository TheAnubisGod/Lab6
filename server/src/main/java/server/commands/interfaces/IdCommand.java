package server.commands.interfaces;

import core.essentials.Vehicle;

import java.io.Serializable;
import java.util.OptionalInt;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * Команды, принимающие в качестве аргумента id.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public interface IdCommand extends Serializable {
    default int getIndexById(int id, Stack<Vehicle> stack) throws Exception {
        OptionalInt indexOpt = IntStream.range(0, stack.size())
                .filter(i -> stack.get(i).getId() == id).findFirst();
        if (indexOpt.isPresent()) {
            return indexOpt.getAsInt();
        }
        throw new Exception("Элемента с таким id не существует в коллекции.");
    }

    default int idArgToIndex(String argument, Stack<Vehicle> stack) {
        int id;
        try {
            id = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
//            interactor.broadcastMessage("Неверный аргумент. Ожидается число (id).", true);
            return -1;
        }

        int index;

        try {
            index = getIndexById(id, stack);
        } catch (Exception e) {
//            interactor.broadcastMessage(e.getMessage(), true);
            return -1;
        }

        return index;
    }
}
