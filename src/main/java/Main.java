import Util.CollectionManager;
import Util.CommandScanner;


import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        /* Scanner scanner = new Scanner(System.in);
        // Создаём объект Scanner
        String name = scanner.nextLine();
        // Читаем строку из ввода
        System.out.println("Hello world! " + name);
         */
            CollectionManager.setCollection(new LinkedHashMap<>());

        CommandScanner.startInteractiveMode();
    }
}