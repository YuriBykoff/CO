package projeto_compiladores;

import java.text.CharacterIterator;

public class OperacaoDelimitadores extends AFD{
    
    @Override
    public Token evaluate(CharacterIterator code){
        switch (code.current()){
            case '(':
                code.next();
                return new Token("abre_parenteses", "(");
            case ')':
                code.next();
                return new Token("fecha_parenteses", ")");
            case '{':
                code.next();
                return new Token("abre_chaves", "{");
            case '}':
                code.next();
                return new Token("fecha_chaves", "}");
            case ';':
                code.next();
                return new Token("ponto_virgula", ";");
            default:
                return null;
        }
    }
}
