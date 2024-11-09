package projeto_compiladores.tokens;

import java.text.CharacterIterator;

import projeto_compiladores.lexer.AFD;
import projeto_compiladores.lexer.Token;

public class reservado extends AFD {
    
    @Override
    public Token evaluate(CharacterIterator code) {
        StringBuilder lexema = new StringBuilder();
        
        //Realiza a leitura enquanto for letra
        while (Character.isLetter(code.current())) {
            lexema.append(code.current());
            code.next();
        }
        
        //Verifica se é uma palavra reservada
        String palavra = lexema.toString();
        switch (palavra) {
            case "inteiro":
                return new Token("reservado_declarado", palavra);
            case "decimal":
                return new Token("reservado_declarado", palavra);
            case "texto":
                return new Token("reservado_declarado", palavra);
            case "se":
                return new Token("reservado", palavra);
            case "senaoSe":
                return new Token("reservado", palavra);
            case "senao":
                return new Token("reservado", palavra);
            case "exibir":
                return new Token("reservado", palavra);
            case "exibirln":
                return new Token("reservado", palavra);
            case "para":
                return new Token("reservado", palavra);
            case "enquanto":
                return new Token("reservado", palavra);
            case "leia":
                return new Token("reservado", palavra);
            default:
                return null;
        }
    }
}