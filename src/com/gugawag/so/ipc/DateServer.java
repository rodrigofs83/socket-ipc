package com.gugawag.so.ipc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
/**
 * Time-of-day server listening to port 6013.
 *
 * Figure 3.21
 *
 * @author Silberschatz, Gagne, and Galvin. Pequenas alterações feitas por Gustavo Wagner (gugawag@gmail.com)
 * Operating System Concepts  - Ninth Edition
 * Copyright John Wiley & Sons - 2013.
 */
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DateServer  {
	
	public static void main(String[] args) throws IOException  {
		
			ServerSocket sock = new ServerSocket(6013);
			System.out.println("=== Servidor iniciado ===\n");
			
			// escutando por conexões
			

			
			while (true) {
				Socket client = sock.accept();
				// Cria uma thread do servidor para tratar a conexão
								Thread clientThread = new Thread(new ClientHandler(client));
				 // Inicia a thread para o cliente conectado
				clientThread.start();
				
				
				
			}
	}
	

	private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                // Configura os fluxos de entrada e saída para comunicação com o cliente
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

                while (true) {
                    // Lê a mensagem do cliente
                    String mensagem = dis.readUTF();
                    System.out.println("Mensagem do cliente: " + mensagem);

                    // Escreve a data atual no socket
                    String resposta = new Date().toString() + " - Boa noite aluno " + mensagem;

                    // Envia a resposta de volta para o cliente
                    dos.writeUTF(resposta);
                    dos.flush();
                    System.out.println("Resposta enviada para o cliente: " + resposta);
                }
            } catch (IOException e) {
                System.err.println("Erro ao lidar com o cliente: " + e.getMessage());
            }
	}
}
}


