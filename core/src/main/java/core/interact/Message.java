package core.interact;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Message implements Serializable {
    private final String text;
    private final ZonedDateTime creationDate;
    private final boolean result;

    public Message(String text, boolean result) {
        this.text = text;
        this.creationDate = ZonedDateTime.now();
        this.result = result;
    }

    public String getText() {
        return text;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public boolean isSuccessful() {
        return this.result;
    }
}
