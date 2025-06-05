package br.com.empresa.model;

import br.com.empresa.util.ValidacoesUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaModel {
    private List<FuncionarioModel> funcionarios = new ArrayList<>();
    private List<GerenteModel> gerentes = new ArrayList<>();

    public void adicionaFuncionario(FuncionarioModel pessoa) {
        if (ValidacoesUtil.validaPessoa(pessoa)) {
            funcionarios.add(pessoa);
        }
    }

    public void adicionaGerente(GerenteModel pessoa) {
        if (ValidacoesUtil.validaPessoa(pessoa)) {
            gerentes.add(pessoa);
        }
    }

    public void exibirFuncionarios() {
        for (FuncionarioModel pessoa : funcionarios) {
            pessoa.exibirInfo();
        }
    }

    public void exibirGerentes() {
        for (GerenteModel pessoa : gerentes) {
            pessoa.exibirInfo();
        }
    }

    public void salvarFuncionariosEmArquivo(String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (FuncionarioModel funcionario : funcionarios) {
                writer.write(funcionario.getNome() + ", " + funcionario.getCpf() + ", "
                        + funcionario.getSetor() + ", " + funcionario.getSalario());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao guardar funcionario: " + e.getMessage());
        }
    }

    public void salvarGerentesEmArquivo(String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (GerenteModel gerente : gerentes) {
                writer.write(gerente.getNome() + ", " + gerente.getCpf() + ", "
                        + gerente.getSetor() + ", " + gerente.getSalario() + ", " + gerente.getEquipe());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever gerente: " + e.getMessage());
        }
    }

    public void carregarFuncionariosDeArquivo(String caminho) {
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            System.out.println("Caminho do arquivo não encontrado");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(", ");

                if (partes.length == 4) {
                    String nome = partes[0];
                    String cpf = partes[1];
                    String setor = partes[2];
                    double salario = Double.parseDouble(partes[3]);

                    adicionaFuncionario(new FuncionarioModel(nome, cpf, setor, salario));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar funcionarios: " + e.getMessage());
        }
    }

    public void carregarGerentesDeArquivos(String caminho) {
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            System.out.println("Caminho do arquivo não encontrado");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(", ");

                if (partes.length == 5) {
                    String nome = partes[0];
                    String cpf = partes[1];
                    String setor = partes[2];
                    double salario = Double.parseDouble(partes[3]);
                    int equipe = Integer.parseInt(partes[4]);

                    adicionaGerente(new GerenteModel(nome, cpf, setor, salario, equipe));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar Gerente: " + e.getMessage());
        }
    }
}
