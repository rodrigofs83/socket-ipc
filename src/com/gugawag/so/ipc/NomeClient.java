package com.gugawag.so.ipc;

/**
 * Client program requesting current date from server.
 *
 * Figure 3.22
 *
 * @author Silberschatz, Gagne and Galvin. Pequenas alterações feitas por Gustavo Wagner (gugawag@gmail.com)
 * Operating System Concepts  - Eighth Edition
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class NomeClient  {
	public static void main(String[] args) throws IOException  {
		
			// this could be changed to an IP name or address other than the localhost
			Socket servidorSock = new Socket("127.0.0.1",6013);
			System.out.println("=== Cliente iniciado ===\n");
			  // Thread para enviar mensagens para o servidor
        Thread senderThread = new Thread(() -> {
            try {
                DataOutputStream dos = new DataOutputStream(servidorSock.getOutputStream());
				Scanner scanner = new Scanner(System.in);

                while (true) {
                  // TODO Altere abaixo para enviar seu nome ao servidor
					System.out.println("digite a mesagem para servidor");
					String nome = scanner.nextLine();
			
                    dos.writeUTF(nome);
                    dos.flush(); // Garante que a mensagem seja enviada imediatamente
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Thread para receber mensagens do servidor
        Thread receiverThread = new Thread(() -> {
            try {
                DataInputStream dis = new DataInputStream(servidorSock.getInputStream());
                while (true) {
                    String mensagem = dis.readUTF();
                    System.out.println("Resposta do servidor");
                    System.out.println("Servidor: " + mensagem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        senderThread.start();
        receiverThread.start();
		
	}
	
}
