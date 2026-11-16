package br.edu.atitus.bibliodigital.views;

public class ConsoleUiApplication {
    static public void mostrarMenu() {
    IO.println("""
========== BIBLIODIGITAL ==========
1 - Cadastrar livro
2 - Pesquisar livro por Titulo
3 - Pesquisar livro por Autor
4 - Alterar dados do livro
5 - Excluir livro
0 - Sair
===================================
""");
    }
}