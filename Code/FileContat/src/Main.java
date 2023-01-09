package Code.FileContat.src;
import Code.FileContat.src.Define;

public class Main {
    public static void main(String[] args) {
        System.out.println(Define.ANSI_BLUE + "Welcome!!! \nCheck ReadME before using this program.\n" +
                Define.ANSI_RESET);
        InitProcess startProgram = new InitProcess();
        startProgram.initProgram();
    }
}