#include <iostream>
#include "8080emuCPP.h"
#include "gtuos.h"
#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */
#include <fstream>

GTUOS::GTUOS(){
	threadindex = 0;
	threadCount = 0;
	srand (time(NULL));
	threadCount++;
	thread sakThread;

	sakThread.threadID = 0;
	sakThread.totalThread = threadCount;
	sakThread.threadStatus = 1; /* -1:blocked 0:ready 1:running */
	sakThread.stackEmptySpace = 1;
	sakThread.checkBeginFunction = true;
	threadTable.push_back(sakThread);
}

void GTUOS::roundRobin(unsigned int & cycle,CPU8080 & cpu){
	if(cycle >= 100 && threadTable.size() > 1){
		
		threadTable[threadindex].state = *cpu.state;
		cout<<"thread "<<threadindex;	
		threadindex++;

		if(threadindex % threadTable.size() == 0){	
			threadindex = 0;
		}


		cpu.state = &(threadTable[threadindex].state);

		if(threadindex != 0 && threadTable[threadindex].checkBeginFunction){
			cpu.state->pc = threadTable[threadindex].functionAddress;
			threadTable[threadindex].checkBeginFunction = false;
		}

		cycle = 0;
	}
}

int GTUOS::TJoin(const CPU8080 & cpu){
	if(threadTable.size() > 1){
		for(int i=1; i < threadTable.size() ; i++){
			if(threadTable[i].threadID == cpu.state->b){
				threadTable[i].threadStatus = -1;
				return 40;
			}
		}
	}
	threadTable[cpu.state->b].threadStatus = 0;
	return 40;
}

uint64_t GTUOS::handleCall(CPU8080 & cpu,unsigned int & cycle){
	int returnValue;
    switch(cpu.state->a){
        case 1:
                returnValue = PRINT_B(cpu);
                break;
        case 2:
                returnValue = PRINT_MEM(cpu);
                break;
        case 3:
                returnValue = READ_B(cpu);
                break;
        case 4:
                returnValue = READ_MEM(cpu);
                break;
        case 5:
                returnValue = PRINT_STR(cpu);
                break;
        case 6:
                returnValue = READ_STR(cpu);
                break;
        case 7:
                returnValue = GET_RND(cpu);
                break;
        case 8:
                returnValue = TExit(cpu);
                break;
        case 9:
                returnValue = TJoin(cpu);
                break;
        case 10:
                returnValue = TYield(cpu);
                break;
        case 11:
                returnValue = TCreate(cpu,cycle);
                break;
    }
    
	return returnValue;
}


int GTUOS::TExit(CPU8080 & cpu){
	cout<<threadTable[threadindex].threadID<<endl;
	if( threadTable.size() > 1 && cpu.state->b != 0){
		for(int i=1; i < threadTable.size() ; i++){
			if(threadTable[i].threadID == cpu.state->b){
				threadTable.erase(threadTable.begin()+i);
			}
		}
	}
	//threadTable.pop_back();
	threadindex=0;
	//cout<<threadTable.size()<<endl;
	cpu.state = &(threadTable[0].state);
	return 50;
}

int GTUOS::TYield(CPU8080 & cpu){
	unsigned int cycle = 100;
	roundRobin(cycle,cpu);
	return 40;
}

int GTUOS::TCreate(const CPU8080 & cpu,unsigned int & cycle){
	threadCount++;
	thread sakThread;
	bool cvo = true;
	bool cvi = true;
	long tempTid;	
	sakThread.state = *cpu.state;/***********************************************/
	sakThread.functionAddress = cpu.state->b*256+cpu.state->c; /*****************/
	sakThread.startTimeOfThread = cycle;
	sakThread.totalThread = threadCount;
	sakThread.threadStatus = 0; /* -1:blocked 0:ready 1:running */
	sakThread.stackEmptySpace = 1;
	sakThread.checkBeginFunction = true;
	
	for(int i=0;i < threadTable.size() && cvo; i++){
		tempTid = 1000+i;
		cvi = true;
		for(int j=0; j < threadTable.size() && cvi ; j++){
			if(threadTable[j].threadID == tempTid)
				cvi = false;
		}
		if(cvi)
			cvo=false;
	}
	sakThread.threadID = tempTid;/* buna sonra bak */
	threadTable.push_back(sakThread);
	cpu.state->b = sakThread.threadID;
	return 80;
}

int GTUOS::PRINT_B(const CPU8080 & cpu){
    cout<<(unsigned)cpu.state->b;
    return 10;
}

int GTUOS::PRINT_MEM(const CPU8080 & cpu){
    cout<<hex<<(unsigned)cpu.memory->physicalAt(cpu.state->b*256+cpu.state->c)<<endl;
    return 10;
}

int GTUOS::READ_B(const CPU8080 & cpu){
    int x;
    cin>>x;
    cpu.state->b = x;
    return 10;
}

int GTUOS::READ_MEM(const CPU8080 & cpu){
    int x;
    cin>>x;
    cpu.memory->physicalAt(cpu.state->b*256+cpu.state->c) = x;
    return 10;
}

int GTUOS::PRINT_STR(const CPU8080 & cpu){
    /* bu baslangıc adresi*/
    for(int i=cpu.state->b*256+cpu.state->c; unsigned(cpu.memory->physicalAt(i)) != 10 ; i++)
        cout<<cpu.memory->physicalAt(i);
    return 100;
}

int GTUOS::READ_STR(const CPU8080 & cpu){
    string x;
    uint16_t a=cpu.state->b*256+cpu.state->c;
    
    cin>>x;
    for(int i=0 ;x[i] != '\0';i++){
        cpu.memory->physicalAt(a) = x[i];
        a++;
    }
    cpu.memory->physicalAt(a) = 10;
    return 100;
}

int  GTUOS::GET_RND(const CPU8080 & cpu){
    cpu.state->b = rand() % 256;
    return 5;
}

void GTUOS::writeMemory(const CPU8080 & cpu){
    ofstream outFile;
    outFile.open("exe.mem");
    for(int i=0;i<0x1000;i++){
	if(i % 16 == 0 && i != 0)
		outFile<<endl;
        if(i % 16 == 0){
            outFile<<hex<< i<<"\t: ";
        }
        outFile<<hex<<unsigned(cpu.memory->physicalAt(i))<<" ";
    }
    outFile.close();
    
}
