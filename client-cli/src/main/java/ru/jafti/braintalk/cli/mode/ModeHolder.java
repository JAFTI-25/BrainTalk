package ru.jafti.braintalk.cli.mode;

public class ModeHolder {

    public static final ModeHolder INSTANCE = new ModeHolder();

    private static final String DEFAULT_MODE = "DEFAULT";
    private static final String INTERCONNECT_MODE = "INTERCONNECT";

    private String currentMode = DEFAULT_MODE;
    private String currentConnectedUser;

    public void connectToUser(String user) {
        currentConnectedUser = user;
        currentMode = INTERCONNECT_MODE;
    }

    public void toDefault() {
        currentConnectedUser = null;
        currentMode = DEFAULT_MODE;
    }

    public boolean isConnected() {
        return currentMode.equals(INTERCONNECT_MODE);
    }

    public boolean isDefaultMode() {
        return currentMode.equals(DEFAULT_MODE);
    }

    public String getConnectedUser() {
        return currentConnectedUser;
    }
}
