package projeto_compiladores;

import java.text.CharacterIterator;

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
                return new Token("reservado", palavra);
            case "decimal":
                return new Token("reservado", palavra);
            case "texto":
                return new Token("resercado", palavra);
            case "se":
                return new Token("reservado", palavra);
            case "senao_se":
                return new Token("reservado", palavra);
            case "senao":
                return new Token("reservado", palavra);
            case "exibir":
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