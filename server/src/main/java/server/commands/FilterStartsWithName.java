package server.commands;

import server.commands.interfaces.Command;
import core.essentials.Vehicle;
import core.interact.Message;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс команды фильтрации по имени.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class FilterStartsWithName implements Command {
    private final String argument;

    public FilterStartsWithName(String nameStart) {
        this.argument = nameStart;
    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        StringBuilder str = new StringBuilder("Все элементы, чье название начинается с " + argument + ":\n");
        Stream<Vehicle> vehicleStream = stack.stream();
        List<Vehicle> stack1 = vehicleStream.filter(x -> x.getName().startsWith(argument)).collect(Collectors.toList());
        int num = 0;
        for (Vehicle vehicle : stack1) {
            str.append(vehicle.toString()).append("\n");
            num++;
        }
        return new Message(str + "Всего: " + num + ".", true);
    }
}
