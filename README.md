# Projeto Compiladores - Analisador Léxico e Sintático

## Autores
- **Arthur Veloso** - RA: 22.221.038-7
- **Daniel Eiji** - RA: 22.121.131-1
- **Yuri Bykoff** - RA: 22.121.045-3

## Documentação

[Documentação ](https://github.com/YuriBykoff/CO/wiki)

## Estrutura do Projeto Java

### Diretórios Principais
```
src/projeto_compiladores/
├── input/                  # Diretório contendo arquivos de entrada
├── lexer/                  # Analisador Léxico
├── parser/                 # Analisador Sintático
└── tokens/                 # Definições dos Tokens
```

### Detalhamento dos Componentes

#### 1. Lexer (Analisador Léxico)
- Localizado em `src/projeto_compiladores/lexer/`
- Responsável pela análise léxica do código fonte
- Identifica e classifica os tokens da linguagem
- Principais componentes:
  - Reconhecimento de identificadores
  - Análise de números
  - Identificação de operadores
  - Tratamento de strings
  - Gerenciamento de palavras reservadas

#### 2. Parser (Analisador Sintático)
- Localizado em `src/projeto_compiladores/parser/`
- Realiza a análise sintática dos tokens
- Verifica a estrutura gramatical do código
- Implementa as regras da gramática da linguagem

#### 3. Tokens
- Localizado em `src/projeto_compiladores/tokens/`
- Define os tipos de tokens suportados
- Implementa a estrutura de dados para tokens
- Gerencia a classificação dos elementos léxicos

# Tutorial de Execução

## 1. Configuração do Arquivo de Input
1. Navegue até a pasta `src/projeto_compiladores/input`
2. Localize o arquivo `input_codigo.txt`
3. Copie o caminho completo deste arquivo
4. Navegue até `src/projeto_compiladores/`
5. Localize e abra o arquivo que contém o path do input
6. Substitua o caminho existente pelo caminho que você copiou do `input_codigo.txt`

## 2. Compilação do Java
1. Abra um terminal na pasta `src`
2. Execute o comando de compilação:
```bash
javac -d ../build projeto_compiladores/*.java projeto_compiladores/lexer/*.java projeto_compiladores/parser/*.java projeto_compiladores/tokens/*.java
```
3. Verifique se os arquivos foram compilados com sucesso na pasta `/build`

## 3. Configuração do Ambiente Python
1. Navegue até a pasta `flask`
2. Crie um ambiente virtual:
```bash
python -m venv .venv
```

3. Ative o ambiente virtual:

**Windows (Command Prompt)**:
```cmd
.venv\Scripts\activate.bat
```

**Windows (PowerShell)**:
```powershell
.venv\Scripts\Activate.ps1
```

**macOS/Linux**:
```bash
source .venv/bin/activate
```

## 4. Instalação das Dependências
Com o ambiente virtual ativado, instale as dependências:
```bash
pip install flask flask-socketio
```

## 5. Execução do Aplicativo
1. Execute o aplicativo Flask:
```bash
python app.py
```

2. Abra seu navegador e acesse:
```
http://127.0.0.1:5000/
```

3. O terminal web estará pronto para uso!

## Observações
- Certifique-se de que todas as pastas estão no caminho correto
- O ambiente virtual (.venv) deve estar ativado sempre que for executar o aplicativo
- Mantenha o terminal aberto enquanto estiver usando o aplicativo

## Estrutura Completa do Projeto
```
Projeto_Compiladores/
├── build/                  # Arquivos compilados
├── flask/                  # Interface web
│   └──  app.py             # Aplicação Flask
│  
├── src/
│   └── projeto_compiladores/
│       ├── input/ 
│       │   └── input_codigo.txt            # Arquivo de input para o compilador.
│       └── Projeto_Compiladores.java       # Start do compilador.
└── Main.java                               # Código traduzido apartir do compilador.
```
