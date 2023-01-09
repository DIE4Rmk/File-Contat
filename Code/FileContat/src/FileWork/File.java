package Code.FileContat.src.FileWork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Объект - файл/директория и связанные с ними методы.
 */
class File {
    //Root folder
    private final Path mainDirectory;

    //Instead of root folder we use current parent.
    private final Path currDirectory;

    //For working with directory.
    public File(String str1) {
        mainDirectory = Path.of(str1);
        currDirectory = Path.of("");
    }

    public File(String str1, String str2) {
        mainDirectory = Path.of(str1);
        currDirectory = Path.of(str2);
    }

    Path getMainDirectory() throws IOException{
        Path path = new java.io.File(mainDirectory.toFile(), currDirectory.toString()).toPath();
        if(path.isAbsolute()) {
            return path;
        } else {
            throw new IOException("FatalError");
        }
    }

    Path getCurrDirectory() {
        return currDirectory;
    }

    /**
     * получение списка зависимостей.
     * @return
     * @throws IOException
     * @throws Exception
     */
    Set<File> getRequires() throws IOException, Exception {
        List<String> linesText = Files.readAllLines(getMainDirectory());
        Set<File> requires = new HashSet<>();
        for (var thisLine : linesText) {
            //Вырезаем условие зависимости.
            if (thisLine.startsWith("require ‘") && thisLine.endsWith("’")) {
                String path = thisLine.substring(9, thisLine.length() - 1);
                File dependency = new File(currDirectory.toString(), path);
                requires.add(dependency);
            }
        }
        return requires;
    }

    /**
     * Получение сета всех файлов корневой директории.
     * @return Set<FILE>
     */
    Set<File> getFiles() throws IOException {
        try {
            Set<Path> allPaths = Files.walk(mainDirectory).
                    filter(filePath -> !Files.isDirectory(filePath)).
                    collect(Collectors.toSet());
            Set<File> allFiles = new HashSet<>();
            for (Path filePath : allPaths) {
                allFiles.add(new File(mainDirectory.toString(), mainDirectory.relativize(filePath).toString()));
            }
            return allFiles;
        } catch (IOException e) {
            throw e;
        }

    }

    String getFileData() throws IOException {
        return Files.readString(getMainDirectory());
    }

    /**
     * Нужные переопределения для getFileList, getFiles, getRequires
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (hashCode() != obj.hashCode()) {
            return false;
        }
        return currDirectory.equals(((File) obj).currDirectory);
    }

    @Override
    public int hashCode() {
        return currDirectory.hashCode();
    }

    @Override
    public String toString() {
        return currDirectory.toString();
    }
}
