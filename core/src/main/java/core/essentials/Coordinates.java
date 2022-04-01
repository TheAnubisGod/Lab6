package core.essentials;

import java.io.Serializable;

/**
 * Класс координат для элемента коллекции.
 *
 * @version 1.0
 * @author Владислав Дюжев
 */
public class Coordinates implements Serializable {
    private Long x; //Поле не может быть null
    private double y; //Максимальное значение поля: 849

    public Coordinates(Long xCord, double yCord) throws Exception {
        if (xCord == null) {
            throw new Exception("Значение координаты х не может быть null.");
        } else if (yCord > 849) {
            throw new Exception("Максимально допустимое значение координаты у: 849.");
        }
        this.x = xCord;
        this.y = yCord;
    }

    @Override
    public String toString() {
        return String.format("(%d, %f)", this.x, this.y);
    }

    public Long getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void moveTo(Long newX, double newY) throws Exception {
        if (newX == null) {
            throw new Exception("Значение координаты х не может быть null.");
        } else if (newY > 849) {
            throw new Exception("Максимально допустимое значение координаты у: 849.");
        }
        this.x = newX;
        this.y = newY;
    }
}
