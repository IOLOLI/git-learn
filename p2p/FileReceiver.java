import java.io.*;
import java.net.*;

public class FileReceiver {
    public static void main(String[] args) {
        int port = 12345; // 服务器端口
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("等待连接...");
            Socket socket = serverSocket.accept();
            System.out.println("连接成功，开始接收文件...");

            // 创建输入流
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String fileName = dataInputStream.readUTF();
            long fileSize = dataInputStream.readLong();

            // 创建文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream("接收到的_" + fileName);
            byte[] buffer = new byte[4096];
            int bytesRead;

            // 接收文件并写入输出流
            while ((bytesRead = dataInputStream.read(buffer, 0, buffer.length)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("文件接收成功: " + fileName);
            fileOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
