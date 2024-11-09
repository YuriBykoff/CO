package projeto_compiladores.tokens;

import java.text.CharacterIterator;

import projeto_compiladores.lexer.AFD;
import projeto_compiladores.lexer.Token;

public class id extends AFD{
    
    @Override
    public Token evaluate(CharacterIterator code) {
        StringBuilder lexema = new StringBuilder();
        char currentChar = code.current();

        //Verifica se o primeiro caractere é uma letra ou underline
        if (Character.isLetter(currentChar) || currentChar == '_') {
            lexema.append(currentChar);
            code.next();

            //Continua lendo enquanto for letra, dígito ou underscore
            while (Character.isLetterOrDigit(code.current()) || code.current() == '_') {
                lexema.append(code.current());
                code.next();
            }

            return new Token("id", lexema.toString());
        }

        //Se não for um identificador válido, retorna null
        return null;
    }
}
