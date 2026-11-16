package br.edu.atitus.bibliodigital.repositories;

import java.util.ArrayList;
import java.util.List;

import br.edu.atitus.bibliodigital.entities.Livro;

public class LivroRepository {
    private List<Livro> acervo = new ArrayList<>();

    public List<Livro> findAll() {
        return acervo;
    }

    public void inserir(Livro livro) {
        acervo.add(livro);
    }

    public List<Livro> findByTitulo(String parametro) {

        List<Livro> resultado = new ArrayList<>();

        for (Livro livro : acervo) {
            if (livro.getTitulo().toUpperCase().contains(parametro.toUpperCase())) {
            resultado.add(livro);
            }
        }
        return resultado;
    }

    public List<Livro> findByAutor(String parametro) {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getAutor().toUpperCase().contains(parametro.toUpperCase())) {
                resultado.add(livro);
            }
        }
        return resultado;
    }

    public boolean existsByTitulo(String titulo) {
        for (Livro livro : acervo) {
           if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                   return true;
               }
            }
           return false;
        }

       public void alterar(int indice, Livro livroAtualizado) {
        acervo.set(indice, livroAtualizado);
       }

       public void excluir(int indice) {
        acervo.remove(indice);
       }

}
