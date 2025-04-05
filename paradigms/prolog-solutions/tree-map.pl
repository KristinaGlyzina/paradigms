map_build([], []) :- !.
map_build(ListMap, TreeMap) :-
	create_index(ListMap, ListMap, 1),
	length(ListMap, Len),
	map_build_rec(ListMap, ListMap, TreeMap, 1, Len), !.

map_build_rec(ListMap0, _, [M_VAL], L, L) :- get_index(ListMap0, M_VAL, L), !.
map_build_rec(ListMap0, _, [[LEFT], M_VAL], L, L1) :-
	L1 is L + 1,
	get_index(ListMap0, LEFT, L),
	get_index(ListMap0, M_VAL, L1), !.
map_build_rec(ListMap0, ListMap, [LEFT, M_VAL, RIGHT], L, R) :-
	M is (L + R) // 2,
	M1 is M - 1,
	M2 is M + 1,
	get_index(ListMap0, M_VAL, M),
	map_build_rec(ListMap0, ListMap, LEFT, L, M1),
	map_build_rec(ListMap0, ListMap, RIGHT, M2, R), !.

create_index(ListMap0, [(K,V)], C) :- assertz(get_index(ListMap0, (K,V), C)), !.
create_index(ListMap0, [(K,V) | ListMap], C) :-
	C2 is C + 1,
	assertz(get_index(ListMap0, (K,V), C)),
	create_index(ListMap0, ListMap, C2), !.

map_get([(Key, Value)], Key, Value) :- !.
map_get([_, (Key, Value)], Key, Value) :- !.
map_get([Left, (Key1, _)], Key, Value) :- map_get(Left, Key, Value), !.
map_get([_, (Key, Value), _], Key, Value) :- !.

map_get([_, (Key1, _), Right], Key, Value) :-
	Key > Key1, map_get(Right, Key, Value), !.

map_get([Left, (Key1, _), _], Key, Value) :-
	Key < Key1, map_get(Left, Key, Value), !.

map_lastKey([(K, _)], K) :- !.
map_lastKey([[(_, _)], (K, _)], K) :- !.
map_lastKey([_, (_, _), RIGHT], K) :- map_lastKey(RIGHT, K), !.

map_lastValue([(_, V)], V) :- !.
map_lastValue([[(_, _)], (_, V)], V) :- !.
map_lastValue([_, (_, _), RIGHT], V) :- map_lastValue(RIGHT, V), !.

map_lastEntry([(K, V)], (K, V)) :- !.
map_lastEntry([[(_, _)], (K, V)], (K, V)) :- !.
map_lastEntry([_, (_, _), RIGHT], (K, V)) :- map_lastEntry(RIGHT, (K, V)), !.
