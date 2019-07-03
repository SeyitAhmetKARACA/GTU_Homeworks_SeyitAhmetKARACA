#include <iostream>
#include "8080emuCPP.h"
#include "gtuos.h"
#include "memory.h"
#include "fstream"

using namespace std;
	// This is just a sample main function, you should rewrite this file to handle problems 
	// with new multitasking and virtual memory additions.
int main (int argc, char**argv)
{
	int DEBUG = atoi(argv[3]);
	if (argc != 4){
		std::cerr << "Usage: prog exeFile debugOption\n";
		exit(1);
	}

	unsigned int cyclesCount =0;
	
	memory mem;

	CPU8080 theCPU(&mem);
	GTUOS	theOS(argv[2]);

	theCPU.ReadFileIntoMemoryAt(argv[1], 0x0000);	
	 
	unsigned int cycleRobin=0;

	do{
		cycleRobin+=theCPU.Emulate8080p(DEBUG);
		if(DEBUG == 1)
			theOS.mod1();

	        if(theCPU.isSystemCall()){
	        	cycleRobin+=theOS.handleCall(theCPU,cyclesCount);
		}
		cyclesCount += cycleRobin;
		theOS.roundRobin(cycleRobin,theCPU);
	}while (!theCPU.isHalted());


    	cout<<endl<<"Total number of cycles used by the program :"<<cyclesCount<<endl;
	theOS.writeMemory(theCPU);
    	theOS.mod0();
	return 0;
}

