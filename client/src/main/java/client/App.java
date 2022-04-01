package client;


import core.commands.Exit;
import core.commands.interfaces.Command;
import core.commands.interfaces.Preprocessable;
import core.interact.ConsoleInteractor;
import core.interact.Message;
import core.interact.NetInteractor;
import core.interact.UserInteractor;
import core.main.CommandRouter;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Arrays;


public class App {
    private static final UserInteractor userInteractor = new ConsoleInteractor();
    private static NetInteractor serverInteractor;
    private static int PORT;
    private static String address;

    public static void main(String[] args) {
        try {
            String[] ar = args[0].split(":");
            if (ar.length != 2) {
                throw new Exception();
            }
            address = ar[0];
            PORT = Integer.parseInt(ar[1]);

        } catch (Exception e) {
            userInteractor.broadcastMessage("Укажите адрес в качестве аргумента командной строки (Формат - localhost:50001).", true);
            return;
        }
        connect();
        runInteracting();
    }

    public static void connect() {
        userInteractor.broadcastMessage("Подключение к серверу...", true);
        while (true) {
            try {
                Socket socket = new Socket(address, PORT);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                serverInteractor = new NetInteractor(objectInputStream, objectOutputStream);
                return;
            } catch (IOException e) {
                userInteractor.broadcastMessage(String.format("Не удалось подключиться к серверу (%s)%n", e.getMessage()), true);
                userInteractor.broadcastMessage("Попробовать еще (Y/N)?", true);
                String inp = userInteractor.getData();
                if (inp.toUpperCase().equals("N")) {
                    userInteractor.broadcastMessage("Завершение работы программы", true);
                    System.exit(0);
                }
            }
        }
    }

    public static void runInteracting() {
        userInteractor.broadcastMessage("Для просмотра списка команд введите 'help'.", true);
        boolean run = true;
        try {
            while (run) {
                userInteractor.broadcastMessage("\nВведите команду: ", false);
                String potentialCommand = userInteractor.getData();
                if (potentialCommand == null) {
                    continue;
                }
                Command command = CommandRouter.getCommand(potentialCommand, false, userInteractor);
                if (command == null) {
                    continue;
                }
                if (command instanceof Exit) {
                    return;
                }
                if (command instanceof Preprocessable) {
                    ((Preprocessable) command).preprocess(userInteractor);
                }
                try {
                    serverInteractor.sendObject(command);
                } catch (IOException e) {
                    connect();
                }
                Message msg;
                try {
                    msg = (Message) serverInteractor.readObject();
                } catch (Exception e) {
                    msg = new Message(e.getMessage(), false);
                }

                userInteractor.broadcastMessage(msg.getText(), true);
                if (!msg.isSuccessful()) {
                    run = false;
                }
            }
        } catch (Exception e) {
            connect();
        }

    }
}
