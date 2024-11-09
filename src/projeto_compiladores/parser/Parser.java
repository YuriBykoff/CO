package projeto_compiladores.parser;

import projeto_compiladores.lexer.Token;

import java.io.FileNotFoundException;
import java.util.List;

import java.io.PrintWriter;

public class Parser {
    
    List<Token> tokens;
    Token token;
    String traducao_declaracao;
    
    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }
    
    public Token nextToken(){
        if(tokens.size() > 0){
            return tokens.remove(0);
        }
        else{
            return null;
        }
    }
    
    public void erro(String regra){
        System.out.println("Regra: " + regra);
        System.out.println("token invalido: " + token);
        System.exit(0);
    }
    
    public void main(){
        try{
            PrintWriter escritor = new PrintWriter("Main.java");
            header(escritor);
        
            token = nextToken();
            bloco(escritor);
            if (token.tipo == "EOF"){
            //System.out.println("sintaticamente correto");
            }
            else{
                erro("EOF");
            }
        
            traduz("}", escritor);
            traduz("}", escritor);
        
            escritor.close();
        } 
        catch (FileNotFoundException e) {
            System.err.println("Erro ao tentar abrir o arquivo: " + e.getMessage());
        }
    }

    private void header(PrintWriter escritor) {
        //Adiciona o início da tradução.
        traduz("import java.util.Scanner;", escritor);
        traduz("public class Main {", escritor);
        traduz("public static void main(String[] args) {", escritor);
        traduz("Scanner scanner = new Scanner(System.in);", escritor);
    }
    
    public boolean bloco(PrintWriter escritor){
        if (token.lexema.equals("se")){
            if (se(escritor) && bloco(escritor)){
                return true;
            }
            erro("se");
            return false;
        }
        else if (token.lexema.equals("enquanto")){
            if (enquanto(escritor) && bloco(escritor)){
                return true;
            }
            erro("enquanto");
            return false;
        }
        
        else if (token.lexema.equals("para")){
            if (para(escritor) && bloco(escritor)){
                return true;
            }
            erro("para");
            return false;
        }
        
        else if (token.lexema.equals("exibir")){
            if (exibir(escritor) && bloco(escritor)){
                return true;
            }
            erro("exibir");
            return false;
        }
        else if (token.lexema.equals("exibirln")){
            if (exibirln(escritor) && bloco(escritor)){
                return true;
            }
            erro("exibirln");
            return false;
        }
        
        else if (token.tipo.equals("reservado_declarado")){
            if (reservado(escritor) && bloco(escritor)){
                return true;
            }
            erro("reservado_declarado");
            return false;
        }
        
        else if (token.tipo.equals("id")){
            if (atribuicao(escritor) && bloco(escritor)){
                return true;
            }
            erro("atribuição");
            return false;
        }
        return true;
    }
    
    public boolean se(PrintWriter escritor){
        if (matchL("se", "if", escritor) && condicao(escritor) && op_condicional(escritor) && matchL("{", "{", escritor) && opcaoChaves(escritor) && matchL("}", "}", escritor) && senaose(escritor) && senao(escritor)){
            return true;
        }
        erro("se");
        return false;
    }
    
    public boolean enquanto(PrintWriter escritor){
        if (matchL("enquanto", "while", escritor) && matchT("id", "(" + token.lexema, escritor) && comparacao(escritor) && valor(escritor) && matchL("{", ")" + "{", escritor) && opcaoChaves(escritor) && matchL("}", "}", escritor)){
            return true;
        }
        erro("enquanto");
        return false;
    }
    
    public boolean exibir(PrintWriter escritor){
        if (matchL("exibir", "System.out.print", escritor) && matchL("(", "(", escritor) && opcaoExibir(escritor) && matchL(")", ")", escritor) && matchL(";", ";", escritor)){
            return true;
        }
        erro("exibir");
        return false;
    }
    
    public boolean exibirln(PrintWriter escritor){
        if (matchL("exibirln", "System.out.println", escritor) && matchL("(", "(", escritor) && opcaoExibir(escritor) && matchL(")", ")", escritor) && matchL(";", ";", escritor)){
            return true;
        }
        erro("exibirln");
        return false;
    }
    
    public boolean opcaoExibir(PrintWriter escritor){
        if (token.tipo == "id"){
            if (matchT("id", token.lexema, escritor)){
                return true;
            }
        }
        else if (token.tipo == "texto"){
            if (matchT("texto", token.lexema, escritor)){
                return true;
            }
        }
        erro("opcaoExibir");
        return false;
    }
    
    public boolean para(PrintWriter escritor){
        if (matchL("para", "for", escritor) && matchT("id", "(" + token.lexema, escritor) && matchL("=", "=", escritor) && matchT("num_inteiro", token.lexema, escritor) && matchL(";", ";", escritor) && matchT("id", token.lexema, escritor) && comparacao(escritor) && valor(escritor) && matchL(";", ";", escritor) && atribuicao_for(escritor) && matchL("{", ")" + "{", escritor) && opcaoChaves(escritor) && matchL("}", "}", escritor)){
            return true;
        }
        erro("para");
        return false;
    }
    public boolean atribuicao_for(PrintWriter escritor){
        if (matchT("id", token.lexema, escritor) && matchL("=", "=", escritor) && expressao(escritor)){
            return true;
        }
        erro("atribuicao_for");
        return false;
    }
    
    public boolean reservado(PrintWriter escritor){
        if (token.lexema.equals("inteiro")){
            traducao_declaracao = "int";
        }
        if (token.lexema.equals("decimal")){
            traducao_declaracao = "double";
        }
        if (token.lexema.equals("texto")){
            traducao_declaracao = "String";
        }
        
        if (matchT("reservado_declarado", traducao_declaracao, escritor) && matchT("id", token.lexema, escritor) && matchL(";", ";", escritor)){
            return true;
        }
        erro("reservado");
        return false;
    }
    
    public boolean condicao(PrintWriter escritor){
        if (matchT("id", "(" + token.lexema, escritor) && comparacao(escritor) && valor_string(escritor)){
            return true;
        }
        erro("condicao");
        return false;
    }
    
    public boolean comparacao(PrintWriter escritor){
        if (matchL("==", "==", escritor) || matchL(">=", ">=", escritor) || matchL("<=", "<=", escritor) || matchL(">", ">", escritor) || matchL("<", "<", escritor) || matchL("!=", "!=", escritor)){
            return true;
        }
        erro("comparacao");
        return false;
    }
    
    public boolean valor(PrintWriter escritor){
        if (matchT("op_decimal", token.lexema, escritor) || matchT("num_inteiro", token.lexema, escritor) || matchT("id", token.lexema, escritor)){
            return true;
        }
        erro("valor");
        return false;
    }
    
    public boolean valor_string(PrintWriter escritor){
        if (matchT("id", token.lexema + ")", escritor) || matchT("op_decimal", token.lexema + ")", escritor) || matchT("num_inteiro", token.lexema + ")", escritor) || matchT("texto", token.lexema, escritor)){
            return true;
        }
        erro("valor_string");
        return false;
    }
    
    public boolean op_condicional(PrintWriter escritor){
        if (matchL("e", "&&", escritor) && condicao(escritor) && op_condicional(escritor) || matchL("ou", "||", escritor) && condicao(escritor) && op_condicional(escritor)){
            return true;
        }
        return true;
    }
    
    public boolean opcaoChaves(PrintWriter escritor){
        if (token.lexema.equals("se") || token.lexema.equals("enquanto") || token.lexema.equals("exibir") || token.lexema.equals("para") || token.lexema.equals("exibirln") || token.tipo.equals("id")){
            if (bloco(escritor)){
                return true;
            }
            else{
                erro("opcaoChaves");
                return false;
            }
        }
        
        erro("opcaoChaves");
        return false;
    }
    
    public boolean atribuicao(PrintWriter escritor){
        if (matchT("id", token.lexema, escritor) && matchL("=", "=", escritor) && opcaoAtribuicao(escritor) && matchL(";", ";", escritor)){
            return true;
        }
        erro("atribuicao");
        return false;
    }
    
    public boolean opcaoAtribuicao(PrintWriter escritor){
        if (token.lexema.equals("leiaLinha")){
            if (matchL("leiaLinha", "scanner.nextLine", escritor) && matchL("(", "(", escritor) && matchL(")", ")", escritor)){
                return true;
            }
        }
        else if (token.lexema.equals("leiaInteiro")){
            if (matchL("leiaInteiro", "scanner.nextInt", escritor) && matchL("(", "(", escritor) && matchL(")", ")", escritor)){
                return true;
            }
        }
        else if (token.lexema.equals("leiaDecimal")){
            if (matchL("leiaDecimal", "scanner.nextDouble", escritor) && matchL("(", "(", escritor) && matchL(")", ")", escritor)){
                return true;
            }
        }
        else if (token.tipo.equals("num_inteiro") || token.tipo.equals("op_decimal") || token.tipo.equals("id")){
            if (expressao(escritor)){
                return true;
            }
        }
        erro("opcaoAtribuicao");
        return false;
    }
    
    public boolean expressao(PrintWriter escritor){
        if (termo(escritor) && expressao_L(escritor)){
            return true;
        }
        erro("expressao");
        return false;
    }
    
    public boolean expressao_L(PrintWriter escritor){
        if ((matchL("+", "+", escritor) && termo(escritor) && expressao_L(escritor)) || (matchL("-", "-", escritor) && termo(escritor) && expressao_L(escritor))){
            return true;
        }
        return true;
    }
    
    public boolean termo(PrintWriter escritor){
        if (fator(escritor) && termo_L(escritor)){
            return true;
        }
        erro("termo");
        return false;
    }
    
    public boolean fator(PrintWriter escritor){
        if ((matchL("(", "(", escritor) && expressao(escritor) && matchL(")", ")", escritor)) || valor(escritor)){
            return true;
        }
        erro("fator");
        return true;
    }
    
    public boolean termo_L(PrintWriter escritor){
        if ((matchL("*", "*", escritor) && fator(escritor) && termo_L(escritor)) || matchL("/", "/", escritor) && fator(escritor) && termo_L(escritor)){
            return true;
        }
        return true;
    }
    
    public boolean senaose(PrintWriter escritor){
        if (matchL("senaoSe", "else", escritor) && condicao(escritor) && op_condicional(escritor) && matchL("{", "{", escritor) && opcaoChaves(escritor) && matchL("}", "}", escritor) && senao(escritor)){
            return true;
        }
        return true;
    }
    
    public boolean senao(PrintWriter escritor){
        if (matchL("senao", "else", escritor) && matchL("{", "{", escritor) && opcaoChaves(escritor) && matchL("}", "}", escritor)){
            return true;
        }
        return true;
    }
    
    public void traduz(String code, PrintWriter escritor){
        System.out.print(code + ' ');
        
        escritor.print(code + ' ');
        
    }
    
    public boolean matchL(String lexema, String newcode, PrintWriter escritor){
        if (token.lexema.equals(lexema)){
            traduz(newcode, escritor);
            token = nextToken();
            return true;
        }
        return false;
    }
    
    public boolean matchT(String tipo, String newcode, PrintWriter escritor){
        if (token.tipo.equals(tipo)){
            traduz(newcode, escritor);
            token = nextToken();
            return true;
        }
        return false;
    }
}
