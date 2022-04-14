package server.commands;

import server.commands.interfaces.DateCommand;
import core.essentials.Vehicle;
import core.interact.Message;

import java.time.ZonedDateTime;
import java.util.Stack;

/**
 * Класс команды получения информации о коллекции.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class Info implements DateCommand {

    public Info() {

    }


    @Override
    public Message execute(Stack<Vehicle> stack, ZonedDateTime zonedDateTime) {
        return new Message("Важная информация о коллекции:\n" +
                "\n" +
                "Тип: " + Vehicle.class.getName() + "\n" +
                "Дата инициализации: " + zonedDateTime.toString() + "\n" +
                "Максимальный id: " + Vehicle.getMaxId() + "\n" +
                "Количество элементов: " + stack.size(), true);
    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        return new Message("", false);
    }
}
