package projeto_compiladores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
// teste
import projeto_compiladores.lexer.Lexer;
import projeto_compiladores.lexer.Token;
import projeto_compiladores.parser.Parser;

public class Projeto_Compiladores {

    public static void main(String[] args) {
        //Realiza a leitura do arquivo que terá o código.

        String nomeArquivo = "C:/Users/Yuri/Downloads/Projeto Final/Projeto_Compiladores/src/projeto_compiladores/input/input_codigo.txt";
        StringBuilder codigo = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                codigo.append(linha).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }
        
        List<Token> tokens = null;
        Lexer lexer = new Lexer(codigo.toString());
        tokens = lexer.getTokens();
        
        Parser parser = new Parser(tokens);
        parser.main();
    }
}
