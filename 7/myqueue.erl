-module(myqueue).
-export([create/0, enqueue/2, dequeue/1,create2/0, enqueue2/2, dequeue2/1]).

create() -> [].
enqueue(Queue,Item) -> Queue ++ [Item].
dequeue([]) -> empty;
dequeue([H|T]) -> {T,H}.


create2() -> {[],[]}.
enqueue2({In,Out},Item) -> {[Item|In],Out}.

dequeue2({[],[]}) -> empty;
dequeue2({In,[]}) -> dequeue2({[],lists:reverse(In)});
dequeue2({In,[H|T]}) -> {{In,T},H}.

% implementaçao do reverse
reverse(L) -> reverse(L,[]).
reverse([H|T],A) -> A;
reverse([H|T],A) -> reverse(T, [H|A]).