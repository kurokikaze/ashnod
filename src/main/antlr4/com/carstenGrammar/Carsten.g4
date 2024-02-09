grammar Carsten;

matcherFile
    : '{' matcherRecord+ '}' EOF
    ;

matcherRecord
    : matcherBlock # matcherRuleBlock
    | actionLine # matcherAction
    ;

matcherBlock
    : '[' comparativeExpr ']' '{' actionBlock '}'
    ;

actionBlock
    : actionLine+
    ;

actionLine
    :   variable=ID op=OP_COMPARE_EQ expr
    |   variable=ID '=' value=STRING
    ;

expr
    :   LPAREN expr RPAREN                   # parensExpr
    |   op=('+'|'-') expr                    # unaryExpr
    |   left=expr op=(OP_MUL|OP_DIV) right=expr    # infixExpr
    |   left=expr op=(OP_ADD|OP_SUB) right=expr    # infixExpr
    |   func=ID LPAREN operand=expr RPAREN         # funcExpr
    |   atom                            # numberExpr
    ;

atom
    :   value=NUM
    |   strValue=STRING
    |   variable=ID
    ;

comparativeExpr
    : DEFAULT_SELECTOR  # defaultSelector
    | left=expr compare=compareSymbol right=expr # comparator
    | expr # soloExpr
    ;

compareSymbol
    : OP_COMPARE_LESS
    | OP_COMPARE_MORE
    | OP_COMPARE_LESS_OR_EQ
    | OP_COMPARE_MORE_OR_EQ
    | '='
    | OP_COMPARE_NOTEQ
    ;

DEFAULT_SELECTOR: '//*';
OP_COMPARE_LESS: '<';
OP_COMPARE_MORE: '>';
OP_COMPARE_LESS_OR_EQ: '<=';
OP_COMPARE_MORE_OR_EQ: '>=';
OP_COMPARE_EQ: '=';
OP_COMPARE_NOTEQ: '!=';

BOOLEAN_OPERATION
    : BOOLEAN_OR
    | BOOLEAN_AND
    ;

BOOLEAN_NOT: 'not';
BOOLEAN_OR: 'or';
BOOLEAN_AND: 'and';

OP_ADD: '+';
OP_SUB: '-';
OP_MUL: '*';
OP_DIV: '/';

LPAREN: '(';
RPAREN: ')';

NUM :   [0-9]+;
ID  :   [_a-zA-Z]+;
STRING: '"' (~[\r\n"] | '""')* '"';
WS  :   [ \t\r\n] -> channel(HIDDEN);