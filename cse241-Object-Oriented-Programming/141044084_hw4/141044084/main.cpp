#include "CPU.h"
#include "CPUProgram.h"
#include "Memory.h"
#include "Computer.h"
#include <iostream>

/*  141044084 - Seyit Ahmet KARACA*/

int stringToint(string param);
bool isint(string st);

/*
sizin test icin verdiginiz kodlari kullandim. atoi yi degistirdim sadece birde 
altta getline i degistirdim. not olarak dustum onu.
*/

using namespace std;
int main(int argc, char** argv){
	//////////////////////////////////////////////////////////////////////////
	//command line parameters
	int option;
	if(argc != 3){
		cerr<<"Error Error : too few arguments to run"<<endl;
		return 0;
	}else if(isint(argv[2])){
		option = stringToint(argv[2]);
		if(option > 1 || option < 0){
			cerr<<"third argument must be 0-1-2"<<endl;
			return 0;
		}
	}else if(isint(argv[2]) == false){
		option = stringToint(argv[2]);
		if(option > 1 || option < 0){
			cerr<<"third argument must be 0-1-2"<<endl;
			return 0;
		}
	}

	string filename = argv[1];

	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//Testing class Memory
	Memory myMemory(option);
	//index, value
	myMemory.setMem(0, 100);
	cout << myMemory.getMem(0) << endl;
	//should print in a way that similar to this:
	//Memory Values:
	//[0] -> 100
	//[1] -> 0
	//[2] -> 0
	//.
	//.
	//[49] -> 0
	myMemory.printAll();
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//Testing class CPU
	CPU myCPU(option);
	myCPU.execute("MOV #0, R1", myMemory);
	myCPU.execute("MOV R1, #1", myMemory);
	//should print in a way that similar to this:
	//CPU Register Values:
	//[0] -> 100
	//[1] -> 0
	//[2] -> 0
	//[3] -> 0
	//[4] -> 0
	myCPU.print();
	//should print in a way that similar to this:
	//Memory Values:
	//[0] -> 100
	//[1] -> 100
	//[2] -> 0
	//.
	//.
	//[49] -> 0
	myMemory.printAll();
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//Testing class CPUProgram
	CPUProgram myCPUProgram(option);
	myCPUProgram.ReadFile(filename);
	cout << myCPUProgram.getLine(1) << endl;

	//	getLine'a hangi satiri istiyorsak onu parametre yolluyoruz.
	//	0.satir yok. o yuzden 1 yazdim. 
	//	onceki odevde getline a PC yaziyorduk ve PC 1 den basliyordu.

	cout << myCPUProgram.getLine(myCPUProgram.size() - 1) << endl;//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	//Testing class Computer
	Computer myComputer1(myCPU, myCPUProgram, myMemory, option);
	Computer myComputer2(option);
	myComputer2.setCPU( myComputer1.getCPU() );
	myComputer2.setCPUProgram(myComputer1.getCPUProgram() );
	myComputer2.setMemory(myComputer1.getMemory() );
	myComputer2.execute();
	//////////////////////////////////////////////////////////////////////////

	return 0;
}


bool isint(string st){
	int lcv=0;
	if(st == "")
		return false;

	if(st[0] == '-')
		st = &st[1];

	while(st.size() > lcv){ /* int e cevrilmiyor.	*/
		if(st[lcv] < '0' || st[lcv] > '9')
			return false;
		lcv++;
	}

	return true;
}


int stringToint(string param){
	const int powerx = 10;
	int power = 1,lcv=0,sum=0;
	string st="";
	int i=param.size();;
	int sign = 1;

	if(param[0] == '-'){
		st = &param[1];
		sign = -1;
		i = st.size();
	}else
		st = param;

	while(i>0){
		sum += power * (st[i-1] - '0');
		power *= powerx;
		i--;
	}
	
	return sum*sign;
}
