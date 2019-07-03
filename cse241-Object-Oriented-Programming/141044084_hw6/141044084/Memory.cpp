#include "Memory.h"
#include <iostream>

using namespace std;

Memory::Memory(){
	for(int i=0;i<NUMBER_OF_MEMORY;i++)
		M[i] = 0;
	option = 0;
}
Memory::Memory(int cOption){
	for(int i=0;i<NUMBER_OF_MEMORY;i++)
		M[i] = 0;
	option = cOption;
}

const void Memory::setMem(const int index,const unsigned int value){
	if(index < NUMBER_OF_MEMORY && value >= 0){
		M[index] = value;
	}
	return;
}

const void Memory::printAll()const{
	for(int i = 0;i < NUMBER_OF_MEMORY;i++)
		cout<<"["<<i<<"] -> "<<M[i]<<endl; 
}
