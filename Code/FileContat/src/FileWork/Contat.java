package Code.FileContat.src.FileWork;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс основных методов - получения списка зависимостей и контатенация файлов.
 */
public class Contat {
    private final String fullPath;
    private List<File> memory;
    public Contat(String str) {
        fullPath = str;
    }

    public String contatenation() throws IOException, Exception {
        return contatFiles(memory);
    }

    /**
     * Получение ПУТЕЙ файлов в порядке правила.
     * @return Список List<Path>.
     */
    public List<Path> getPathList() throws IOException, Exception {
        List<File> fileList = getFileList();
        memory = fileList;
        if(fileList.isEmpty()) {
            throw new IOException("FatalError?");
        }
        List<Path> pathList = new ArrayList<>();
        for (File file : fileList) {
            pathList.add(file.getCurrDirectory());
        }
        return pathList;
    }

    /**
     * Получение  файлов в порядке правила.
     * @return Список List<Path>.
     */
    private List<File> getFileList() throws IOException, Exception {
        File full = new File(fullPath);
        //Вообще все файлы.
        Set<File> filesSet = full.getFiles();
        Set<File> sortSet = new HashSet<>();
        List<File> filesList = new ArrayList<>();
        //Перекидываем из текущей папки файлы в "правильном" порядке.
        while (!filesSet.isEmpty()) {
            boolean flag = false;
            for (File file : filesSet) {
                if (sortSet.containsAll(file.getRequires())) {
                    flag = true;
                    filesList.add(file);
                    sortSet.add(file);
                    filesSet.remove(file);
                    break;
                }
            }
            // Если не смогли добавить, значит что-то уже лежало в Set, а это цикл.
            if (!flag) {
                throw new Exception("Найден цикл!");
            }
        }
        return filesList;
    }

    /**
     * Соединение текста файлов в одно целое.
     * @return Отправляет весь текст в Init и выкидывает в коносль.
     */
    private String contatFiles(List<File> files) throws IOException {
        StringBuilder txt = new StringBuilder();
        for (var currFile : files) {
            txt.append(currFile.getFileData());
        }
        return txt.toString();
    }

}
