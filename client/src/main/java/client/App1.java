package client;

import core.commands.Clear;
import core.commands.Exit;
import core.commands.interfaces.Command;
import core.interact.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class App1 {

    public static void main(String[] args) {

        try {
            Selector selector = Selector.open();
            SocketChannel connectionClient = SocketChannel.open();
            connectionClient.configureBlocking(false);
            connectionClient.connect(new InetSocketAddress("localhost", 8001));
            connectionClient.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {

                    SelectionKey key = (SelectionKey) iterator.next();
                    iterator.remove();

                    SocketChannel client = (SocketChannel) key.channel();

                    //check a connection was established with a remote server.
                    if (key.isConnectable()) {

                        //if a connection operation has been initiated on this channel but not yet completed
                        if (client.isConnectionPending()) {
                            try {
                                //invoke finishConnect() to complete the connection sequence
                                client.finishConnect();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        client.register(selector, SelectionKey.OP_WRITE, SelectionKey.OP_READ);
                    }

                    if (key.isWritable()) {
                        sendSocketObject(client);
                        if (key.isReadable()){
                            SocketChannel channel = (SocketChannel) key.channel();
                            Message msg = getSocketObjet(channel);
                            System.out.println(msg.getText());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendSocketObject(SocketChannel client) throws IOException {
        Command outgoingMessage = new Clear();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(outgoingMessage);
        objectOutputStream.flush();
        client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
    }
    public static Message getSocketObjet(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ByteBuffer data = ByteBuffer.allocate(100);
        socketChannel.read(data);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Message) objectInputStream.readObject();

    }
}
