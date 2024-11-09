package projeto_compiladores.tokens;

import java.text.CharacterIterator;

import projeto_compiladores.lexer.AFD;
import projeto_compiladores.lexer.Token;

public class MathOperator extends AFD{
    
    @Override
    public Token evaluate(CharacterIterator code){
        switch (code.current()){
            case '+':
                code.next();
                if (code.current() == '+'){
                    code.next();
                    return new Token("op_incrementar", "++");
                }
                return new Token("op_soma", "+");
            case '-':
                code.next();
                return new Token("op_subtracao", "-");
            case '*':
                code.next();
                return new Token("op_multiplicacao", "*");
            case '/':
                code.next();
                return new Token("op_divisao", "/");
            case '%':
                code.next();
                return new Token("op_restoDivisao", "%");
            case '=':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("op_igual", "==");
                }
                return new Token("op_atribuicao", "=");
            case '<':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("op_menorIgual", "<=");
                }
                return new Token("op_menor", "<");
            case '>':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("op_maiorIgual", ">=");
                }
                return new Token("op_maior", ">");
            case '!':
                code.next();
                if (code.current() == '=') {
                    code.next();
                    return new Token("op_diferente", "!=");
                }
            default:
                return null;
        }
    }
}
