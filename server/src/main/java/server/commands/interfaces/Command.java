package server.commands.interfaces;

import core.essentials.Vehicle;
import core.interact.Message;

import java.io.Serializable;
import java.util.Stack;

/**
 * Общий интерфейс для команд.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public interface Command extends Serializable {
    Message execute(Stack<Vehicle> stack);
}
