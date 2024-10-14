package projeto_compiladores;

import java.text.CharacterIterator;

public class texto extends AFD{
    
    @Override
    public Token evaluate(CharacterIterator code){
        StringBuilder lexema = new StringBuilder();

        //Verifica se o primeiro caractere é uma aspas
        if (code.current() == '"') {
            lexema.append(code.current());
            code.next();

            //Realiza a leitura até encontrar a outra aspas.
            while(code.current() != '"' && code.current() != CharacterIterator.DONE){
                lexema.append(code.current());
                code.next();
            }
            
            if (code.current() == '"') {
                lexema.append(code.current());
                code.next();
            }
            
            return new Token("texto", lexema.toString());
        }

        //Se não for um identificador válido, retorna null
        return null;
    }
}
