-module(login_manager).
-export([start/0,
        cretate_account/2,
        close_account/2,
        login/2,
        logout/1,
        online/0]).

start() -> register(?MODULE, spawn(fun() -> loop(#{}) end)).

invoke(Request) ->
    ?MODULE ! {Request, self()},
    receive {Res, ?MODULE} -> Res end.

cretate_account (User, Pass) -> invoke({create_accoubt, User, Pass}).
close_account(User, Pass) -> invoke({close_account, User, Pass}).
login(User,Pass) -> invoke({login, User,Pass}).
logout(User) -> invoke({logout, User}).
online() -> invoke(online).

loop(Map) ->
    receive
        {{cretate_account, User, Pass},From} -> 
            case maps:is_key(User, Map) of 
                true -> 
                    From ! {user_exists, ?MODULE},
                    loop(Map);
                false -> 
                    From ! {ok, ?MODULE},
                    loop(maps:put(User, {Pass, false}, Map))
            end;
        {{close_account, User, Pass}, From} ->
            case maps:find(User, Map) of
                {ok,{Pass,_}} ->
                    From ! {ok, ?MODULE},
                    loop(maps:remove(User, Map));
                _->
                    From ! {invalid, ?MODULE},
                    loop(Map)
                end;
        {online, From} ->
            F = fun(User, {_Pass, true}, Acc) -> [User | Acc];
                   (_User, _, Acc) -> Acc
                end,
            Res = maps:fold(F,[], Map),
            Res = [U || {U, {_P, true}} <- maps:to_list(Map)],

            From ! {Res, ?MODULE},
            loop(Map)
    end.