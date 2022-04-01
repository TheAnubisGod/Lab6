package core.commands;

import core.commands.interfaces.Command;
import core.commands.interfaces.Preprocessable;
import core.essentials.FuelType;
import core.essentials.Vehicle;
import core.essentials.VehicleType;
import core.interact.Message;
import core.interact.UserInteractor;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Класс команды добавления элемента.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public class Add implements Command, Preprocessable {
    protected final boolean fromScript;
    protected Vehicle vehicle;

    public Add(boolean from_script) {
        this.fromScript = from_script;
    }

    @Override
    public void preprocess(UserInteractor interactor) {
        interactor.broadcastMessage("Добавление элемента в коллекцию.", true);
        this.vehicle = createVehicle(fromScript, interactor);
    }

    @Override
    public Message execute(Stack<Vehicle> stack) {
        this.vehicle.generateId();
        stack.add(this.vehicle);
        return new Message("Элемент успешно добавлен.", true);
    }

    private void chooseVehicleType(Vehicle vehicle, boolean from_script, UserInteractor interactor) {
        boolean res = false;
        VehicleType vehicleType;
        if (!from_script) {
            interactor.broadcastMessage("Доступные типы транспорта: ", false);

            for (int i = 0; i < VehicleType.values().length; i++) {
                interactor.broadcastMessage(VehicleType.values()[i].toString() + " ", false);
            }
            interactor.broadcastMessage("", true);
        }

        while (!res) {
            if (!from_script) {
                interactor.broadcastMessage("Выберете тип транспорта (поле type): ", false);
            }
            String vehicleTypeName = interactor.getData();
            vehicleType = VehicleType.getByName(vehicleTypeName);
            try {
                vehicle.setType(vehicleType);
            } catch (Exception e) {
                interactor.broadcastMessage("Такого типа транспорта не существует.", true);
                continue;
            }
            res = true;
        }
    }

    private void chooseFuelType(Vehicle vehicle, boolean from_script, UserInteractor interactor) {
        boolean res = false;
        FuelType fuelType;
        if (!from_script) {
            interactor.broadcastMessage("Доступные типы топлива: ", false);

            for (int i = 0; i < FuelType.values().length; i++) {
                interactor.broadcastMessage(FuelType.values()[i].toString() + " ", false);
            }
            interactor.broadcastMessage("", true);
        }

        while (!res) {
            if (!from_script) {
                interactor.broadcastMessage("Выберете тип топлива (поле fuelType): ", false);
            }
            String fuelTypeName = interactor.getData();
            if (fuelTypeName.isEmpty()) {
                vehicle.setFuelType(null);
                return;
            }
            fuelType = FuelType.getByName(fuelTypeName);
            if (fuelType != null) {
                vehicle.setFuelType(fuelType);
            } else {
                interactor.broadcastMessage("Такого типа топлива не существует.", true);
                continue;
            }
            res = true;
        }
    }

    private void chooseName(Vehicle vehicle, boolean from_script, UserInteractor interactor) {
        boolean res = false;
        while (!res) {
            if (!from_script) {
                interactor.broadcastMessage("Дайте название транспорту: ", false);
            }
            try {
                vehicle.setName(interactor.getData());
            } catch (Exception e) {
                interactor.broadcastMessage("Название не должно являться пустой строкой.", true);
                continue;
            }

            res = true;
        }
    }

    private void chooseEnginePower(Vehicle vehicle, boolean from_script, UserInteractor interactor) {
        boolean res = false;
        while (!res) {
            if (!from_script) {
                interactor.broadcastMessage("Введите мощность двигателя: ", false);
            }
            try {
                vehicle.setEnginePower(Float.parseFloat(interactor.getData()));
            } catch (NumberFormatException e) {
                interactor.broadcastMessage("Неверный формат ввода.", true);
                continue;
            } catch (Exception e) {
                interactor.broadcastMessage("Мощность двигателя не может быть отрицательной.", true);
                continue;
            }

            res = true;
        }
    }

    private void chooseCoordinates(Vehicle vehicle, boolean from_script, UserInteractor interactor) {
        boolean res = false;
        while (!res) {
            if (!from_script) {
                interactor.broadcastMessage("Введите начальные координаты через пробел (х у): ", false);
            }
            String potentialCords = interactor.getData();
            potentialCords = potentialCords.trim();
            String[] cords = potentialCords.split("\\s+");
            ArrayList<String> Cords = new ArrayList<>();
            for (String cord : cords) {
                String arg = cord.replaceAll("\\s+", "");
                if (!arg.isEmpty()) {
                    Cords.add(arg);
                }
            }
            if (Cords.size() != 2) {
                interactor.broadcastMessage("Неверный формат ввода.", true);
                continue;
            }

            long X;
            double Y;

            try {
                X = Long.parseLong(Cords.get(0));
                Y = Double.parseDouble(Cords.get(1));
            } catch (NumberFormatException e) {
                interactor.broadcastMessage("Неверный формат ввода.", true);
                continue;
            }

            try {
                vehicle.setCoordinates(X, Y);
            } catch (Exception e) {
                interactor.broadcastMessage("Координаты введены в неверном формате. Максимальное значение у 849.", true);
                continue;
            }

            res = true;
        }
    }

    protected Vehicle createVehicle(boolean from_script, UserInteractor interactor) {
        Vehicle vehicle = new Vehicle();

        chooseVehicleType(vehicle, from_script, interactor);
        chooseFuelType(vehicle, from_script, interactor);
        chooseName(vehicle, from_script, interactor);
        chooseEnginePower(vehicle, from_script, interactor);
        chooseCoordinates(vehicle, from_script, interactor);

        return vehicle;
    }


}
