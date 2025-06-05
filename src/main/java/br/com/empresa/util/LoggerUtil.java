package br.com.empresa.util;

public class LoggerUtil {
    public static void logErro(String contexto, Exception e){
        System.err.println("[ERRO]: " + contexto);
        System.err.println("MENSAGEM: " + e.getMessage());
    }

    public static void logInfo(String msg){
        System.out.println("[INFO] " + msg);
    }

    public static void logWarning(String msg){
        System.out.println("[WARN] " + msg);
    }
}
