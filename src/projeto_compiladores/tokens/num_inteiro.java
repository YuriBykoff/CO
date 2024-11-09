package projeto_compiladores.tokens;

import java.text.CharacterIterator;

import projeto_compiladores.lexer.AFD;
import projeto_compiladores.lexer.Token;

public class num_inteiro extends AFD{
    
    @Override
     public Token evaluate(CharacterIterator code){
         if(Character.isDigit(code.current())){
             String number = readNumber(code);
             
             if(endNumber(code)){
                 return new Token("num_inteiro", number);
             }
         }
         return null;
     }
     
     private String readNumber(CharacterIterator code){
         String number = "";
         
         while(Character.isDigit(code.current())){
             number+= code.current();
             code.next();
         }
         return number;
     }
     
     private boolean endNumber(CharacterIterator code){
         return code.current() == ' ' || 
                 code.current() == '+' || 
                 code.current() == '-' ||
                 code.current() == '*' ||
                 code.current() == '/' ||
                 code.current() == '%' ||
                 code.current() == ')' ||
                 code.current() == ';' ||
                 code.current() == '{' ||
                 code.current() == '\n' ||
                 code.current() == CharacterIterator.DONE;
     }
}
