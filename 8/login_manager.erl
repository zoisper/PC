-module(my_login_manager).
-export([start/0,
        cretate_account/2,
        close_account/2,
        login/2,
        logout/1,
        online/0]).

start() -> register(?MODULE, spawn(fun() -> loop(#{}) end)).

cretate_account(User, Pass) -> invoke({create_account, User, Pass}).

close_account(User, Pass) -> invoke({close_account,User, Pass}).

login(User, Pass) -> invoke({login,User, Pass}).

logout(User) -> invoke({logout,User}).

online() -> invoke(online).

invoke(Request) -> ?MODULE ! {Request,self()},
receive {Response, ?MODULE} -> Response end .

loop(Map) ->
    receive 
        {{create_account, User, Pass},From} -> 
            case maps:is_key(User,Map) of
                true -> 
                    From ! {user_exists, ?MODULE},
                    loop(Map);
                false ->
                    From ! {ok, ?MODULE},
                    loop(maps:put(User,{Pass, false},Map)) 
                end;
        {{close_account,User, Pass},From} ->
            case maps:find(User,Map) of
                {ok,{Pass,_}} ->
                    From ! {ok, ?MODULE},
                    loop(maps:remove(User,Map));
                _-> 
                    From ! {invalid, ?MODULE},
                    loop(Map)
                end;
        {{login,User, Pass},From} ->
            case maps:find(User, Map) of
                {ok,{Passw,_}} ->
                    if Pass == Passw ->
                        From ! {ok, ?MODULE},
                        loop(maps:update(User,{Pass,true},Map));
                    true -> From ! {invalid, ?MODULE},
                            loop(Map)
                            end;
                _->
                    From ! {invalid, ?MODULE},
                    loop(Map)
                end;
        {logout,User,From} ->
            case maps:find(User,Map) of
                {ok,{Pass,_}} ->
                    From ! {ok,?MODULE},
                    loop(maps:update(User,{Pass,false},Map));
                _->
                    {invalid, ?MODULE},
                    loop(Map)
            end;
        {online, From} ->
            Response = [User ||{User,{_,State}} <- maps:to_list(Map), State == true],
            From ! Response,
            loop(Map)
        end.

        




