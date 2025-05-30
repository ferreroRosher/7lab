package Util;

import Collection.*;
import java.util.StringTokenizer;
import java.util.function.DoublePredicate;
import java.util.function.LongPredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.time.ZonedDateTime;

public class CreatePerson {

    public static Person createFromInput() {
        StringTokenizer tokens = null;

        System.out.println("Введите данные для нового Person:");
        System.out.println("Формат строки: имя X Y рост паспортID национальность locX locY locZ locName");
        System.out.println("Пример: Иван 123 321 180 ABCDEF GERMANY 1.0 2.0 3 Центр");

        String line = CommandScanner.readLine("> ");
        if (line == null) throw new InterruptedInsertException();
        if (!line.trim().isEmpty()) {
            tokens = new StringTokenizer(line);
        }

        try {
            String name = getNextValidString("имя", tokens, s -> !s.isEmpty(), "Имя не может быть пустым");

            Coordinates coordinates = new Coordinates(
                    getNextValidInt("координата X (целое ≤ 369)", tokens, x -> x <= 369, "X должен быть ≤ 369"),
                    getNextValidFloat("координата Y (> -983)", tokens, y -> y > -983, "Y должен быть > -983")
            );

            long height = getNextValidLong("рост (> 0)", tokens, h -> h > 0, "Рост должен быть > 0");

            String passportID = getNextValidString("паспорт ID (6–33 символа)", tokens,
                    s -> s.length() >= 6 && s.length() <= 33,
                    "Паспорт ID должен быть от 6 до 33 символов");

            Country nationality = getNextValidCountry(tokens);

            Location location = new Location(
                    getNextValidDouble("локация X", tokens),
                    getNextValidDouble("локация Y", tokens),
                    getNextValidInt("локация Z", tokens, z -> true, ""),
                    getNextValidString("название локации", tokens, s -> !s.isEmpty(), "Название не может быть пустым")
            );

            return new Person(IdGenerator.generateId(), name, coordinates, height, ZonedDateTime.now(), passportID, nationality, location);

        } catch (InterruptedInsertException e) {
            System.out.println("Команда insert прервана.");
            return null;
        }
    }

    // Вспомогательные методы

    private static String checkInterrupt(String input) {
        if (input == null) throw new InterruptedInsertException();
        if (Manager.getCommands().containsKey(input.toUpperCase())) {
            CommandScanner.disableInputMode();
            Manager.executeCommand(input.toUpperCase());
            throw new InterruptedInsertException();
        }
        return input;
    }

    private static String getNextValidString(String prompt, StringTokenizer tokens, Predicate<String> validator, String errorMessage) {
        while (true) {
            String input = tokens != null && tokens.hasMoreTokens() ? tokens.nextToken() : CommandScanner.readLine("Введите " + prompt + ":");
            input = checkInterrupt(input);
            if (validator.test(input)) return input;
            System.out.println(" " + errorMessage);
        }
    }

    private static int getNextValidInt(String prompt, StringTokenizer tokens, IntPredicate validator, String errorMessage) {
        while (true) {
            try {
                String token = tokens != null && tokens.hasMoreTokens() ? tokens.nextToken() : CommandScanner.readLine("Введите " + prompt + ":");
                token = checkInterrupt(token);
                int value = Integer.parseInt(token);
                if (validator.test(value)) return value;
            } catch (Exception ignored) {}
            System.out.println("Неверный ввод. " + errorMessage);
        }
    }

    private static float getNextValidFloat(String prompt, StringTokenizer tokens, DoublePredicate validator, String errorMessage) {
        while (true) {
            try {
                String token = tokens != null && tokens.hasMoreTokens() ? tokens.nextToken() : CommandScanner.readLine("Введите " + prompt + ":");
                token = checkInterrupt(token);
                float value = Float.parseFloat(token);
                if (validator.test(value)) return value;
            } catch (Exception ignored) {}
            System.out.println("Неверный ввод. " + errorMessage);
        }
    }

    private static long getNextValidLong(String prompt, StringTokenizer tokens, LongPredicate validator, String errorMessage) {
        while (true) {
            try {
                String token = tokens != null && tokens.hasMoreTokens() ? tokens.nextToken() : CommandScanner.readLine("Введите " + prompt + ":");
                token = checkInterrupt(token);
                long value = Long.parseLong(token);
                if (validator.test(value)) return value;
            } catch (Exception ignored) {}
            System.out.println("Неверный ввод. " + errorMessage);
        }
    }

    private static double getNextValidDouble(String prompt, StringTokenizer tokens) {
        while (true) {
            try {
                String token = tokens != null && tokens.hasMoreTokens() ? tokens.nextToken() : CommandScanner.readLine("Введите " + prompt + ":");
                token = checkInterrupt(token);
                return Double.parseDouble(token);
            } catch (Exception e) {
                System.out.println("Неверный ввод. Повторите попытку.");
            }
        }
    }

    private static Country getNextValidCountry(StringTokenizer tokens) {
        while (true) {
            try {
                String token = tokens != null && tokens.hasMoreTokens() ? tokens.nextToken() : CommandScanner.readLine("Введите национальность:");
                token = checkInterrupt(token);
                return Country.valueOf(token.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Неверное значение. Повторите ввод.");
            }
        }
    }

    private static class InterruptedInsertException extends RuntimeException {}
}


