package core.essentials;

import java.io.Serializable;
import java.util.Objects;

/**
 * Enum описывающий виды элементов коллекции.
 *
 * @version 1.0
 * @author Владислав Дюжев
 */
public enum VehicleType implements Serializable {
    SUBMARINE("SUBMARINE"),
    BICYCLE("BICYCLE"),
    HOVERBOARD("HOVERBOARD");

    private final String name;

    VehicleType(String name) {
        this.name = name;
    }

    public static VehicleType getByName(String name) {
        for (VehicleType vehicleType : VehicleType.values()) {
            if (Objects.equals(vehicleType.name, name)) {
                return vehicleType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
