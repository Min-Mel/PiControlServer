package server;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.*;

import java.io.DataInputStream;

public class Server implements Runnable {

	private final static int QUIT = 0, KEY_PRESS = 1, KEY_RELEASE = 2, MOUSE_PRESS = 3, MOUSE_RELEASE = 4,
			MOUSE_MOVE = 5, MOUSE_WHEEL = 6;

	private Robot r;
	private boolean quit;
	private int port;

	public Server(int port) {
		this.port = port;
		try {
			r = new Robot();
		} catch (java.awt.AWTException e) {
		}
	}

	public void run() {
		if (r == null)
			return;
		ServerSocket serverSocket = null;
		Socket socket = null;
		DataInputStream in = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("Unable to open socket on " + port);
			return;
		}
		System.out.println("KeyServer is running, listening on port " + port + "...");
		while (true) {
			try {
				socket = serverSocket.accept();
				in = new DataInputStream(socket.getInputStream());
				System.out.println("Client connected on port " + port + ", taking input from " + socket.getInetAddress());
			} catch (Exception e) {
				System.out.println("Unable to open socket on " + port);
			}
			quit = false;
			try {
				while (!quit) {
					int event = in.readInt();
					int key = 0, x = 0, y = 0, button = 0;
					switch (event) {
					case QUIT:
						quit = true;
						in.close();
						System.out.println("Client quit!");
						break;
					case KEY_PRESS:
						key = in.readInt();
						r.keyPress(key);
						break;
					case KEY_RELEASE:
						key = in.readInt();
						r.keyRelease(key);
						break;
					case MOUSE_PRESS:
						button = in.readInt();
						r.mousePress(button);
						break;
					case MOUSE_RELEASE:
						button = in.readInt();
						r.mouseRelease(button);
						break;
					case MOUSE_MOVE:
						x = in.readInt();
						y = in.readInt();
						r.mouseMove(x, y);
						break;
					case MOUSE_WHEEL:
						y = in.readInt();
						r.mouseWheel(y);
						break;
					}

				}
			} catch (Exception e) {
				System.out.println("Error during read");
			}
		}
	}
}
