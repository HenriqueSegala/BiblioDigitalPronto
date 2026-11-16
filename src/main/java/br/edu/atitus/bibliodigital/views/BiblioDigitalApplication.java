package br.edu.atitus.bibliodigital.views;

import br.edu.atitus.bibliodigital.entities.AudioLivro;
import br.edu.atitus.bibliodigital.entities.Livro;
import br.edu.atitus.bibliodigital.entities.LivroDigital;
import br.edu.atitus.bibliodigital.entities.LivroFisico;
import br.edu.atitus.bibliodigital.services.LivroService;

import java.text.NumberFormat;
import java.util.List;

public class BiblioDigitalApplication {
    private final LivroService service = new LivroService();

    void main() {

        int opcao = -1;

        do {
            ConsoleUiApplication.mostrarMenu();
           try {
                opcao = lerInteiro("Digite a opção que deseja: ");

                switch (opcao) {
                    case 1 -> cadastrarLivro();
                    case 2 -> pesquisarPorTitulo();
                    case 3 -> pesquisarPorAutor();
                    case 4 -> alterarLivro();
                    case 5 -> excluirLivro();
                    case 0 -> IO.println("Saindo do BiblioDigital. Até logo!");
                    default -> IO.println("Opção inválida! Escolha uma opção entre 0 e 5");
                }
            } catch (RuntimeException err) {
               IO.println("Erro: " + err.getMessage());
           }
           IO.println();
        } while (opcao != 0);
    }

    private void cadastrarLivro() {
        IO.println("========== Cadastrar Novo Livro ==========");
        Livro novoLivro = lerDadosLivro();
        service.inserir(novoLivro);
        IO.println("Livro cadastrado com sucesso!");
    }

    private void pesquisarPorTitulo() {
        IO.println("========== Pesquisar Livro por Titulo ==========");
        String titulo = IO.readln("Informe o título ou parte dele: ");
        List<Livro> encontrados = service.findByTitulo(titulo);
        exibirResultadosPesquisa(encontrados);
    }

    private void pesquisarPorAutor() {
        IO.println("========== Pesquisar Livro por Autor ==========");
        String autor = IO.readln("Informe o nome do autor ou parte dele: ");
        List<Livro> encontrados = service.findByAutor(autor);
        exibirResultadosPesquisa(encontrados);
    }

    private void alterarLivro() {
        IO.println("========== Alterar Dados do Livro ==========");
        List<Livro> acervo = listarLivrosNumerados();

        int escolha = lerInteiro("\n Informe o índice do livro que deseja alterar: ");
        int indiceReal = escolha - 1;

        Livro livroAtual = service.findByIndice(indiceReal);
        IO.println("Dados atuais: " + livroAtual);
        IO.println("\nInforme os novos dados do livro: ");

        Livro livroAtualizado = lerDadosLivro();
        service.alterar(indiceReal, livroAtualizado);
        IO.println("Dados do livro alterados com sucesso!");
    }

    private void excluirLivro() {
        IO.println("========== Excluir Livro ==========");
        listarLivrosNumerados();

        int escolha = lerInteiro("\n Informe o índice do livro que deseja excluir: ");
        int indiceReal = escolha - 1;

        Livro livroSelecionado = service.findByIndice(indiceReal);
        IO.println("Livro selecionado: " + livroSelecionado);

        String confirmacao = IO.readln("Tem certeza que deseja excluir esse livro? (S/N): ").trim();
        if (confirmacao.equalsIgnoreCase("S")) {
            service.excluir(indiceReal);
            IO.println("Livro excluído com sucesso!");
        } else {
            IO.println("Operação de exclusão cancelada.");
        }
    }

    private List<Livro> listarLivrosNumerados() {
        List<Livro> acervo = service.findAll();
        if (acervo.isEmpty()) {
            throw new RuntimeException("Não há livros cadastrados no acervo.");
        }

        IO.println("========== Livros Cadastrados ==========");
        for (int i = 0; i < acervo.size(); i++) {
            IO.println("%d - %s".formatted(i + 1, acervo.get(i).getTitulo()));
        }
        return acervo;
    }

    private void exibirResultadosPesquisa(List<Livro> livros) {
        if (livros.isEmpty()) {
            IO.println("Nenhum livro encontrado para a pesquisa realizada.");
        } else {
            IO.println("\n========== Livros Encontrados ==========");
            for (Livro livro : livros) {
                IO.println(livro);
            }
        }
    }

    private Livro lerDadosLivro() {
        IO.println("""
                Selecione o tipo de livro:
                1 - Livro Físico
                2 - Livro Digital
                3 - Audiolivro""");
        int tipo = lerInteiro("Tipo: ");

        if (tipo < 1 || tipo > 3) {
            throw new RuntimeException("Tipo de livro inválido! Escolha 1, 2 ou 3.");
        }

        String titulo = IO.readln("Título: ");
        String autor = IO.readln("Autor: ");
        int anoPublicacao = lerInteiro("Ano de publicação: ");

        return switch(tipo) {
            case 1 -> {
                int quantidade = lerInteiro("Quantidade de exemplares: ");
                String localizacao = IO.readln("Localização (ex: Corredor A, Prateleira 2): ");
                yield new LivroFisico(titulo, autor, anoPublicacao, quantidade, localizacao);
            }

            case 2 -> {
                String formato = IO.readln("Formato (ex: PDF, EBUP): ");
                double tamanho = lerDouble("Tamanho do arquivo (MB): ");
                yield new LivroDigital(titulo, autor, anoPublicacao, formato, tamanho);
            }

            case 3 -> {
                int duracao = lerInteiro("Duração (em minutos): ");
                String narrador = IO.readln("Narrador: ");
                yield new AudioLivro(titulo, autor, anoPublicacao, duracao, narrador);
            }
            default -> throw new RuntimeException("Tipo de livro inválido.");
        };
    }
     private int lerInteiro(String mensagem) {
        try {
            return Integer.parseInt(IO.readln(mensagem).trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Valor inválido! Digite um número inteiro válido. ");
        }
     }

     private double lerDouble(String mensagem) {
        try {
            String entrada = IO.readln(mensagem).trim().replace(",", ".");
                    return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            throw new RuntimeException("valor inválido. Digite um número decimal vãlido.");
        }
     }
}

