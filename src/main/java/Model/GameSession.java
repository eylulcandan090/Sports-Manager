package Model;

public class GameSession {
    private static String managerName = "";

    public static void setManagerName(String name) {
        managerName = (name == null) ? "" : name.trim();
    }

    public static String getManagerName() {
        return managerName;
    }

    public static String getManagerFirstName() {
        if (managerName.isEmpty()) return "Manager";
        return managerName.split(" ")[0];
    }

    public static void reset() {
        managerName = "";
    }
}
