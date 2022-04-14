package client;

import core.interact.UserInteractor;
import core.precommands.*;
import core.precommands.argcreation.VehicleGenerator;

import java.util.ArrayList;

/**
 * Класс обращения к командам.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public abstract class PreCommandRouter {
    public static Precommand getCommand(String input, boolean from_script, UserInteractor interactor) {
        input = input.trim();
        String[] commandParts = input.split("\\s+");
        String command = commandParts[0];
        ArrayList<String> Args = new ArrayList<>();
        for (int i = 1; i < commandParts.length; i++) {
            String arg = commandParts[i].replaceAll("\\s+", "");
            if (!arg.isEmpty()) {
                Args.add(arg);
            }
        }


        switch (command) {
            case "help":
                BasicPrecommand helpPrecommand = new BasicPrecommand("help");
                helpPrecommand.preprocess(null, from_script);
                return helpPrecommand;
            case "info":
                BasicPrecommand infoPrecommand = new BasicPrecommand("info");
                infoPrecommand.preprocess(null, from_script);
                return infoPrecommand;
            case "show":
                BasicPrecommand showPrecommand = new BasicPrecommand("show");
                showPrecommand.preprocess(null, from_script);
                return showPrecommand;
            case "add":
                ObjectPrecommand addPrecommand = new ObjectPrecommand("add");
                addPrecommand.preprocess(VehicleGenerator.createVehicle(from_script, interactor), from_script);
                return addPrecommand;
            case "update":
                if (Args.size() == 0) {
                    interactor.broadcastMessage("Отсутствуют необходимые параметры.", true);
                    return null;
                }
                ObjectIdPrecommand updatePrecommand = new ObjectIdPrecommand("update", Args.get(0));
                updatePrecommand.preprocess(VehicleGenerator.createVehicle(from_script, interactor), from_script);
                return updatePrecommand;
            case "remove_by_id":
                if (Args.size() == 0) {
                    interactor.broadcastMessage("Отсутствуют необходимые параметры.", true);
                    return null;
                }
                IdPrecommand removeByIdPrecommand = new IdPrecommand("remove_by_id", Args.get(0));
                removeByIdPrecommand.preprocess(null, from_script);
                return removeByIdPrecommand;
            case "clear":
                BasicPrecommand clearPrecommand = new BasicPrecommand("clear");
                clearPrecommand.preprocess(null, from_script);
                return clearPrecommand;
            case "execute_script":
                if (from_script) {
                    interactor.broadcastMessage("Запрещено выполнять скрипт из другого скрипта.", true);
                    return null;
                }
                if (Args.size() == 0) {
                    interactor.broadcastMessage("Отсутствуют необходимые параметры.", true);
                    return null;
                }
                BasicPrecommand executePrecommand = new BasicPrecommand("execute_script");
                executePrecommand.preprocess(Args.get(0), false);
                return executePrecommand;
            case "exit":
                BasicPrecommand exitPrecommand = new BasicPrecommand("exit");
                exitPrecommand.preprocess(null, from_script);
                return exitPrecommand;
            case "remove_first":
                BasicPrecommand removeFirstPrecommand = new BasicPrecommand("remove_first");
                removeFirstPrecommand.preprocess(null, from_script);
                return removeFirstPrecommand;
            case "add_if_min":
                ObjectPrecommand addIfMinPrecommand = new ObjectPrecommand("add_if_min");
                addIfMinPrecommand.preprocess(VehicleGenerator.createVehicle(from_script, interactor), from_script);
                return addIfMinPrecommand;
            case "reorder":
                BasicPrecommand reorderPrecommand = new BasicPrecommand("reorder");
                reorderPrecommand.preprocess(null, from_script);
                return reorderPrecommand;
            case "group_counting_by_id":
                BasicPrecommand groupCountingPrecommand = new BasicPrecommand("group_counting_by_id");
                groupCountingPrecommand.preprocess(null, from_script);
                return groupCountingPrecommand;
            case "filter_starts_with_name":
                if (Args.size() == 0) {
                    interactor.broadcastMessage("Отсутствуют необходимые параметры.", true);
                    return null;
                }
                BasicPrecommand filterPrecommand = new BasicPrecommand("filter_starts_with_name");
                String nameStart = Args.get(0);
                filterPrecommand.preprocess(nameStart, from_script);
                return filterPrecommand;
            case "print_unique_fuel_type":
                BasicPrecommand printPrecommand = new BasicPrecommand("print_unique_fuel_type");
                printPrecommand.preprocess(null, from_script);
                return printPrecommand;
            case "sort":
                BasicPrecommand sortPrecommand = new BasicPrecommand("sort");
                sortPrecommand.preprocess(null, from_script);
                return sortPrecommand;

            case "info_by_id":
                if (Args.size() == 0) {
                    interactor.broadcastMessage("Отсутствуют необходимые параметры.", true);
                    return null;
                }
                IdPrecommand infoByIdPrecommand = new IdPrecommand("info_by_id", Args.get(0));
                infoByIdPrecommand.preprocess(null, from_script);
                return infoByIdPrecommand;
            case "":
                return null;
            default:
                interactor.broadcastMessage("Команды '" + command + "' не существует. " +
                        "Воспользуйтесь 'help' для получения списка команд.", true);
                return null;
        }

    }
}
