package Commands;

import Util.Manager;

import java.util.Scanner;

public class Wordle extends AbstractCommand {
    private static final String SECRET_WORD = "INSERT";
    private static final int MAX_ATTEMPTS = 6;
    private static boolean isUnlocked = false;

    public Wordle() {
        super("Wordle", "Начать игру для разблокировки скрытой команды");
    }

    @Override
    public void execute(String[] args) {
        if (isUnlocked) {
            System.out.println("Вы уже разблокировали скрытую команду!");
            return;
            //Нужно ли?
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в Wordle! Угадайте скрытое слово из " + SECRET_WORD.length() + " букв.");

        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Попытка " + (attempts + 1) + ": ");
            String guess = scanner.nextLine().trim().toUpperCase();

            if (guess.length() != SECRET_WORD.length()) {
                System.out.println("Слово должно быть длиной " + SECRET_WORD.length() + " букв!");
                continue;
            }

            if (guess.equals(SECRET_WORD)) {
                System.out.println("Поздравляем! Вы угадали слово и разблокировали команду insert!");
                isUnlocked = true;
                Manager.unlockInsert();
                Manager.removeCommand("WORDLE");
                return;
            } else {
                System.out.println("Неверно. Подсказка: " + getHint(guess));
            }

            attempts++;
        }

        System.out.println("Вы исчерпали все попытки. Попробуйте снова позже!");
    }

    private String getHint(String guess) {
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < SECRET_WORD.length(); i++) {
            if (guess.charAt(i) == SECRET_WORD.charAt(i)) {
                hint.append(guess.charAt(i));
            } else if (SECRET_WORD.contains(String.valueOf(guess.charAt(i)))) {
                hint.append("*");
            } else {
                hint.append("_");
            }
        }
        return hint.toString();
    }
}

