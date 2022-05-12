-module(queue).
-export([create/0, enqueue/2, dequeue/1]).

create() -> {[],[]}.

enqueue({In,Out},Item) -> {[Item|In],Out}.

dequeue({[],[]}) -> empty;
dequeue ({In,[]}) -> dequeue ({[],reverse(In)});
dequeue ({In,[H|T]}) -> {{In, T},H}.

reverse(L) -> reverse(L,[]).
reverse([],L) -> L;
reverse([H|T],L) -> reverse(T,[H|L]).
