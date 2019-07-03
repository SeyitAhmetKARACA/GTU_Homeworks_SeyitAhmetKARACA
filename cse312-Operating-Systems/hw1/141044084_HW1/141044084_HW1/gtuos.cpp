#include <iostream>
#include "8080emuCPP.h"
#include "gtuos.h"
#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */
#include <fstream>

uint64_t GTUOS::handleCall(const CPU8080 & cpu){
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
    }
    
	return returnValue;
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
    //std::getline(cin,x);
    
	cin>>x;
    //cout<<x << endl;
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
