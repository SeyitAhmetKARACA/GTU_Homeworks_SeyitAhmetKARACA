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
	if (argc != 3){
		std::cerr << "Usage: prog exeFile debugOption\n";
		exit(1); 
	}

	int DEBUG = atoi(argv[2]);

	string progName = "./sim8080";
	string comName = "exe.com";
	if(progName.compare(argv[0])){
		cerr<<"Program should work with command line"<<endl;
		cerr<<"sim8080 exe.com debugFlag"<<endl;
		return 0;
	}
	if(comName.compare(argv[1])){
		cerr<<"Program should work with command line"<<endl;
		cerr<<"sim8080 exe.com debugFlag"<<endl;
		return 0;
	}

	if(DEBUG < 0 || DEBUG > 2){
		cerr<<"Program should work with command line"<<endl;
		cerr<<"sim8080 exe.com debugFlag"<<endl;
		return 0;
	}


	unsigned int cyclesCount =0;
	srand (time(NULL));
	memory mem;

	CPU8080 theCPU(&mem);
	GTUOS	theOS;

	theCPU.ReadFileIntoMemoryAt(argv[1], 0x0000);	
	int i=1; 
	do{
		cyclesCount+=theCPU.Emulate8080p(DEBUG);
		if(DEBUG == 2)
			getchar();
        if(theCPU.isSystemCall()){
        	cyclesCount+=theOS.handleCall(theCPU);
	}
        i++;
	}while (!theCPU.isHalted());
    	cout<<endl<<"Total number of cycles used by the program :"<<cyclesCount<<endl;
    theOS.writeMemory(theCPU);
    
	return 0;
}

