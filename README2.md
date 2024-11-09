
# ALTERAR O CAMINHO DO INPUT (NO MOMENTO ESTA EM CAMINHO ABSOLUTO)

```
Projeto_Compiladores/
├── build/
├── flask/
├── Main.java
└── src
```

- Abra um terminal dentro da pasta **src** e execute o comando

```
javac -d ../build projeto_compiladores/*.java projeto_compiladores/lexer/*.java projeto_compiladores/parser/*.java projeto_compiladores/tokens/*.java
```
---
