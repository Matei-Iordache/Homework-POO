package main;

import common.Constants;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Use this if you want to test on a specific input file
 */
public final class Test {
    /**
     * for coding style
     */
    private Test() {
    }

    /**
     * Main method
     * @param args parameters
     * @throws IOException in case of error
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        File[] inputDir = directory.listFiles();

        if (inputDir != null) {
            Arrays.sort(inputDir);

            Scanner scanner = new Scanner(System.in);
            String fileName = scanner.next();
            for (File file : inputDir) {
                if (file.getName().equalsIgnoreCase(fileName)) {
                    Main.action(file.getAbsolutePath(), Constants.OUT_FILE);
                    break;
                }
            }
        }
    }
}
