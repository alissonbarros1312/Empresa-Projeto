package org.example.abstractclass;

public class Validacoes {
    public static boolean validaString(String dado) {
        return dado != null && !dado.trim().isEmpty();
    }

    public static boolean validaCPF(String cpf) {
        // verifico se é um dado nulo e se possui exatamente 11 digitos
        if (cpf == null || !cpf.matches("\\d{11}")) {
            System.out.println("CPF invalido");
            return false;
        }

        // verifica se todos os numeros sao iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            System.out.println("CPF não contem sequencia valida de numeros");
            return false;
        }
        return true;
    }
    public static boolean validaSalarioPositivo(double dado){
        return dado >= 0;
    }
    public static boolean validaEquipePositiva(int dado){
        return dado > 0;
    }
}
