package server.commands;

import server.commands.interfaces.Command;
import core.essentials.Vehicle;
import core.interact.Message;

import java.util.Stack;

/**
 * Класс команды подсчета групп по id.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class GroupCountingById implements Command {
    public GroupCountingById() {

    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        StringBuilder str = new StringBuilder();
        for (Vehicle vehicle : stack) {
            str.append("1 элемент со значением id=").append(vehicle.getId()).append(".\n");
        }
        return new Message(str.toString(), true);
    }
}
