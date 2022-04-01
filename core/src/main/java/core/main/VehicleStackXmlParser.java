package core.main;

import core.essentials.FuelType;
import core.essentials.StackInfo;
import core.essentials.Vehicle;
import core.essentials.VehicleType;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс взаимодействия с xml кодом (преобразование и парсинг коллекции).
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public abstract class VehicleStackXmlParser {
    public static String stackToXml(StackInfo stackInfo) {
        StringBuilder fileString = new StringBuilder();
        for (Vehicle xmlConvertable : stackInfo.getStack()) {
            fileString.append(xmlConvertable.convertToXmlString());
        }

        fileString = new StringBuilder(String.format("<element-list>%s</element-list>", fileString));
        fileString = new StringBuilder(String.format("<creation-date>%s</creation-date>%s", stackInfo.getCreationDate(), fileString));
        fileString = new StringBuilder(String.format("<max-id>%d</max-id>%s", stackInfo.getMaxId(), fileString));
        fileString = new StringBuilder(String.format("<Stack>%s</Stack>", fileString));

        return fileString.toString();
    }

    private static boolean tagExists(String xmlCode, String tag) {
        int startIndex = xmlCode.indexOf(String.format("<%s>", tag));
        int endIndex = xmlCode.indexOf(String.format("</%s>", tag));
        return !(startIndex == -1 || endIndex == -1);
    }

    private static String parseTagFirst(String xmlCode, String tag) throws Exception {
        int startIndex = xmlCode.indexOf(String.format("<%s>", tag));
        int endIndex = xmlCode.indexOf(String.format("</%s>", tag));
        if (startIndex == -1 || endIndex == -1) {
            throw new Exception("Неверный формат файла, или тега не существует.");
        }
        return xmlCode.substring(startIndex + String.format("<%s>", tag).length(), endIndex);
    }

    private static String deleteTagFirst(String xmlCode, String tag) throws Exception {
        int startIndex = xmlCode.indexOf(String.format("<%s>", tag));
        int endIndex = xmlCode.indexOf(String.format("</%s>", tag));
        if (startIndex == -1 || endIndex == -1) {
            throw new Exception("Неверный формат файла, или тега не существует.");
        }
        return xmlCode.substring(endIndex + String.format("</%s>", tag).length());
    }

    public static StackInfo parseFromXml(String xmlCode) {
        xmlCode = xmlCode.replaceAll("\\s+", "");
        StackInfo stackInfo;
        try {
            ZonedDateTime creationDate = ZonedDateTime.parse(parseTagFirst(xmlCode, "creation-date"));
            int maxId = Integer.parseInt(parseTagFirst(xmlCode, "max-id"));
            Stack<Vehicle> stack = new Stack<>();
            String arrayCode = parseTagFirst(xmlCode, "element-list");
            while (tagExists(arrayCode, "Vehicle")) {
                String vehicleCode = parseTagFirst(arrayCode, "Vehicle");
                int id = Integer.parseInt(parseTagFirst(vehicleCode, "id"));
                ZonedDateTime initTime = ZonedDateTime.parse(parseTagFirst(vehicleCode, "init-time"));
                VehicleType vehicleType = VehicleType.getByName(parseTagFirst(vehicleCode, "Vehicle-type"));
                FuelType fuelType = FuelType.getByName(parseTagFirst(vehicleCode, "Fuel-type"));
                String name = parseTagFirst(vehicleCode, "name");
                float enginePower = Float.parseFloat(parseTagFirst(vehicleCode, "engine-power"));
                Long xCord = Long.parseLong(parseTagFirst(vehicleCode, "coordinate-x"));
                double yCord = Double.parseDouble(parseTagFirst(vehicleCode, "coordinate-y"));


                Vehicle vehicle = new Vehicle();
                vehicle.generateId(id);
                vehicle.setCreationDate(initTime);
                vehicle.setType(vehicleType);
                vehicle.setFuelType(fuelType);
                vehicle.setName(name);
                vehicle.setEnginePower(enginePower);
                vehicle.setCoordinates(xCord, yCord);

                stack.add(vehicle);
                arrayCode = deleteTagFirst(arrayCode, "Vehicle");
            }
            stackInfo = new StackInfo(stack, maxId, creationDate);
        } catch (Exception e) {
            return null;
        }


        return stackInfo;
    }

    public static StackInfo parseFromXml(File file) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(file);
        StringBuilder xml = new StringBuilder();
        while (fileScanner.hasNextLine()) {
            xml.append(fileScanner.nextLine());
        }

        return VehicleStackXmlParser.parseFromXml(xml.toString());
    }
}
