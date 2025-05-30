package Util;

public class IdGenerator {
    private static int currentId = 1;

    public static int generateId() {
        return currentId++;
    }

    public static void setStartId(int value) {
        currentId = Math.max(currentId, value + 1);
    }
}
