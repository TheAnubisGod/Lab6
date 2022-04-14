package server.commands;

import server.commands.interfaces.Command;
import core.essentials.FuelType;
import core.essentials.Vehicle;
import core.interact.Message;

import java.util.HashSet;
import java.util.Stack;

/**
 * Класс команды вывода уникальных видов топлива.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class PrintUniqueFuelType implements Command {

    public PrintUniqueFuelType() {

    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        StringBuilder str = new StringBuilder("Уникальные типы топлива:\n");
        HashSet<FuelType> hashSet = new HashSet<>();
        int num = 0;
        for (Vehicle vehicle : stack) {
            if (vehicle.getFuelType() != null && !hashSet.contains(vehicle.getFuelType())) {
                hashSet.add(vehicle.getFuelType());
                str.append(vehicle.getFuelType().toString()).append("\n");
                num++;
            }
        }
        return new Message(str + "Всего: " + num + ".", true);
    }
}
