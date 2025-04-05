loop(N, K, MAXN) :- NK is N * K, NK > MAXN, !.
loop(N, K, MAXN) :- NK is N * K, K2 is K + 1, assert(composite(NK)), loop(N, K2, MAXN), !.

init(MAXN) :- init2(1, 2, MAXN).
init2(_, N, MAXN) :- N > MAXN, !.
init2(C, N, MAXN) :- prime(N), assert(nth_prime(C, N)), loop(N, 2, MAXN), C2 is C + 1, N2 is N + 1, init2(C2, N2, MAXN), !.
init2(C, N, MAXN) :- N2 is N + 1, init2(C, N2, MAXN), !.

prime(N) :- not(composite(N)).

is_lowest(N, K, K) :- prime(K), 0 is N mod K, !.
is_lowest(N, K, MAXK) :- K2 is K + 1, not(0 is N mod K), is_lowest(N, K2, MAXK), !.

prime_divisors(1, []) :- !.
prime_divisors(N, [N]) :- prime(N), !.
prime_divisors(N, [X | ARR]) :- is_lowest(N, 2, X), N2 is N / X, prime_divisors(N2, ARR), !.