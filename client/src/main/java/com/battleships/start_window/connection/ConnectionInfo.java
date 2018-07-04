package com.battleships.start_window.connection;

public class ConnectionInfo {
    private final String ip;
    private final int port;

    public ConnectionInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    String getIp() {
        return ip;
    }

    int getPort() {
        return port;
    }
}
