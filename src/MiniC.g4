grammar MiniC;


@header { 
package generated;
}
program	: decl+			;
decl		: var_decl		
		| fun_decl		;
var_decl	:  type_spec IDENT ';' 
		| type_spec IDENT '=' LITERAL ';'	
		| type_spec IDENT '[' LITERAL ']' ';'	
		| THIS '.' IDENT '=' LITERAL ';'
		| type_spec '[' ']' '=' NEW type_spec'[' DecimalConstant ']' ;
type_spec	: VOID				
		| INT					
		| FLOAT	;
fun_decl	: type_spec IDENT '(' params ')' compound_stmt ;
params		: param (',' param)*		
		| VOID				
		|			;
param		: type_spec IDENT		
		| type_spec IDENT '[' ']'	;
stmt		: expr_stmt			
		| compound_stmt			
		| if_stmt			
		| while_stmt			
		| for_stmt
		| return_stmt			;
expr_stmt	: expr ';'			;
while_stmt	: WHILE '(' expr ')' stmt	;
// for문을 정의
for_stmt	: FOR '(' local_decl ';' expr ';' expr ')' compound_stmt	;
compound_stmt: '{' local_decl* stmt* '}'	;
local_decl	: type_spec IDENT ';'
		| type_spec IDENT '=' LITERAL ';'	
		| type_spec IDENT '[' LITERAL ']' ';'	;
if_stmt		: IF '(' expr ')' stmt		
		| IF '(' expr ')' stmt ELSE stmt 		;
return_stmt	: RETURN ';'			
		| RETURN expr ';'				;
expr	:  LITERAL				
	| '(' expr ')'	
	| LITERAL
	| IDENT				 
	| IDENT '[' expr ']'			 
	| IDENT '(' args ')'			
	| '-' expr				 
	| '+' expr				 
	| '--' expr				 
	| '++' expr				 
	| expr '*' expr				 
	| expr '/' expr				 
	| expr '%' expr				 
	| expr '+' expr				 
	| expr '-' expr				 
	| expr EQ expr				
	| expr NE expr				 
	| expr LE expr				 
	| expr '<' expr				 
	| expr GE expr				 
	| expr '>' expr				 
	| '!' expr					 
	| expr AND expr				 
	| expr OR expr				
	| IDENT '=' expr			
	| IDENT '[' expr ']' '=' expr		;
args	: expr (',' expr)*			 
	|					 ;

VOID: 'void';
INT: 'int';
// 새롭게 정의
FLOAT: 'float';
NEW: 'new';
FOR: 'for'; 

WHILE: 'while';
IF: 'if';
ELSE: 'else';
RETURN: 'return';
OR: 'or';
AND: 'and';
LE: '<=';
GE: '>=';
EQ: '==';
NE: '!=';

IDENT  : [a-zA-Z_]
        (   [a-zA-Z_]
        |  [0-9]
        )*;

// float형 추가
LITERAL:   DecimalConstant     |   OctalConstant     |   HexadecimalConstant    | FLOAT_REGEX ;

FLOAT_REGEX : DecimalConstant '.'[0-9]+;

DecimalConstant
    :   '0'
	|   [1-9] [0-9]*
    ;

OctalConstant
    :   '0'[0-7]*
    ;

HexadecimalConstant
    :   '0' [xX] [0-9a-fA-F] +
    ;

WS  :   (   ' '
        |   '\t'
        |   '\r'
        |   '\n'
        )+
	-> channel(HIDDEN)	 
    ;
