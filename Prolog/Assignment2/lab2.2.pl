s --> w(N,K,NK),{NK is N*K,create_succs(N,N_new),create_succs(K,K_new),create_succs(NK,NK_new)},[3],v(N_new,K_new,NK_new).

w(0,0,0) --> [].
w(1,0,0) --> [0].
w(0,1,0) --> [1].
w(0,0,1) --> [2].
w(N,K,NK) --> [0],w(N_new,K,NK),[0],{N is N_new + 2}.
w(N,K,NK) --> [1],w(N,K_new,NK),[1],{K is K_new + 2}.
w(N,K,NK) --> [2],w(N,K,NK_new),[2],{NK is NK_new + 2}.

v(N,K,NK) --> zeros(N),ones(K),twos(NK).

zeros(0) --> [].
zeros(succ(N)) --> [0],zeros(N).

ones(0) --> [].
ones(succ(K)) --> [1],ones(K).

twos(0) --> [].
twos(succ(NK)) --> [2],twos(NK).

create_succs(0,0).
create_succs(Num,succ(X)) :- Num \= -1, New_num is Num - 1, create_succs(New_num,X).