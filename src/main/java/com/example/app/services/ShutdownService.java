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

        stopOtherApplication();
    }

    @Override
    protected void shutDown() throws Exception {
        log.debug("ShutdownService shutting down...");
    }

    private void stopOtherApplication() throws Exception {
        if (Main.arguments != null && Main.arguments.length == 2) {
            if ("stop".equals(Main.arguments[1])) {
                SocketUtils.sendByte(InetAddress.getByName(null), config.getShutdownPort(), SHUTDOWN);
                stopApplication();
            }
        }
    }

    @Override
    protected void run() throws Exception {
        ServerSocket serverSocket = new ServerSocket(config.getShutdownPort(), 0, InetAddress.getByName(null));
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
