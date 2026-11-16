package br.edu.atitus.bibliodigital.services;

import br.edu.atitus.bibliodigital.entities.AudioLivro;
import br.edu.atitus.bibliodigital.entities.Livro;
import br.edu.atitus.bibliodigital.entities.LivroDigital;
import br.edu.atitus.bibliodigital.entities.LivroFisico;
import br.edu.atitus.bibliodigital.repositories.LivroRepository;

import java.util.List;

public class LivroService {
    private final LivroRepository repository = new LivroRepository();

    public void inserir(Livro livro) {
        validarDadosComuns(livro);

        if (repository.existsByTitulo(livro.getTitulo())) {
            throw new RuntimeException("Já existe um livro cadastrado com esse titulo!");
        }
        repository.inserir(livro);
    }

    public List<Livro> findAll() {
        return repository.findAll();
    }

    public List<Livro> findByTitulo(String parametro) {
        if (parametro == null || parametro.isBlank()) {
            throw new RuntimeException("O titulo para a pesquisa NÃO pode estar vazio.");
        }
        return repository.findByTitulo(parametro.trim());
    }

    public List<Livro> findByAutor(String parametro) {
        if (parametro == null || parametro.isBlank()) {
            throw new RuntimeException("O nome do autor para pesquisa NÃO pode estar vazio.");
        }
        return repository.findByAutor(parametro.trim());
    }

    public Livro findByIndice(int indice) {
        validarIndice(indice);
        return repository.findAll().get(indice);
    }

    public void alterar (int indice , Livro livroAtualizado) {
        validarIndice(indice);
        validarDadosComuns(livroAtualizado);

        List<Livro> acervo = repository.findAll();
        for (int i = 0; i < acervo.size(); i++) {
            if (i != indice && acervo.get(i).getTitulo().equalsIgnoreCase(livroAtualizado.getTitulo())){
                throw new RuntimeException("Já existe outro livro cadastrado com esse titulo.");
            }
        }
        repository.alterar(indice, livroAtualizado);
    }

    public void excluir(int indice) {
        validarIndice(indice);
        repository.excluir(indice);
    }

    private void validarIndice(int indice) {
        List<Livro> acervo = repository.findAll();

        if (acervo.isEmpty()) {
            throw new RuntimeException("Não possuem livros cadastrados.");
        }

        if (indice < 0 || indice >= acervo.size()) {
            throw new RuntimeException("Índice inválido. Selecione um número correspondente à listagem.");
        }
    }

    private void validarDadosComuns(Livro livro) {
        if (livro == null) {
            throw new RuntimeException("O livro NÃO pode ser nulo");
        }

        if (livro.getTitulo() == null || livro.getTitulo().isBlank()) {
            throw new RuntimeException("O titulo do livro NÃO pode estar vazio.");
        }

        if (livro.getAutor() == null || livro.getAutor().isBlank()) {
            throw new RuntimeException("O autor do livro NÃO pode estar vazio.");
        }

        if (livro.getAnoPublicacao() < 1900) {
            throw new RuntimeException("O ano de publicação NÃO pode ser inferior a 1900.");
        }

        livro.setTitulo(livro.getTitulo().trim());
        livro.setAutor(livro.getAutor().trim());

        if (livro instanceof LivroFisico fisico && fisico.getLocalizacao() != null) {
            fisico.setLocalizacao(fisico.getLocalizacao().trim());
        } else if (livro instanceof LivroDigital digital && digital.getFormato() != null) {
            digital.setFormato(digital.getFormato().trim());
        } else if (livro instanceof AudioLivro audio && audio.getNarrador() != null) {
            audio.setNarrador(audio.getNarrador().trim());
        }

    }
}
