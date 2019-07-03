#include "Cpuprogram.h"

int Cpuprogram::sizeOfFile = 0;

Cpuprogram::Cpuprogram(string cpFileName){
	readFile(cpFileName);
	sizeOfFile = instructions.size();
}

Cpuprogram::Cpuprogram(){
	readFile("");
	sizeOfFile = instructions.size();
}

void Cpuprogram::readFile(string param){
	string line;
	fs.open(param.c_str(),ios::in);

	if(fs.is_open()){
		while(!fs.eof()){
			getline(fs,line);
			if(line != "")
				instructions.push_back(toUpper(line));
		}
		fs.close();
	}else
		cerr<<"Error Error : The file was not found"<<endl;
}

string Cpuprogram::toUpper(string param){
	for(int i=0;i< param.size();i++){
		if(param[i] >= 'a' && param[i] <= 'z')
			param[i] += 'A' - 'a';
	}
	return param;	
}

const string Cpuprogram::getLine(const int index){
	if(instructions.size() >= index)
		return instructions[index-1];
	else
		return "";
}
