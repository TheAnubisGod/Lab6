package server.commands;


import core.essentials.Vehicle;
import core.precommands.*;
import server.commands.interfaces.Command;
import core.interact.UserInteractor;

import java.util.ArrayList;

/**
 * Класс обращения к командам.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public abstract class CommandRouter {
    public static Command getCommand(Precommand precommand) {
        switch (precommand.getCommandName()) {
            case "help":
                return new Help();
            case "info":
                return new Info();
            case "show":
                return new Show();
            case "add":
                if (precommand instanceof ObjectPrecommand && precommand.getArg() instanceof Vehicle){
                    return new Add(precommand);
                }
                return null;
            case "update":
                if (precommand instanceof ObjectIdPrecommand){
                    return new Update(precommand);
                }

            case "remove_by_id":
                if (precommand instanceof IdPrecommand){
                    return new RemoveById(((IdPrecommand) precommand).getId());
                }
                return null;
            case "clear":
                return new Clear();
            case "remove_first":
                return new RemoveFirst();
            case "add_if_min":
                return new AddIfMin(precommand);
            case "reorder":
                return new Reorder();
            case "group_counting_by_id":
                return new GroupCountingById();
            case "filter_starts_with_name":
                if (precommand instanceof BasicPrecommand && precommand.getArg() instanceof String) {
                    return new FilterStartsWithName((String) precommand.getArg());
                }
                return null;

            case "print_unique_fuel_type":
                return new PrintUniqueFuelType();
            case "sort":
                return new Sort();

            case "info_by_id":
                if (precommand instanceof IdPrecommand){
                    return new InfoById(precommand);
                }
                return null;
            default:
                return null;
        }

    }
}
