import java.io.*;
import java.net.*;

public class FileSender {
    public static void main(String[] args) {
        String host = "localhost"; // 服务器地址
        int port = 12345; // 服务器端口
        String filePath = "C:\\Users\\admin\\Desktop\\电脑硬件信息.txt"; // 要发送的文件路径

        try (Socket socket = new Socket(host, port)) {
            System.out.println("连接到服务器...");

            // 创建输出流
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            File file = new File(filePath);
            dataOutputStream.writeUTF(file.getName()); // 发送文件名
            dataOutputStream.writeLong(file.length());  // 发送文件大小

            // 创建文件输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;

            // 发送文件内容
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("文件发送成功: " + file.getName());
            fileInputStream.close();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
