#include "CPUProgram.h"

CPUProgram::CPUProgram(int op):option(op){
	sizeOfFile = instructions.size();
}

CPUProgram::CPUProgram(){
}

void CPUProgram::ReadFile(string param){
	string line;
	fstream fs;
	fs.open(param.c_str(),ios::in);

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
	if(instructions.size() >= index)
		return instructions[index-1];
	else
		return "";
}
