package server;

import core.commands.Save;
import core.commands.interfaces.Command;
import core.commands.interfaces.DateCommand;
import core.essentials.StackInfo;
import core.essentials.Vehicle;
import core.interact.ConsoleInteractor;
import core.interact.Message;
import core.interact.UserInteractor;
import core.main.VehicleStackXmlParser;

import java.io.*;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Stack;

/**
 * Hello world!
 */
public class App {
    public static final int port = 8001;
    private static final UserInteractor adminInteractor = new ConsoleInteractor();
    private static Stack<Vehicle> collection = new Stack<>();
    private static ZonedDateTime initDateTime;
    private static final File file = new File("collection.xml");

    public static void main(String[] args) {
        adminInteractor.broadcastMessage("Подготовка к запуску...", true);

        if (!prepare()) {
            adminInteractor.broadcastMessage("Остановка запуска", true);
            return;
        }

        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(VehicleStackXmlParser.stackToXml(new StackInfo(collection, Vehicle.getMaxId(), initDateTime)));
                    fileWriter.flush();
                } catch (Exception e){
                    adminInteractor.broadcastMessage(e.getMessage(), true);
                }

                adminInteractor.broadcastMessage("Остановка севера.", true);
            }));
        } catch (Exception e) {
            adminInteractor.broadcastMessage("Не удалось настроить условие выхода.", true);
            return;
        }

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            adminInteractor.broadcastMessage(String.format("Невозможно запустить сервер (%s)%n", e.getMessage()), true);
            return;
        }

        InetAddress inetAddress = serverSocket.getInetAddress();

        adminInteractor.broadcastMessage("Сервер запущен по адресу: " + inetAddress.getHostAddress(), true);
        adminInteractor.broadcastMessage("Для остановки нажмите ctrl + c", true);

        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                adminInteractor.broadcastMessage(String.format("Клиент (%s:%s) присоединился!", socket.getInetAddress().toString(), socket.getPort()), true);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                while (!(socket.isClosed())) {
                    Command command = (Command) inputStream.readObject();
                    Message msg;
                    if (command instanceof DateCommand) {
                        msg = ((DateCommand) command).execute(collection, initDateTime);
                    } else {
                        msg = command.execute(collection);
                    }
                    outputStream.writeObject(msg);
                }

                inputStream.close();
                outputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                adminInteractor.broadcastMessage("Соединение потеряно!", true);
            }
        }
    }

    private static void uploadInfo() throws FileNotFoundException, NoSuchFieldException, IllegalAccessException {
        StackInfo stackInfo = VehicleStackXmlParser.parseFromXml(file);
        collection = Objects.requireNonNull(stackInfo).getStack();
        initDateTime = stackInfo.getCreationDate();
        Field field = Vehicle.class.getDeclaredField("maxId");
        field.setAccessible(true);
        field.setInt(null, stackInfo.getMaxId());
    }

    private static boolean prepare() {
        try {
            uploadInfo();
        } catch (FileNotFoundException | NoSuchFieldException | IllegalAccessException | NullPointerException ex) {
            if (ex instanceof NoSuchFieldException || ex instanceof IllegalAccessException || ex instanceof NullPointerException) {
                adminInteractor.broadcastMessage("Возникли проблемы при обработке файла. Данные не считаны. Создаем новый файл.", true);
            }
            initDateTime = ZonedDateTime.now();
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(file);
                fileWriter.close();
            } catch (IOException e) {
                adminInteractor.broadcastMessage("Файл не может быть создан, недостаточно прав доступа или формат имени файла неверен.", true);
                adminInteractor.broadcastMessage("Сообщение об ошибке: " + e.getMessage(), true);
                return false;
            }
        }
        return true;
    }

}
