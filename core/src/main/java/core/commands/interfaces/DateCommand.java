package core.commands.interfaces;

import core.essentials.Vehicle;
import core.interact.Message;
import core.interact.UserInteractor;

import java.time.ZonedDateTime;
import java.util.Stack;

public interface DateCommand extends Command {
    Message execute(Stack<Vehicle> stack, ZonedDateTime zonedDateTime);
}
