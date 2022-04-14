package server.commands.interfaces;

import core.essentials.Vehicle;
import core.interact.Message;

import java.time.ZonedDateTime;
import java.util.Stack;

public interface DateCommand extends Command {
    Message execute(Stack<Vehicle> stack, ZonedDateTime zonedDateTime);
}
