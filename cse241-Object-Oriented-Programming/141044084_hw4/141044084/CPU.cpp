#include "CPU.h"
#include "CPUProgram.h"
#include <iostream>


CPU::CPU(){
	for(int i=1;i <= NUMBER_OF_REGS ; i++)
		setReg(i,0);
	setPc(1);
	arg = 0;
}

CPU::CPU(int argC):arg(argC){
	for(int i=1;i <= NUMBER_OF_REGS ; i++)
		setReg(i,0);
	setPc(1);
}

void CPU::setSize(int Size){
	size = Size;
}

int CPU::stringToInt(string param){
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


int CPU::R_N_C(string st){
	int rv;

	clearWSpace(st);
	if(st[0] == 'R' && st[1] != '-')
	{
		if(isInt(&st[1]))
			rv = 1;
		else
			rv =-1;
	}else if(st[0] == '#' && st[1] != '-')
	{
		if(isInt(&st[1]))
			rv = 3;
		else
			rv =-1;
	}else if(st == "")
		rv = 0;
	else if(isInt(st))
		rv = 2;
	else
		rv = -1;

	return rv;
}

int CPU::commaIndex(string st)
{
	int i=0,j=0;
	int b=0;
	while (i != string::npos){
		
		i= st.find_first_of(',',i+1);
		if(i != -1)
			j =i;
		b++;
	}

	if( b-1 == 0)
		j = -1;
	else if(b-1 > 1)
		j = -2;

	return j;
}

void CPU::clearWSpace(string& param1){
	int i=0,j=0;
	bool lcv = true;
	int k =0;

	k = param1.find_first_of(';');		
	if( k != -1)				/* varsa noktali virgul ve  */
		param1.erase(k,param1.size()-k);/* sonrasini yok et !	    */

	while(lcv){
		if(param1[i] == ' ' || param1[i] == '	')
			i++;
		else
			lcv = false;
	}
	param1.erase(0,i);

	i=0;
	lcv = true;
	while(lcv){
		if(param1[param1.size()-(i+1)] == ' ' 
				|| param1[param1.size()-(i+1)] == '	' )
			j++;
		else
			lcv = false;
		i++;
	}
	param1.erase(param1.size()-j,j);

	return;
}

bool CPU::isInt(string st){
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


void CPU::execute(string fLine,Memory& CpuMemory){
	string mod,p2="",p3=""; /*mod: instruction p2-3:parametre 2-3 */
	int ip2,ip3=-1; /* i = int 				 */
	int p2t,p3t; /* parametre tipi				*/
	int vs = 0;  /* virgul sayisi				*/
	int imod;    /* index mod,inst. tan sonraki ilk virgul indexi	*/
	clearWSpace(fLine);
	imod = fLine.find_first_of(" ");
	mod = fLine.substr(0,imod);
	fLine.erase(0,imod);
	
	setPc(getPc()+1);

	vs = commaIndex(fLine);
	if(commaIndex(fLine) >= 0){ /* 1 tane virgul var		*/
		int i = commaIndex(fLine);


		p2 = fLine.substr(0,i);
		clearWSpace(p2);
	
		fLine.erase(0,i+1);

		p3 = fLine;
		clearWSpace(p3);

		p3t = R_N_C(p3);
		p2t = R_N_C(p2);

		/* parametre 2 icin RNCE
		 R ise index al
		 C ise constant'i al
		 E ise error
		 N , null*/
		if(p2t == 1)
			ip2 = stringToInt(&p2[1]);
		else if(p2t == 2)
			ip2 = stringToInt(p2);
		else if(p2t == 0)
			ip2 = 0;
		else if(p2t == -1 ){
		   	cerr<<"Error Line :"<<getPc();
			cerr<<" Check first parameter"<<endl;
			haltedValue = true;			
			return;
		}else if(p2t == 3){
			ip2 = stringToInt(&p2[1]);
		}
		
		
		if(p3t == 1 ){
			ip3 = stringToInt(&p3[1]);
		}else if(p3t == 2)
			ip3 = stringToInt(p3);
		else if(p3t == 0)
			ip3 = 0;
		else if(p3t == -1){
			cerr<<"Error Line :"<<getPc();
			cerr<<" Check second parameter"<<endl;
			haltedValue = true;			
			return;
		}else if(p3t == 3){
			ip3 = stringToInt(&p3[1]);
		}
	}else if(commaIndex(fLine) == -1){ /* virgul yok demek.		*/
		p2 = fLine;
		clearWSpace(p2);
		
		p2t = R_N_C(p2);
		p3t = 0;
		if(p2t == 1)
			ip2 = stringToInt(&p2[1])-1;
		else if(p2t == 2)
			ip2 = stringToInt(p2);
		else if(p2t == 3){
			ip2 = stringToInt(&p2[1]);
		}else if(mod != "HLT"){
			cerr<<"Error Line :"<<getPc();
			cerr<<" Check the parameters"<<endl;
			haltedValue = true;			
			return;
		}
	}
	/* arraylerin indexlerin disina gecmemesi icin */
	if((p2t == 1 && ip2 > NUMBER_OF_REGS) || (p2t == 3 && ip2 > NUMBER_OF_MEMORY)){
		cerr<<"Error Line "<<getPc();
		cerr<<" : entered unknown register"<<endl;
			haltedValue = true;			
			return;
	}else if(p3t == 1 && ip3 > NUMBER_OF_REGS || (p3t == 3 && ip3 > NUMBER_OF_MEMORY)){
		cerr<<"Error Line "<<getPc();
		cerr<<" : entered unknown register"<<endl;
			haltedValue = true;			
			return;
	}

	/*unsigned int - int sorunlarini engellemek icin*/
	if(mod == "MOV" && (p2t == 1 || p2t == 3) && ip2 != -1){
		if((p2t == 1 && p3t == 3 && getReg(ip2) <0 )
			 || (p2t == 3 && p3t == 1 && getReg(ip2) >= numeric_limits<int>::max())){
		cout<<" ------ " << getReg(ip2) << "ip2 : "<<ip2 <<endl;

			cerr<<"Error Line "<<getPc();
			cerr<<" : Couldn't converted signed integer to ";
			cerr<<"unsigned integer"<<endl;
			haltedValue = true;			
			return;
		}else
			MOV(CpuMemory,ip2,ip3,p2t,p3t);
	}else if(mod == "ADD" && p2t == 1)
		ADD(CpuMemory,ip2,ip3,p3t);
	else if(mod == "SUB" && p2t == 1)
		SUB(CpuMemory,ip2,ip3,p3t);
	else if(mod == "JMP"){
		if(p2t == 2 && p3t == 0){/*sabit sayi geldiginde.	*/
			if(JMP(ip2) == false || ip2 > size){				
				cerr<<"Error Line :"<<getPc()<<
				" Wrong parameter"<<endl;
				cerr<<"The value that is given to JMP "<<
				"instruction can not be higher than ";
				cerr<<"file line size"<<endl;
				haltedValue = true;			
				return;
			}
		setPc(ip2);
		}
		else if(p2t == 1 && p3t == 2){
			if(getReg(ip2) == 0){
				if(JMP(ip3) == false || ip3 > size){
					cerr<<"Error Line :"<<getPc()<<
					" Wrong parameter"<<endl;
					cerr<<"The value that is given to JMP "<<
					"instruction can not be higher than ";
					cerr<<"file line size"<<endl;
					haltedValue = true;			
					return;
				}
				setPc(ip3);
			}
		}else{
			cerr<<"Error Line :"<<getPc()<<" wrong parameters"<<endl;
			haltedValue = true;			
			return;
		}
	}else if(mod == "JPN" && p2t == 1 && p3t == 2){ // JPN
		if(getReg(ip2) <= 0){
			if(JMP(ip3) == false || ip3 > size ){
				cout << "size : " << size <<endl;
				cerr<<"Error Line :"<<getPc()<<
				" Wrong parameter"<<endl;
				cerr<<"The value that is given to JMP "<<
				"instruction can not be higher than ";
				cerr<<"file line size"<<endl;
				haltedValue = true;			
				return;
			}
			setPc(ip3);
		}
	}else if(mod == "PRN" && vs == -1){
		if(p2t == 2)
			PRN(ip2);
		else if(p2t == 1)
			PRN(getReg(ip2));
		else if(p2t == 3)
			PRN(CpuMemory.getMem(ip2));
	}else if(mod == "HLT" && vs == -1 && p3 =="" && p2t == 0){
		HLT();
		haltedValue = true;			
		return;
	}else{
		cerr<<"Error Line :"<<getPc()<<" wrong parameters"<<endl;
		haltedValue = true;			
		return;
	}

	if(arg == 1){
		if(mod == "PRN" || mod == "JMP" || mod == "SUB")
			cout<<mod<<" "<<p2<<" "<<p3<<"   \t";
		else
			cout<<mod<<" "<<p2<<" "<<p3<<"\t";
		for(int i=0;i< NUMBER_OF_REGS;i++){
			cout<<"R"<< i+1<<"="<<getReg(i);
			if(i != NUMBER_OF_REGS-1)
				cout<<",";
		}
		cout<<endl;
	}else if(arg == 2){
		if(mod == "PRN" || mod == "JMP" || mod == "SUB")
			cout<<mod<<" "<<p2<<" "<<p3<<"   \t";
		else
			cout<<mod<<" "<<p2<<" "<<p3<<"\t";
		for(int i=0;i< NUMBER_OF_MEMORY;i++){
			cout<<"#"<< i<<"="<<CpuMemory.getMem(i);
			if(i != NUMBER_OF_REGS-1)
				cout<<",";
		}
		cout<<endl;
	}
	return;
}

void CPU::MOV(Memory& Mem,int p1,int p2,int p1type,int p2type){
	if(p1type == 1 && p2type == 1) // reg - reg
		setReg(p2,getReg(p1));	
	else if(p1type == 1 && p2type == 2) // reg- const
		setReg(p1,p2);		    //++
	else if(p1type == 1 && p2type == 3) // reg - memory
		Mem.setMem(p2,getReg(p1));
	else if(p1type == 3 && p2type == 1) // memory - reg
		setReg(p2,Mem.getMem(p1));
	else if(p1type == 3 && p2type == 2 && p2 >= 0) // memory - const
		Mem.setMem(p1,p2);

	return;
}

void CPU::ADD(Memory& Mem,int p1,int p2,int p2type){
	if(p2type == 1)
		setReg(p1,getReg(p1)+getReg(p2));	
	else if(p2type == 2)
		setReg(p1,getReg(p1)+p2);
	else if(p2type == 3)
		setReg(p1,Mem.getMem(p2) + getReg(p1));

	return;
}

void CPU::SUB(Memory& Mem,int p1,int p2,int p2type){
	if(p2type == 1)
		setReg(p1,getReg(p1)-getReg(p2));		
	else if(p2type == 2)
		setReg(p1,getReg(p1)-p2);
	else if(p2type == 3)
		setReg(p1,getReg(p1) - Mem.getMem(p2));
	return;
}

void CPU::PRN(int p1){
	cout <<p1<<endl;	
	return;
}

bool CPU::JMP(int q)
{
	PC = q;
	return true;
}


void CPU::HLT(){
//	for(int i=0;i< NUMBER_OF_REGS ;i++)
//		cout<<"R"<< i+1 <<"="<<getReg(i)<<"\t";
	cout<<endl;
	
	haltedValue = true;
}

void CPU::print()const{
	int p=getPc();
	for(int i=0;i < NUMBER_OF_REGS;i++){
		cout<<"R"<< i+1<<"="<<getReg(i+1);
		if(i != size-1)
			cout<<",";
	}
	cout<< "PC : "<<p-1;
	cout<<endl;
	return;		
}

void CPU::setReg(int numReg,int value){
	if(numReg <= NUMBER_OF_REGS)
		R[numReg-1] = value;
	else
		cerr<<" Hata ."<<endl;
}

int CPU::getReg(int numReg)const{
	return R[numReg-1];
}

int CPU::getPc()const{
	return PC;
}

void CPU::setPc(const int pc){
	PC = pc;
}

void CPU::setOption(int op){
	arg = op;
}
