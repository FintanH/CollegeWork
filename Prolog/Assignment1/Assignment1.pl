numeral(0).
numeral(s(X)) :- numeral(X).
numeral(p(X)) :- numeral(X).

add(X,Y,Z) :- add1(X,Y,A), eval(A,Z).%, tidy(A,_,Z).

add1(0,X,X).
add1(s(X),Y,s(Z)) :- add1(X,Y,Z).
add1(p(X),Y,p(Z)) :- add1(X,Y,Z).

%eval(0,0).
%eval(X+Y,Z) :- add1(X,Y,A).
%eval(X-Y,Z) :- subtract1(X,Y,A).
%eval(s(X),Z) :- X\=p(_), eval(X,Y), add1(0,s(Y),Z).
%eval(p(X),Z) :- X\=s(_), eval(X,Y), add1(0,p(Y),Z).
%eval(s(p(X)),Z) :- eval(X,Y), add1(0,Y,Z).
%eval(p(s(X)),Z) :- eval(X,Y), add1(0,Y,Z).
%eval(-X,Z) :- eval(X,A), minus(A,Z).

eval(0,0).
eval(s(X),Z) :- X\=p(_), eval(X,Temp), add(s(0),Temp,Z).
eval(p(X),Z) :- X\=s(_), eval(X,Temp), add(p(0),Temp,Z).
eval(s(p(X)),Z) :- add(0,X,Temp), eval(Temp,Z).
eval(p(s(X)),Z) :- add(0,X,Temp), eval(Temp,Z).

minus(0,X) :- add1(0,0,X).
minus(s(X),p(Y)) :- minus(X,Y).
minus(p(X),s(Y)) :- minus(X,Y).

subtract1(0,X,Z) :- eval(X,Z).%, tidy(A,_,Z).
subtract1(X,0,Z) :- eval(X,Z).%, tidy(A,_,Z).
subtract1(X,Y,Z) :- X\=0, Y\=0, eval(X,A), eval(Y,B), minus(B,C), add(A,C,Z).

add2(0,X,Z) :- eval(X,Z).%, tidy(A,_,Z).
add2(X,0,Z) :- eval(X,Z).%, tidy(A,_,Z).
add2(X,Y,Z) :- X\=0, Y\=0, eval(X,A), eval(Y,B), add(A,B,Z).