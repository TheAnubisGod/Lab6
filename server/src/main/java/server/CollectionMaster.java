package server;

import core.commands.interfaces.Command;
import core.essentials.StackInfo;
import core.essentials.Vehicle;
import core.interact.NetInteractor;
import core.interact.ConsoleInteractor;
import core.interact.UserInteractor;
import core.main.CommandRouter;
import core.main.VehicleStackXmlParser;

import java.io.*;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Stack;

/**
 * Класс команды добавления элемента, если он минимальный.
 *
 * @author Владислав Дюжев
 * @version 1.0
 */
public abstract class CollectionMaster {

    private static Stack<Vehicle> collection = new Stack<>();
    private static ZonedDateTime initDateTime;
    private static File file = new File("collection.xml");
    private static UserInteractor interactor = new ConsoleInteractor();
    public static final int port = 50001;

    public static void main(String[] args) {
        if (!prepare()) {
            return;
        }

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientConn = serverSocket.accept();
//            clientInteractor = new NetInteractor(new DataInputStream(clientConn.getInputStream()),new DataOutputStream(clientConn.getOutputStream()));
        } catch (IOException e) {
            interactor.broadcastMessage("Сервер не может быть запущен!",true);
            return;
        }

        file = new File("Collection.xml");

        if (!prepare()) {
            return;
        }

        runInteracting();


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
                interactor.broadcastMessage("Возникли проблемы при обработке файла. Данные не считаны.", true);
            }
            initDateTime = ZonedDateTime.now();
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(file);
                fileWriter.close();
            } catch (IOException e) {
                interactor.broadcastMessage("Файл не может быть создан, недостаточно прав доступа или формат имени файла неверен.", true);
                interactor.broadcastMessage("Сообщение об ошибке: " + e.getMessage(), true);
                return false;
            }
        }
        return true;
    }

    private static void runInteracting() {
        interactor.broadcastMessage("Для просмотра списка команд введите 'help'.", true);
        boolean run = true;
        while (run) {
            interactor.broadcastMessage("\nВведите команду: ", false);
            String potentialCommand = interactor.getData();
            if (potentialCommand == null) {
                continue;
            }
            Command command = CommandRouter.getCommand(potentialCommand, false, interactor);
            if (command != null) {
//                run = command.execute(collection);
            }
        }
    }

}
