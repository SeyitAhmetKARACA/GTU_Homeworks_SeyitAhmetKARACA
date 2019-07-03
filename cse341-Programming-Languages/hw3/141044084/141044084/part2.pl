% nerden-nereye-uzaklık
flight(edirne, erzurum, 5).
flight(erzurum, edirne, 5).
flight(erzurum, antalya , 2).
flight(antalya, erzurum , 2).
flight(antalya,izmir,1).
flight(izmir,antalya,1).
flight(antalya,diyarbakır,5).
flight(diyarbakır,antalya,5).
flight(izmir,istanbul,3).
flight(istanbul,izmir,3).
flight(istanbul,trabzon,3).
flight(trabzon,istanbul,3).
flight(istanbul,ankara,2).
flight(ankara,istanbul,2).
flight(izmir,ankara,6).
flight(ankara,izmir,6).
flight(ankara,diyarbakır,8).
flight(diyarbakır,ankara,8).
flight(ankara,trabzon,6).
flight(trabzon,ankara,6).
flight(kars,ankara,3).
flight(ankara,kars,3).
flight(kars,gaziantep,3).
flight(gaziantep,kars,3).

% rota base case
route(X, Y, C) :- route(X, Y, C, [], X).
% route
route(X, Y, C, V, L) :-
	\+member(X, V),
	flight(X, Z, C1),
	not(Z = L),
	route(Z, Y, C2, [X|V], X),
	not(Y = L),
	not(X = Y),
	C is C1 + C2.	
% route
route(X, Y, C, _, _) :- flight(X, Y, C).
