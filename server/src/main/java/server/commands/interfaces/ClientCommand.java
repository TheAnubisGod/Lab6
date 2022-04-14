package server.commands.interfaces;

import core.essentials.Vehicle;
import core.interact.Message;

import java.util.Stack;

public interface ClientCommand extends Command{
    Message execute(Stack<Vehicle> stack);
}
