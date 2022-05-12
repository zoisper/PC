-module(priority_queue).
-export([create/0, enqueue/3, dequeue/1]).

create() -> [].

enqueue([], Item, Priority) -> [{Item, Priority}];
enqueue([{H,P}|T],Item,Priority) -> 
    if 
        Priority > P ->
            [{Item,Priority}|[{H,P}|T]];
        true ->
            [{H,P}|enqueue(T,Item, Priority)]
    end.

dequeue([]) -> empty;
dequeue([{H,_}|T]) -> {H,T}.
