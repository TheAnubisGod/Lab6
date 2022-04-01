package client;


import core.commands.ExecuteScript;
import core.commands.Exit;
import core.commands.interfaces.Command;
import core.commands.interfaces.Preprocessable;
import core.interact.*;
import core.main.CommandRouter;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Scanner;


public class App {
    private static final UserInteractor userInteractor = new ConsoleInteractor();
    private static NetInteractor serverInteractor;
    private static int PORT;
    private static String address;
    private static Socket socket;

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
                socket = new Socket(address, PORT);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                serverInteractor = new NetInteractor(objectInputStream, objectOutputStream);
                return;
            } catch (IOException e) {
                userInteractor.broadcastMessage(String.format("Не удалось подключиться к серверу (%s)%n", e.getMessage()), true);
                userInteractor.broadcastMessage("Попробовать еще (Y/N)?", true);
                String inp = userInteractor.getData();
                if (inp.equalsIgnoreCase("N")) {
                    userInteractor.broadcastMessage("Завершение работы программы", true);
                    System.exit(0);
                }
            }
        }
    }

    public static void runInteracting() {
        userInteractor.broadcastMessage("Для просмотра списка команд введите 'help'.", true);
        boolean run = true;
        boolean recon = false;
        while (run) {
            try {
                if (recon | socket.isClosed() | !socket.isConnected()){
                    connect();
                }
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

                if (command instanceof ExecuteScript) {
                    boolean res = true;
                    File f = new File(((ExecuteScript) command).getArgument());

                    Scanner fileScanner;
                    try {
                        fileScanner = new Scanner(f);
                    } catch (FileNotFoundException e) {
                        userInteractor.broadcastMessage("Такого файла не существует!", true);
                        continue;
                    }
                    int line_num = 1;
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        if (line.trim().isEmpty()) {
                            continue;
                        }
                        try {
                            ScriptInteractor scriptInteractor = new ScriptInteractor(fileScanner);
                            Command command1 = CommandRouter.getCommand(line, true, scriptInteractor);
                            if (command1 == null) {
                                continue;
                            }
                            if (command1 instanceof Exit) {
                                return;
                            }
                            if (command1 instanceof Preprocessable) {
                                ((Preprocessable) command1).preprocess(scriptInteractor);
                            }

                            serverInteractor.sendObject(command1);
                            Message msg = (Message) serverInteractor.readObject();
                            if (!msg.isSuccessful()) {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            userInteractor.broadcastMessage("Возникла ошибка при выполнении " + line_num + " строки:\n" + line, true);
                            res = false;
                            break;
                        }
                        line_num++;
                    }
                    if (res) {
                        userInteractor.broadcastMessage("Команды отработали штатно", true);
                    }

                    continue;
                }
                try {
                    serverInteractor.sendObject(command);
                } catch (IOException e) {
                    userInteractor.broadcastMessage("Ошибка", true);
                    recon = true;
                    continue;
                }
                Message msg;
                try {
                    msg = (Message) serverInteractor.readObject();
                } catch (Exception e) {
                    userInteractor.broadcastMessage("Ошибка", true);
                    recon = true;
                    continue;
                }

                userInteractor.broadcastMessage(msg.getText(), true);
                if (!msg.isSuccessful()) {
                    run = false;
                }

            } catch (Exception e) {
                userInteractor.broadcastMessage("Ошибка " + e.getMessage(), true);
            }
        }

    }
}
