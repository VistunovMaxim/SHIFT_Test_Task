package org.example;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<File> inputFiles = new ArrayList<>();

        for (String fileName : args) {
            if (fileName.contains(".txt"))
                inputFiles.add(new File(fileName));
        }

        Options options = new Options();
        options.addOption("o", true, "Путь");
        options.addOption("p", true, "Префикс");
        options.addOption("a", false, "Добавление");
        options.addOption("s", false, "Краткая статистика");
        options.addOption("f", false, "Полная статистика");
        options.addOption("h", false, "Все опции утилиты");

        CommandLineParser parser = new DefaultParser();
        UtilToFilterFiles utilToFilterFiles = new UtilToFilterFiles();

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("o")) {
                utilToFilterFiles.setPath(cmd.getOptionValue("o"));
            }
            if (cmd.hasOption("p")) {
                utilToFilterFiles.setPrefix(cmd.getOptionValue("p"));
            }
            if (cmd.hasOption("a")) {
                utilToFilterFiles.setAppend(true);
            }
            if (cmd.hasOption("s")) {
                utilToFilterFiles.setFullStats(false);
            }
            if (cmd.hasOption("f")) {
                utilToFilterFiles.setFullStats(true);
            }
            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("main", options);
            }

            if (inputFiles.isEmpty())
                throw new IOException("Не было обнаружено файлов для фильтрации.");

            utilToFilterFiles.filterFiles(inputFiles);

        } catch (ParseException e) {
            System.err.println("Необходимо указать аргумент опции - (" + e.getMessage() + ").");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
