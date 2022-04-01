package core.interact;

import java.io.*;

public class NetInteractor implements UserInteractor {
    private ObjectOutputStream dataOutputStream;
    private ObjectInputStream dataInputStream;

    public NetInteractor(ObjectInputStream dataInputStream, ObjectOutputStream dataOutputStream) {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public String getData() {
        try {
            return dataInputStream.readUTF();
        } catch (Exception e) {
            return "";
        }

    }

    @Override
    public void broadcastMessage(String msg, boolean newLine) {
        try {
            if (newLine) {
                dataOutputStream.writeBytes(msg + "\n");
            } else {
                dataOutputStream.writeBytes(msg);
            }
        } catch (Exception ignored) {
        }
    }

    public void sendObject(Object obj) throws IOException {
        dataOutputStream.writeObject(obj);
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return dataInputStream.readObject();
    }
}
