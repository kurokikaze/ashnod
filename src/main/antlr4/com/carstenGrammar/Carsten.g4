grammar Carsten;

matcherRecord
    : matcherBlock
    | actionLine
    ;

matcherFile
    : '{' matcherRecord+ '}' EOF
    ;

matcherBlock
    : '[' matchRule=RULE ']' '{' actionBlock '}'
    ;

actionBlock
    : actionLine+
    ;

actionLine
    :   variable=ID '=' expr
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
    |   variable=ID
    ;

compareSymbol
    : OP_COMPARE_LESS
    | OP_COMPARE_MORE
    | OP_COMPARE_EQ
    | OP_COMPARE_NOTEQ
    ;

OP_COMPARE_LESS: '<';
OP_COMPARE_MORE: '>';
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
RULE: [a-zA-Z0-9="]+ | '//*';
WS  :   [ \t\r\n] -> channel(HIDDEN);
