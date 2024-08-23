package com.healeniumproxy.replacethelocators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocatorUpdateInClassFiles {

    public void updateLocatorsInDirectory(String directoryPath, String failedLocatorValue, String healedLocatorValue) {
        try (Stream<Path> filePathStream = Files.walk(Paths.get(directoryPath))) {
            List<Path> javaFiles = filePathStream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());

            for (Path filePath : javaFiles) {
                updateLocatorsInFile(filePath, failedLocatorValue, healedLocatorValue);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLocatorsInFile(Path filePath, String failedLocatorValue, String healedLocatorValue) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            boolean isUpdated = false;

            // Clean the failed locator value
            String cleanedFailedLocatorValue = cleanLocator(failedLocatorValue);

            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(cleanedFailedLocatorValue)) {
                    String updatedLine = lines.get(i).replace(cleanedFailedLocatorValue, healedLocatorValue);
                    lines.set(i, updatedLine);
                    isUpdated = true;
                }
            }

            if (isUpdated) {
                Files.write(filePath, lines);
                System.out.println("Updated locators in file: " + filePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String cleanLocator(String locator) {
        // Strip prefixes and escape characters
        String strippedLocator = stripPrefix(locator);
        return strippedLocator.replace("\\", "");
    }

    private String stripPrefix(String locator) {
        // Remove '#' for IDs and '.' for classes if they exist
        if (locator.startsWith("#") || locator.startsWith(".")) {
            return locator.substring(1);
        }
        return locator;
    }
}
