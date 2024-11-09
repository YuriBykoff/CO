package projeto_compiladores.lexer;

import java.util.List;

import projeto_compiladores.parser.OperacaoDelimitadores;
import projeto_compiladores.tokens.Decimal;
import projeto_compiladores.tokens.MathOperator;
import projeto_compiladores.tokens.id;
import projeto_compiladores.tokens.num_inteiro;
import projeto_compiladores.tokens.reservado;
import projeto_compiladores.tokens.texto;

import java.util.ArrayList;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class Lexer {
    private List<Token> tokens;
    private List<AFD> afds;
    private CharacterIterator code;
    
    public Lexer(String code){
        tokens = new ArrayList<>();
        afds = new ArrayList<>();
        this.code = new StringCharacterIterator(code);
        afds.add(new reservado());
        afds.add(new texto());
        afds.add(new OperacaoDelimitadores());
        afds.add(new Decimal());
        afds.add(new num_inteiro());
        afds.add(new MathOperator());
        afds.add(new id());
        
    }
    
    public void skipWhiteSpace(){
        while(code.current() == ' ' || code.current() == '\n'){
            code.next();
        }
    }
    
    public List<Token> getTokens(){
        boolean accepted;
        while(code.current() != CharacterIterator.DONE){
            accepted = false;
            skipWhiteSpace();
            if(code.current() == CharacterIterator.DONE)break;
            for(AFD afd: afds){
                int pos = code.getIndex();
                Token t = afd.evaluate(code);
                if (t != null){
                    accepted = true;
                    tokens.add(t);
                    break;
                }
                else{
                    code.setIndex(pos);
                }
            }
            if(accepted) continue;
            throw new RuntimeException("Token not recognized: " + code.current());
        }
        tokens.add(new Token("EOF", "$"));
        return tokens;
    }
}
