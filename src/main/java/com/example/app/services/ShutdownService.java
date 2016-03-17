package com.example.app.services;

import java.io.BufferedInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.inject.Inject;

import org.gwizard.services.Services;

import com.example.app.ApplicationConfig;
import com.example.app.Main;
import com.example.app.util.SocketUtils;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShutdownService extends AbstractExecutionThreadService {

    public static byte SHUTDOWN = 1;
    public static byte SERVICE_STOP = 2;

    @Inject
    private ApplicationConfig config;

    @Inject
    public ShutdownService(Services services) {
        services.add(this);
    }

    @Override
    protected void startUp() throws Exception {
        log.debug("ShutdownService starting...");

        if (shouldStop()) {
            stopOtherApplication();
        }
    }

    @Override
    protected void shutDown() throws Exception {
        log.debug("ShutdownService shutting down...");
    }

    private void stopOtherApplication() throws Exception {
        InetAddress address = InetAddress.getByName(null);
        int port = config.getShutdownPort();

        log.info(String.format("ShutdownService sending stop command to %s:%s", address, port));

        try {
            SocketUtils.sendByte(address, config.getShutdownPort(), SHUTDOWN);
        } catch (Exception e) {
            log.error("Failed to stop running application.");
        }

        stopApplication();
    }

    @Override
    protected void run() throws Exception {
        if (shouldStop()) {
            return;
        }

        InetAddress address = InetAddress.getByName(null);
        int port = config.getShutdownPort();

        log.info(String.format("ShutdownService listening on %s:%s", address, port));

        ServerSocket serverSocket = new ServerSocket(port, 0, address);
        Socket socket = serverSocket.accept();

        // read
        BufferedInputStream br = new BufferedInputStream(socket.getInputStream());
        byte[] message = new byte[1];
        br.read(message, 0, 1);

        // clean
        socket.close();
        serverSocket.close();

        if (message[0] == SHUTDOWN) {
            log.info("Application is stopping");
            stopApplication();
        }
    }

    @Override
    protected void triggerShutdown() {
        try {
            // Send the signal to stop this service
            SocketUtils.sendByte(InetAddress.getByName(null), config.getShutdownPort(), SERVICE_STOP);
        } catch (Exception e) {

        }
    }

    private boolean shouldStop() {
        return Main.arguments != null && Main.arguments.length == 2 && "stop".equals(Main.arguments[1]);
    }

    /**
     * Calls System.exit(0) in a new thread to let this service stop
     */
    private void stopApplication() {
        new Thread() {
            public void run() {
                System.exit(0);
            }
        }.start();
    }

}
