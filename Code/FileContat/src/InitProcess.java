package Code.FileContat.src;

import Code.FileContat.src.FileWork.Contat;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

/**
 * Запуск основных методов и вывод ответа на поставленную задачу.
 */
public class InitProcess {
    public void initProgram() {
        System.out.println(Define.ANSI_GREEN + "Insert directories path: " + Define.ANSI_RESET);
        Scanner getDirectoryScanner = new Scanner(System.in);
        String getDirectoryName = getDirectoryScanner.nextLine();
        Contat contat = new Contat(getDirectoryName);
        try {
            List<Path> pathsList = contat.getPathList();
            for (Path path : pathsList) {
                System.out.println(Define.ANSI_GREEN + path + Define.ANSI_RESET);
            }
            System.out.println(contat.contatenation());
            System.out.println(Define.ANSI_BLACK_BACKGROUND  +
                    "Program finished");
        } catch (IOException ex) {
            System.out.println(Define.ANSI_RED + "IO-Ecxeption! " + ex.getMessage() + "\n" +
                    Define.ANSI_RESET);
        } catch (Exception ex) {
            System.out.println(Define.ANSI_RED + "Cycle! Program made brrbrr " + ex.getMessage() + "\n" +
                    Define.ANSI_RESET);

        }
    }
}
