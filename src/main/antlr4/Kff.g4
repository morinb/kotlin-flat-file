grammar Kff;

configs : config+;

config : TYPE '{' entry+ '}';

entry : FIELD ':' NUMBER;

TYPE : CAPITAL_LETTER (CAPITAL_LETTER|LETTER|DIGIT)* ;

FIELD : LETTER (CAPITAL_LETTER|LETTER|DIGIT)* ;

NUMBER : DIGIT+ ;

LETTER : [a-z_] ;
CAPITAL_LETTER : [A-Z_] ;

DIGIT : [0-9] ;

WS : [ \t\r\n] -> skip;
