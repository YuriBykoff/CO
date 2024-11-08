# Projeto Compilador

## Descrição
Este projeto implementa um compilador básico utilizando análise léxica e sintática para processar código-fonte. A estrutura do projeto segue a organização padrão de compiladores, com classes dedicadas à análise léxica e sintática. 

[Documentação ](https://github.com/YuriBykoff/CO/wiki)

## Estrutura do Projeto

### Diretórios e Arquivos:


- **src/**: Diretório contendo todo o código-fonte do projeto.
  - **AFD.java**: Implementa um Autômato Finito Determinístico (AFD), utilizado para reconhecer padrões no código-fonte, como identificadores e palavras reservadas.
  - **Decimal.java**: Responsável por reconhecer e processar números decimais no código-fonte.
  - **Lexer.java**: O analisador léxico, encarregado de transformar o código-fonte em uma sequência de tokens utilizando diferentes AFDs.
  - **MathOperator.java**: Implementa a lógica de reconhecimento de operadores matemáticos no código.
  - **OperacaoDelimitadores.java**: Responsável por identificar delimitadores (como parênteses, colchetes, etc.) no código-fonte.
  - **Parser.java**: O analisador sintático, que utiliza a sequência de tokens gerada pelo Lexer para validar a conformidade do código-fonte com a gramática definida.
  - **Projeto_Compiladores.java**: Classe principal do projeto, onde o processo de compilação é iniciado e coordenado.
  - **Token.java**: Define a estrutura de um token, que é uma unidade lexical identificada pelo Lexer, como palavras-chave, operadores ou identificadores.
  - **id.java**: Implementa a lógica de reconhecimento de identificadores no código-fonte.
  - **input_codigo.txt**: Um exemplo de código-fonte de entrada utilizado para testar o compilador.
  - **num_inteiro.java**: Classe responsável por reconhecer e processar números inteiros.
  - **reservado.java**: Classe que identifica palavras reservadas da linguagem no código-fonte.
  - **texto.java**: Responsável por reconhecer e processar literais de texto (strings) no código.


## Estrutura do Compilador
- **Analisador Léxico (Lexer)**: Responsável por transformar o código-fonte em tokens, utilizando Autômatos Finitos Determinísticos (AFDs) para reconhecer padrões.
- **Analisador Sintático (Parser)**: Responsável por verificar se a sequência de tokens gerada pelo Lexer segue as regras da gramática definida.

