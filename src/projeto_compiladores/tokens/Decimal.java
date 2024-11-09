package projeto_compiladores.tokens;

import java.text.CharacterIterator;

import projeto_compiladores.lexer.AFD;
import projeto_compiladores.lexer.Token;

public class Decimal extends AFD{
    
    @Override
     public Token evaluate(CharacterIterator code){
         if(Character.isDigit(code.current()) || code.current() == '.'){
             //Permite apenas 1 ponto
             boolean bool_ponto = false;
             
             //Caso seja um número, continua lendo o inteiro.
             String decimal = readNumber(code);
             
             //Verifica se é um ponto
             if (code.current() == '.' && bool_ponto == false){
                 bool_ponto = true;
                 decimal += code.current();
                 code.next();
             }
             
             //Verifica se o próximo é um numero
             if(Character.isDigit(code.current())){
                 while(Character.isDigit(code.current())){
                     decimal += code.current();
                     code.next();
                 }
                 
                 return new Token("op_decimal", decimal);
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
                 //code.current() == ';' ||
                 code.current() == '{' ||
                 code.current() == '\n' ||
                 code.current() == CharacterIterator.DONE;
     }
}
