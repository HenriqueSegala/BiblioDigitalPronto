<div align="center">

# 📚 BiblioDigital

**Sistema de Gerenciamento de Acervo Bibliográfico em Java**

![Java](https://img.shields.io/badge/Java-25%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Arquitetura](https://img.shields.io/badge/Arquitetura-Em%20Camadas-2EA44F?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Concluído-blue?style=for-the-badge)

*Projeto acadêmico desenvolvido para a disciplina de Programação Orientada a Objetos — Atitus Educação.*

</div>

---

## 👨‍💻 Autor

* **Nome:** Henrique Segalla
* **RA:** 1139783

---

## 📖 Visão Geral

O **BiblioDigital** é uma aplicação de linha de comando (CLI) desenvolvida para gerenciar o cadastro, consulta, atualização e remoção de obras literárias em memória. O projeto foi estruturado aplicando os pilares da **Programação Orientada a Objetos** (abstração, herança, encapsulamento e polimorfismo) aliados a uma **arquitetura em camadas** bem definida.

---

## ⚙️ Funcionalidades e Regras de Negócio

| Operação | Descrição | Validações Aplicadas |
| :--- | :--- | :--- |
| **1. Cadastrar Livro** | Adiciona um **Livro Físico**, **Livro Digital** ou **Audiolivro** ao acervo. | Impede títulos duplicados; exige título/autor preenchidos; ano $\ge 1900$; valida campos específicos de cada tipo. |
| **2. Pesquisar por Título** | Filtra obras pelo título completo ou parcial. | Busca *case-insensitive* (ignora maiúsculas/minúsculas) e espaços extras. |
| **3. Pesquisar por Autor** | Filtra obras pelo nome completo ou parcial do autor. | Busca *case-insensitive* e parcial. |
| **4. Alterar Livro** | Exibe o acervo numerado e atualiza os dados do item escolhido. | Valida o índice informado e reaplica todas as regras de unicidade e integridade. |
| **5. Excluir Livro** | Remove um exemplar do acervo pelo índice na listagem. | Exige confirmação explícita (`S/N`) antes de efetivar a remoção. |

---

## 🧩 Modelo de Domínio (Polimorfismo)

A classe abstrata `Livro` define os atributos comuns (`titulo`, `autor`, `anoPublicacao`) e serve de base para três especializações:

* 📕 **`LivroFisico`**: adiciona `quantidadeExemplares` e `localizacao` (corredor/prateleira).
* 📱 **`LivroDigital`**: adiciona `formatoArquivo` (ex.: PDF, EPUB) e `tamanhoArquivoMB`.
* 🎧 **`AudioLivro`**: adiciona `duracaoMinutos` e `narrador`.

---

## 🏗️ Estrutura de Pacotes

```text
src/main/java/br/edu/atitus/bibliodigital/
├── entities/       # Entidades de domínio e herança (Livro e subclasses)
├── repositories/   # Armazenamento e manipulação da lista em memória
├── services/       # Regras de negócio, validações e filtros de busca
└── views/          # Menus, leitura de dados via console e tratamento de erros
```

---

## 🚀 Como Executar

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git](https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git)
   ```
2. **Abra no IntelliJ IDEA** (ou outra IDE configurada com JDK 25+).
3. **Execute a aplicação** iniciando pela classe `BiblioDigitalApplication.java`.