#define NDEBUG
#include <iostream>
#include <string>
#include "CPUProgramDyn.h"
#include <cassert>

namespace dyn{

const string CPUProgramDyn::getLine(const int index)const{
	assert(this->size() <= index);
	if(this->size() > index)
		return instructions[index];
}

/* friend operator + CC*/
const CPUProgramDyn operator+(CPUProgramDyn& c1,CPUProgramDyn& c2){
	CPUProgramDyn temp(c1);
	
	for(int i=0;i< c2.size(); i++)
		temp += c2.getLine(i);
	
	return temp;
}
/* operator += */
const CPUProgramDyn& CPUProgramDyn::operator+=(string s){
	*this = *this + s;
	return *this;
}

/* operator << */
ostream& operator<<(ostream& output,const CPUProgramDyn& o){
	for(int i = 0 ; i < o.size();i++)
		output<<o.getLine(i)<<endl;
	return output;
}

/* friend operator +  */
CPUProgramDyn operator+(CPUProgramDyn& c,string s){	
	CPUProgramDyn temp(c);
	
	string* ns = new string[temp.size()+1];
	
	for(int i = 0;i< temp.size() ; i++)
		ns[i] = temp.instructions[i];
	ns[temp.size()] = s;
	delete [] temp.instructions;
	temp.sizeOfFile++;
	temp.instructions = ns;
	return temp;
}

/* Copy constructor */
CPUProgramDyn::CPUProgramDyn(const CPUProgramDyn& o){
	if(o.size() != 0 && o.instructions != '\0'){
		instructions = new string[o.size()];
		for(int i = 0;i< o.size(); i++){
			instructions[i] = o.getLine(i);
		}
	}

	option = o.option;
	sizeOfFile = o.sizeOfFile;
	fname = o.fname;
}

/* Destructor */
CPUProgramDyn::~CPUProgramDyn(){

	if(instructions != nullptr && size() != 0)
		delete []instructions;
}
/* assignment operator */
const CPUProgramDyn CPUProgramDyn::operator=(const CPUProgramDyn& o){
	if( instructions != nullptr)
		delete []instructions;
	
	instructions = new string[o.size()];//+1

	for(int i = 0;i< o.size(); i++){
		instructions[i] = o.getLine(i);
	}
	option = o.option;
	sizeOfFile = o.sizeOfFile;
	fname = o.fname;

	return *this;
}
/* one parameter constructor */
CPUProgramDyn::CPUProgramDyn(int op):option(op){

	instructions = nullptr;
	sizeOfFile = 0;
	fname = "";
}
/*	no parameter consttructor */
CPUProgramDyn::CPUProgramDyn():CPUProgramDyn(0){
}

void CPUProgramDyn::setSize(string* inst){
	int i=0;
	if(inst == nullptr){
		sizeOfFile = 0;
		return;
	}	
	for(int j=0; inst[i][j] != '\0'; i++)
		;

	sizeOfFile = i;
	return;
}
void CPUProgramDyn::ReadFile(string param){
	fstream fs;
	fs.open(param.c_str(),ios::in);
	fname = param;
	int tSize=0;
	int i=0;
	bool opened = fs.is_open();
	if(opened){
		string a;
		while(!fs.eof()){
			getline(fs,a);
			if(a != "")
				tSize++;
		}
		fs.close();

		sizeOfFile = tSize;
		instructions = new string[tSize+1];
		instructions[tSize] = '\0';

		fs.open(param.c_str(),ios::in);
		while(!fs.eof()){
			getline(fs, a);
			if( a != ""){
				instructions[i] = a;		
			i++;
			}
		}
		fs.close();
	}else{
		cerr<<"Error : The file was not found"<<endl;
		assert(!opened);
	}
	return;
}

string CPUProgramDyn::toUpper(string param){
	for(int i=0;i< param.size();i++){
		if(param[i] >= 'a' && param[i] <= 'z')
			param[i] += 'A' - 'a';
	}
	return param;	
}
/* operator index*/
const string CPUProgramDyn::operator[](int i){
	if(i >= size() || i < 0){
		cerr<<"Error : used index is false in Operator[]"<<endl;
		assert(i >= size() || i < 0);
	}
	return getLine(i);
}
/* logic operators*/
const bool CPUProgramDyn::operator==(const CPUProgramDyn& o){
	return this->size() == o.size();
}

const bool CPUProgramDyn::operator!=(const CPUProgramDyn& o){
	return !(this->size() == o.size());
}

const bool CPUProgramDyn::operator< (const CPUProgramDyn& o){
	return size() < o.size();
}

const bool CPUProgramDyn::operator> (const CPUProgramDyn& o){
	return size() > o.size();
}

const bool CPUProgramDyn::operator<=(const CPUProgramDyn& o){
	return !(*this > o);
}

const bool CPUProgramDyn::operator>=(const CPUProgramDyn& o){
	return !(*this < o);
}

/* operator Function call*/
const CPUProgramDyn CPUProgramDyn::operator()(int a ,int b){
	CPUProgramDyn CPUtemp(*this);

	if(a > b || a < 0 || b < 0 || a> size() || b > size()){
		cerr<<"Wrong parameter for operator()(int,int)"<<endl;
		assert(a > b || a < 0 || b < 0 || a> size() || b > size());
		return CPUtemp;
	}
	
	string* ns = new string[b-a+1];
	for(int i = a ; i<=b ; i++)
		ns[i-a] = getLine(i);

	delete[] CPUtemp.instructions;
	CPUtemp.instructions = ns;
	CPUtemp.sizeOfFile = b-a;
	return CPUtemp;
}

const CPUProgramDyn CPUProgramDyn::operator--(int){
	CPUProgramDyn temp(*this);
	if(size() == 0)
		return temp;
	if(temp.instructions != nullptr){
		string *s = new string[size()];
		s[size()-1] = '\0';
		for(int i = 0 ; i < size()-1 ; i ++)
			s[i] = temp.instructions[i];
		delete []instructions;
		instructions = s;
		sizeOfFile -= 1;
	}
	return temp;
}

const CPUProgramDyn& CPUProgramDyn::operator--(){
	if( size() == 0)
		return *this;

	string *s = new string[size()];
	for(int i = 0 ; i < size()-1 ; i ++)
		s[i] = instructions[i];
	s[size()-1] = '\0';
	delete []instructions;
	instructions = s;
	sizeOfFile -= 1;
	return *this;
}

} // namespace dyn
