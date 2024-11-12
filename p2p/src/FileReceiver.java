import java.io.*;
import java.net.*;

public class FileReceiver {
    public static void main(String[] args) {
        int port = 12345; // 服务器监听的端口号
        String outputDirectory = "D:/"; // 指定接收文件存放的路径

        // 创建服务器Socket
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("等待连接...");
            Socket socket = serverSocket.accept(); // 接受客户端连接
            System.out.println("连接成功，开始接收文件...");

            // 创建输入流
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String fileName = dataInputStream.readUTF(); // 读取文件名
            long fileSize = dataInputStream.readLong(); // 读取文件大小

            // 创建文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream(outputDirectory + fileName);
            byte[] buffer = new byte[4096]; // 缓冲区
            int bytesRead;

            // 循环读取数据并保存到文件
            while ((bytesRead = dataInputStream.read(buffer, 0, buffer.length)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("文件接收成功: " + outputDirectory + fileName);
            fileOutputStream.close(); // 关闭输出流
            dataInputStream.close(); // 关闭输入流
            socket.close(); // 关闭连接
        } catch (IOException e) {
            e.printStackTrace(); // 输出异常信息
        }
    }
}
