package core.essentials;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Stack;

/**
 * Класс содержащий информацию о коллекции.
 *
 * @version 1.0
 * @author Владислав Дюжев
 */
public class StackInfo implements Serializable {
    private final Stack<Vehicle> stack;
    private final int maxId;
    private final ZonedDateTime creationDate;

    public StackInfo(Stack<Vehicle> stack, int maxId, ZonedDateTime creationDate) throws Exception {
        if (stack != null && maxId >= 0 && creationDate != null) {
            this.stack = stack;
            this.maxId = maxId;
            this.creationDate = creationDate;
        } else {
            throw new Exception("Параметры не могут быть null.");
        }

    }

    public Stack<Vehicle> getStack() {
        return stack;
    }

    public int getMaxId() {
        return maxId;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
}
