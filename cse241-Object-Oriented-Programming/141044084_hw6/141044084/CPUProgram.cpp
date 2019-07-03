#include "CPUProgram.h"

using namespace std;

CPUProgram::CPUProgram(int op):option(op){
	sizeOfFile = instructions.size();
}

CPUProgram::CPUProgram():CPUProgram(0){
}

CPUProgram::CPUProgram(const CPUProgram& o){
	instructions.clear();
	for(int i=0;i< o.instructions.size();i++)
		instructions.push_back(o.instructions[i]);
	fname = o.fname;
	option = o.option;
	sizeOfFile = o.sizeOfFile;
}

const CPUProgram& CPUProgram::operator=(const CPUProgram& o){
	instructions.clear();
	for(int i=0;i< o.instructions.size();i++)
		instructions.push_back(o.instructions[i]);	fname = o.fname;
	option = o.option;
	sizeOfFile = o.sizeOfFile;
	return *this;
}

void CPUProgram::ReadFile(string param){
	string line;
	fstream fs;
	fs.open(param.c_str(),ios::in);
	fname = param;
	instructions.clear();
	if(fs.is_open()){
		while(!fs.eof()){
			getline(fs,line);
			if(line != "")
				instructions.push_back(toUpper(line));
		}
		fs.close();
	sizeOfFile = instructions.size();
	}else
		cerr<<"Error : The file was not found"<<endl;
}

string CPUProgram::toUpper(string param){
	for(int i=0;i< param.size();i++){
		if(param[i] >= 'a' && param[i] <= 'z')
			param[i] += 'A' - 'a';
	}
	return param;	
}

const string CPUProgram::getLine(const int index){
	if(instructions.size() > index)
		return instructions[index];
	else
		return "";
}

/* overloads */
const CPUProgram operator+(CPUProgram& c1,CPUProgram& c2){
	CPUProgram temp(c1);

	for(int i=0;i< c2.size();i++)
		temp += c2.getLine(i);
	return temp;
}

// operator <<
ostream& operator<<(ostream& output,const CPUProgram& o){
	output<<"size :";
	output<< o.size();
	return output;
}

// operator +
CPUProgram operator+(CPUProgram& o,string inst){
	if(inst == "")
		return o;
	else{
		int i = inst.find_first_not_of(" ");
		if(i == -1)
			return o;
	}

	o.instructions.push_back(inst);
	o.sizeOfFile++;
	return o;
}

const CPUProgram& CPUProgram::operator+=(string inst){
	*this = *this + inst;
	return *this;
}

const CPUProgram CPUProgram::operator--(int){
	CPUProgram temp(*this);
	if(size() == 0)
		return temp;

	instructions.pop_back();
	sizeOfFile -= 1;
	return temp;
}

const CPUProgram& CPUProgram::operator--(){
	if( size() == 0)
		return *this;

	instructions.pop_back();
	sizeOfFile -= 1;
	return *this;
}

const bool CPUProgram::operator==(const CPUProgram& o){
	return this->size() == o.size();
}

const bool CPUProgram::operator!=(const CPUProgram& o){
	return !(*this == o);
}

const bool CPUProgram::operator<(const CPUProgram& o){
	return this->size() < o.size();
}

const bool CPUProgram::operator>=(const CPUProgram& o){
	return !(*this < o);
}

const bool CPUProgram::operator>(const CPUProgram& o){
	return this->size() > o.size();
}

const bool CPUProgram::operator<=(const CPUProgram& o){
	return !(*this > o);
}

const CPUProgram CPUProgram::operator()(int a,int b){
	CPUProgram CPUtemp = *this;

	CPUtemp.instructions.clear();
	if(a > b || a < 0 || b < 0 || a> size() || b > size()){
		cerr<<"Wrong parameter for operator()(int,int)"<<endl;
		return CPUtemp;
	}
	for(int i=a;i <= b;i++)
		CPUtemp.instructions.push_back(getLine(i));

	CPUtemp.sizeOfFile = CPUtemp.instructions.size();
	return CPUtemp;
}

const string CPUProgram::operator[](int i){
	if(i >= size() || i < 0){
		cerr<<"Error : used index is false in Operator[]"<<endl;
		return "";
	}
	return getLine(i);
}

















