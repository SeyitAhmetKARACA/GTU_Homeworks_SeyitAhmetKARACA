% Room facts
% id,capacity,equipment
room(z06,10,[hcapped,projector,null]).
room(z11,10,[hcapped,smartboard,null]).


% Occupancy facts
% id-hour-course
occupancy(z06,8,cse341).
occupancy(z06,9,cse341).
occupancy(z06,10,cse341).
occupancy(z06,11,cse341).
occupancy(z06,12,null).
occupancy(z06,13,cse331).
occupancy(z06,14,cse331).
occupancy(z06,15,cse331).
occupancy(z06,16,null).
occupancy(z11,8,cse343).
occupancy(z11,9,cse343).
occupancy(z11,10,cse343).
occupancy(z11,11,cse343).
occupancy(z11,12,null).
occupancy(z11,13,null).
occupancy(z11,14,cse321).
occupancy(z11,15,cse321).
occupancy(z11,16,cse321).

% Course facts
% id-instructor-capacity-hour-room-needs
course(cse341,genc,10,4,z06,projector).
course(cse343,cenovar,6,3,z11,smartboard).
course(cse331,bayrakci,5,3,z06,null).
course(cse321,gozupek,10,4,z11,smartboard).

% instructor
% id-courses-needs
instructor(genc,cse341,projector).
instructor(cenovar,cse343,smartboard).
instructor(bayrakci,cse331,null).
instructor(gozupek,cse321,smartboard).

% Student
% id-list of courses-handicapped
student(1,[cse341,cse343,cse331],null).
student(2,[cse341,cse343],null).
student(3,[cse341,cse331],null).
student(4,[cse341],null).
student(5,[cse343,cse331],null).
student(6,[cse341,cse343,cse331],hcapped).
student(7,[cse341,cse343],null).
student(8,[cse341,cse331],hcapped).
student(9,[cse341],null).
student(10,[cse341,cse321],null).
student(11,[cse341,cse321],null).
student(12,[cse343,cse321],null).
student(13,[cse343,cse321],null).
student(14,[cse343,cse321],null).
student(15,[cse343],hcapped).

% Check whether there is any scheduling conflict. --> conflicts(CourseID1,CourseID2)
% Conflicts
% conflicts(cse321,cse341).
conflicts(X,Y) :- 
	course(X,_,_,R,A,_),
	course(Y,_,_,_,B,_),
	A == B,
	occupancy(A,C,X),
	occupancy(B,D,Y),
	Q is R + C,!,
   	((C =< D), (D < Q)).

% ornek sorgular
% Check which room can be assigned to a given class. --> assign(RoomID,CourseID)
% assign(z23,cse321).
% Check which room can be assigned to which classes. --> assign(RoomID,_)
% assign(z23,Y).
assign(X,Y):- room(X,Rk,Nr),
	course(Y,_,Ck,H,X,Nc),
	occupancy(X,Hl,Y),
	Ck =< Rk, member(Nc,Nr), Q is H + Hl -1 ,
	occupancy(X,Q,Y).

% ornek sorgular
% Check whether a student can be enrolled to a given class. --> enroll(StudentID,CourseID)
% enroll(1,Y).
% Check which classes a student can be assigned. --> enroll(StudentID,_)
% enroll(X,Y).

enroll(X,Y) :- 
	student(X,Q,W) , 
	course(Y,_,_,_,E,_),
	room(E,_,R), 
	\+member(Y,Q), 
	member(W,R).


% öğrenci ekleme.
% Eklenmek istenen öğrenci var ise ekleme yapılamamakta.
% Eklenmek istenen öğrencinin alabileceği dersler liste halinde verilmelidir.
% Liste içerisindeki dersler eğer derslerde yok ise ders eklenemez.
% örnek sorgular.
% addStudent(1,[cse321],null). -> false
% addStudent(20,[cse321],null). -> true
% student(X,Y,Z). ile kontrol edilebilir.

% Aynı id ye sahip iki ogrenci eklenemez.
% ogrenci sistemde bulunmayan bir dersi alamaz.
% ornek sorgu
process(H):- course(H,_,_,_,_,_).
memberSAK([]).
memberSAK([H|T]) :- process(H), memberSAK(T).
addStudent(X,Y,Z):- not(student(X,_,_)),memberSAK(Y),asserta(student(X,Y,Z)).

% Z bir liste olmak zorunda ve içerisinde kesin olarak null bulunmalıdır.
% olan bir sinif eklenemez.
% ornek sorgu
% addRoom(z66,3,[null]).
addRoom(X,Y,Z) :- not(room(X,_,_)),asserta(room(X,Y,Z)).

% Verilen instuctor daha önceden kayıtlı ise tekrar eklenemez.
addInstructor(X,Y,Z) :- not(instructor(X,_,_)),asserta(instructor(X,Y,Z)).

% Eğer kayıtlı bir sınıf yok ise ders eklenemez.
% Kayıtlı bir ders eklenemez.
% ornek sorgu
% addCourse(cse331,bayrakci,5,3,z06,null). -> false
% addCourse(cse666,batman,5,3,z06,null). -> true
addCourse(X,Y,Z,Q,W,E) :- not(course(X,_,_,_,_,_)),room(W,_,_), asserta(course(X,Y,Z,Q,W,E)).


