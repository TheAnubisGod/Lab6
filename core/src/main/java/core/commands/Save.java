package core.commands;

import core.commands.interfaces.Command;
import core.essentials.StackInfo;
import core.essentials.Vehicle;
import core.interact.Message;
import core.interact.UserInteractor;
import core.main.VehicleStackXmlParser;

import java.io.File;
import java.io.FileWriter;
import java.time.ZonedDateTime;
import java.util.Stack;

/**
 * Класс команды сохранения коллекции.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class Save implements Command {
    private final File file;
    private final ZonedDateTime zonedDateTime;

    public Save(File file, ZonedDateTime zonedDateTime){
        this.file = file;
        this.zonedDateTime = zonedDateTime;
    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(VehicleStackXmlParser.stackToXml(new StackInfo(stack, Vehicle.getMaxId(), zonedDateTime)));
            fileWriter.flush();
        } catch (Exception e) {
            return new Message("Возникла ошибка при сохранении в файл: " + e.getMessage() + zonedDateTime.toString() + Vehicle.getMaxId()+stack.toString(), true);
        }
        return new Message("Файл успешно сохранен.", true);
    }
}
