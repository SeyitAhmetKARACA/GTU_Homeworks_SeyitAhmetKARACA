#include "Cpu.h"
#include "Cpuprogram.h"
#include <iostream>

// 141044084 Seyit Ahmet KARACA
//Yorumlar ilk odevde var.
int stringToint(string param);
bool isint(string st);

using namespace std;

int main(int argc, char **argv)
{
	int arg = 0;
	if(argc != 3){
		cerr<<"Error Error : too few arguments to run"<<endl;
		return 0;
	}else if(isint(argv[2])){
		arg = stringToint(argv[2]);
		if(arg > 1 || arg < 0){
			cerr<<"third argument must be '0' or '1'"<<endl;
			return 0;
		}
	}else if(isint(argv[2]) == false){
		arg = stringToint(argv[2]);
		if(arg > 1 || arg < 0){
			cerr<<"third argument must be '0' or '1'"<<endl;
			return 0;
		}
	}

	Cpu cpu(arg);
	Cpuprogram cp(argv[1]);

	while(!cpu.Halted()){
		cpu.execute(cp.getLine(cpu.getPc()));
	}

	cpu.print();
	
	return 0;
}

/* parametreleri kontrol etmek icin burdalar. cpu class indan
   static olarak alabilirdim fakat orayada sacma durdular.
   bunlar icin baska class acmak istemedim */

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

