package utilities;

public class Session {
    private static String loggedInUsername;

    public static void setUsername(String username) {
        loggedInUsername = username;
    }

    public static String getUsername() {
        return loggedInUsername;
    }

    public static void clear() {
        loggedInUsername = null;
    }
}
