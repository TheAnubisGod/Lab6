package core.essentials;

import java.io.Serializable;
import java.time.ZonedDateTime;


/**
 * Класс элемента коллекции.
 *
 * @version 1.0
 * @author Владислав Дюжев
 */
public class Vehicle implements Comparable<Vehicle>, Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float enginePower; //Значение поля должно быть больше 0
    private VehicleType type; //Поле не может быть null
    private FuelType fuelType; //Поле может быть null

    private static int maxId = 0;

    public Vehicle() {
        setCreationDate();
    }

    public Vehicle(VehicleType type, FuelType fuelType, String name, float enginePower, Long xCord, double yCord) throws Exception {
        setCreationDate();
        setType(type);
        setFuelType(fuelType);
        setName(name);
        setEnginePower(enginePower);
        setCoordinates(xCord, yCord);
        generateId();
    }

    public Vehicle(VehicleType type, FuelType fuelType, String name, float enginePower, Coordinates coordinates) throws Exception {
        setCreationDate();
        setType(type);
        setFuelType(fuelType);
        setName(name);
        setEnginePower(enginePower);
        setCoordinates(coordinates);
        generateId();
    }

    @Override
    public int compareTo(Vehicle o) {
        return Float.compare(this.enginePower, o.enginePower);
    }

    @Override
    public String toString() {
        return String.format("#%d %s (%s)", this.id, this.name, this.type.toString());
    }

    public void generateId() {
        if (this.id == null) {
            maxId++;
            this.id = maxId;
        }
    }

    public void generateId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            generateId();
        }
    }

    private void setCreationDate() {
        this.creationDate = ZonedDateTime.now();
    }

    public void setCreationDate(ZonedDateTime date) {
        if (date != null) {
            this.creationDate = ZonedDateTime.now();
        } else {
            setCreationDate();
        }
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setType(VehicleType type) throws Exception {
        if (type == null) {
            throw new Exception("Поле type не может быть null.");
        }
        this.type = type;
    }

    public void setEnginePower(float enginePower) throws Exception {
        if (enginePower <= 0) {
            throw new Exception("Мощность двигателя (поле enginePower) не может быть отрицательной.");
        }
        this.enginePower = enginePower;
    }

    public void setName(String name) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("Название (поле name) не может быть пустой строкой или null.");
        }
        this.name = name;
    }

    public void setCoordinates(Long xCord, double yCord) throws Exception {
        this.coordinates = new Coordinates(xCord, yCord);
    }

    public void setCoordinates(Coordinates coordinates) throws Exception {
        if (coordinates == null) {
            throw new Exception("Координаты (поле coordinates) не может быть null.");
        }
        this.coordinates = coordinates;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public float getEnginePower() {
        return enginePower;
    }

    public VehicleType getType() {
        return type;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void rideTo(Long newX, double newY) throws Exception {
        this.coordinates.moveTo(newX, newY);
    }

    public static int getMaxId() {
        return maxId;
    }

    public String convertToXmlString() {
        String element = "";
        element += String.format("<id>%d</id>", this.getId());
        element += String.format("<init-time>%s</init-time>", this.getCreationDate());
        element += String.format("<Vehicle-type>%s</Vehicle-type>", this.getType());
        element += String.format("<Fuel-type>%s</Fuel-type>", this.getFuelType());
        element += String.format("<name>%s</name>", this.getName());
        element += String.format("<engine-power>%s</engine-power>", this.getEnginePower());
        element += String.format("<coordinate-x>%d</coordinate-x>", this.getCoordinates().getX());
        element += String.format("<coordinate-y>%s</coordinate-y>", this.getCoordinates().getY());
        element = String.format("<Vehicle>%s</Vehicle>", element);

        return element;
    }
}
