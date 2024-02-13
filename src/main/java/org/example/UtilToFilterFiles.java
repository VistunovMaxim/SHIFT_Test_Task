package org.example;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UtilToFilterFiles {

    // Результирующие файлы
    private File integers;
    private File floats;
    private File strings;
    // Опции
    private String path;
    private String prefix;
    private Boolean isFullStats;
    private boolean append;
    // Статистика
    private int maxInteger = Integer.MIN_VALUE;
    private int minInteger = Integer.MAX_VALUE;
    private float maxFloat = Float.MIN_VALUE;
    private float minFloat = Float.MAX_VALUE;
    private int largestLineSize = Integer.MIN_VALUE;
    private int smallestLineSize = Integer.MAX_VALUE;
    private int countOfIntegers;
    private int countOfFloats;
    private int countOfStrings;
    private int sumOfIntegers;
    private float sumOfFloats;

    public UtilToFilterFiles() {
        this.path = null;
        this.prefix = "";
        this.isFullStats = null;
        this.append = false;
    }

    //Сеттеры опций
    public void setPath(String path) {
        this.path = path;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setFullStats(Boolean fullStats) {
        isFullStats = fullStats;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    // Метод запуска утилиты, создание потоков чтения файлов
    public void filterFiles(List<File> listOfFiles) throws FileNotFoundException {
        List<Scanner> listOfScanners = new ArrayList<>();
        for (File listOfFile : listOfFiles) {
            try {
                listOfScanners.add(new Scanner(listOfFile));
            } catch (FileNotFoundException e) {
                System.err.println("Не найден файл с таким названием - " + e.getMessage() + ". " +
                        "Файл не был включен в фильтрацию.");
            }
        }
        fileReader(listOfScanners);
    }

    //Чтение всех файлов построчно
    private void fileReader(List<Scanner> listOfScanners) throws FileNotFoundException {
        while (!listOfScanners.isEmpty()) {
            for (int i = 0; i < listOfScanners.size(); i++) {
                if (listOfScanners.get(i).hasNext()) {
                    informationDistribution(listOfScanners.get(i).nextLine());
                } else {
                    listOfScanners.get(i).close();
                    listOfScanners.remove(i);
                }
            }
        }
        if (isFullStats != null)
            displayStatistic();
    }

    // Распределение информации по соответствующим файлам
    private void informationDistribution(String str) throws FileNotFoundException {

        if (NumberUtils.isCreatable(str)) {

            if (NumberUtils.isDigits(str)) {
                if (integers == null)
                    integers = createNewFile("integers");
                fileWriter(integers, str);

                countOfIntegers++;
                sumOfIntegers += Integer.parseInt(str);
                if (Integer.parseInt(str) > maxInteger)
                    maxInteger = Integer.parseInt(str);
                if (Integer.parseInt(str) < minInteger)
                    minInteger = Integer.parseInt(str);

            } else {
                if (floats == null)
                    floats = createNewFile("floats");
                fileWriter(floats, str);

                countOfFloats++;
                sumOfFloats += Float.parseFloat(str);
                if (Float.parseFloat(str) > maxFloat)
                    maxFloat = Float.parseFloat(str);
                if (Float.parseFloat(str) < minFloat)
                    minFloat = Float.parseFloat(str);

            }
        } else {
            if (strings == null)
                strings = createNewFile("strings");
            fileWriter(strings, str);

            countOfStrings++;
            if (str.length() > largestLineSize)
                largestLineSize = str.length();
            if (str.length() < smallestLineSize)
                smallestLineSize = str.length();

        }
    }

    // Запись в соответсвующие файлы
    private void fileWriter(File fileToBeWritten, String str) throws FileNotFoundException {
        try (FileWriter fileWriter = new FileWriter(fileToBeWritten, true)) {
            fileWriter.write(str + "\n");
        } catch (IOException e) {
            throw new FileNotFoundException("Необходимо указать абсолютный путь к директории для результата - "
                    + e.getMessage());
        }
    }

    // Создание новых файлов
    private File createNewFile(String nameOfFile) throws FileNotFoundException {
        File file = new File(path, prefix + nameOfFile + ".txt");

        //Удаление информации, если такой файл уже существует (if append == false)
        if (!append) {
            try {
                new FileWriter(file).close();
            } catch (IOException e) {
                throw new FileNotFoundException("Необходимо указать абсолютный путь к директории для результата - "
                        + e.getMessage());
            }
        }
        return file;
    }

    //Вывод статистики
    private void displayStatistic() {
        if (isFullStats) {
            System.out.println("Полная статистика:\n" +
                    "Кол-во целых чисел - " + countOfIntegers + "\n" +
                    "Кол-во вещественных чисел - " + countOfFloats + "\n" +
                    "Кол-во строковых данных - " + countOfStrings + "\n" +
                    "Макс. целое число - " + (maxInteger == Integer.MIN_VALUE ? "Отсутствует" : maxInteger) + "\n" +
                    "Макс. вещественное число - " + (maxFloat == Float.MIN_VALUE ? "Отсутствует" : maxFloat) + "\n" +
                    "Мин. целое число - " + (minInteger == Integer.MAX_VALUE ? "Отсутствует" : minInteger) + "\n" +
                    "Мин. вещественное число - " + (minFloat == Float.MAX_VALUE ? "Отсутствует" : minFloat) + "\n" +
                    "Сумм. целых чисел - " + (sumOfIntegers == 0 ? "Отсутствует" : sumOfIntegers) + "\n" +
                    "Сумм. вещественных чисел - " + (sumOfFloats == 0 ? "Отсутствует" : sumOfFloats) + "\n" +
                    "Среднее целых чисел - " + (sumOfIntegers == 0 ? "Отсутствует" : sumOfIntegers / countOfIntegers) + "\n" +
                    "Среднее вещественных чисел - " + (sumOfFloats == 0 ? "Отсутствует" : sumOfFloats / countOfFloats) + "\n" +
                    "Самая длинная строка - " + (largestLineSize == Integer.MIN_VALUE ? "Отсутствует" : largestLineSize + " символа") + "\n" +
                    "Самая короткая строка - " + (smallestLineSize == Integer.MAX_VALUE ? "Отсутствует" : smallestLineSize + " символа"));
        } else {
            System.out.println("Краткая статистика:\n" +
                    "Кол-во целых чисел - " + countOfIntegers + "\n" +
                    "Кол-во вещественных чисел - " + countOfFloats + "\n" +
                    "Кол-во строковых данных - " + countOfStrings);
        }
    }
}
