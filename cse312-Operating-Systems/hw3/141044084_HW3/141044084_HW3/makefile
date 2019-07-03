CC=g++
CFLAGS=-I. --std=c++11
DEPS = gtuos.h memory.h 8080emuCPP.h memoryBase.h gtuos.cpp 8080emu.cpp
OBJ = emulator.o


buildemulator:
	$(CC)  main.cpp $(DEPS) -o emulator.out $(CFLAGS) 

P1:
	./emulator.out P1.com filesystem.dat 0

P11:
	./emulator.out P1.com filesystem.dat 1

P2:
	./emulator.out P2.com filesystem.dat 0

P3:
	./emulator.out P3.com filesystem.dat 0

P4:
	./emulator.out P4.com filesystem.dat 0
