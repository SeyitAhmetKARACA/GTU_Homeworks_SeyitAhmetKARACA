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
/* buraya geri don !!! */
GTUOS::GTUOS(char* filename){
    ifstream inFile;
    int index=0;
    bool endOfFile = true;
    size_t found;
    inFile.open(filename);
    string tempData ="";
    string data="";
    if (inFile.is_open()) {
	while (!inFile.eof()) {
		getline(inFile,tempData);
		found = tempData.find("!");
		if(tempData.find("/sakFile/") < 300){
			endOfFile = false;
		}

		if(found > tempData.size()){ // bak buraya !!
        		data+= tempData;
		}else{
			data+= tempData;
			fileTable.push_back(parse(data,"*"));
			fileTable.push_back(parse(data,"*"));
			fileTable.push_back(parse(data,"*"));
			fileTable.push_back(parse(data,"*"));
			fileTable.push_back(parse(data,"*"));
			fileTable.push_back(parse(data,"*"));
			
			data = "";
		}

	}
    }

    inFile.close();
    seekVal = 0;
    fileID = -1;
}

std::string GTUOS::parse(std::string& str,string seperator){
    size_t pos = 0;
    int cammaPos = 0;
    std::string retString = "";

    if((pos = str.find(seperator)) != std::string::npos){
       retString = str.substr(0,pos);
       str.erase(0,pos+1);
    }
    return retString;
}

void GTUOS::roundRobin(unsigned int & cycle,CPU8080 & cpu){
	if(cycle >= 100 && threadTable.size() > 1){
		
		threadTable[threadindex].state = *cpu.state;
		//cout<<"thread "<<threadindex;	
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
        case 12:
                returnValue = FileCreate(cpu);
                break;
        case 13:
                returnValue = FileClose(cpu);
                break;
        case 14:
                returnValue = FileOpen(cpu);
                break;
        case 15:
                returnValue = FileRead(cpu);
                break;
        case 16:
                returnValue = FileWrite(cpu);
                break;
        case 17:
                returnValue = FileSeek(cpu);
                break;
        case 18:
                returnValue = DirRead(cpu);
                break;
    }
    
	return returnValue;
}

//////////////////////////////////////////////////////////////////////////+++
int GTUOS::FileCreate(const CPU8080 & cpu){
	int i;
	bool checkName = false;
	string localData = "";
	for(int i=cpu.state->b*256+cpu.state->c; unsigned(cpu.memory->physicalAt(i)) != 10 ; i++)
		localData += cpu.memory->physicalAt(i);
	
	for(i=0;i<fileTable.size();i++){
		if(fileTable[i].compare(localData) == 0)
			checkName = true;
	}

	if(checkName){
		//cout<<"cannot add"<<localData<<endl;
		return 0;
	}else{
		fileTable.push_back(localData); // dosya adi
		fileTable.push_back(gettime());	// creation time
		fileTable.push_back(gettime());
		fileTable.push_back(gettime());
		fileTable.push_back("0");
		fileTable.push_back("");
		//cout<<"added "<<localData<<endl;
		return 1;
	}
}

std::string GTUOS::gettime() {
	time_t timeVal;
	struct tm * timeinfo;
	char buffer[100];

	time (&timeVal);
	timeinfo = localtime(&timeVal);

	strftime(buffer,sizeof(buffer),"%d-%m-%Y %I:%M:%S",timeinfo);
	string returnStr(buffer);

	return returnStr;
}

void GTUOS::writeToSystem(){
	ofstream outFile;
	int intBlock=0;
	long longValue = 0;
	string strBlock = "";
	outFile.open("filesystem.dat",ios::in);	
	for(int i=0;i<fileTable.size();i+=6){
		for(int j=0;j<6;j++){
			strBlock += fileTable[i+j] + "*";
		}
		//intBlock += strBlock.size();
		outFile<<endl;

		for(int j=0;j < strBlock.size() ; j++){
			outFile << strBlock[j];
			if(j != 0 && j % 256 == 0)
				outFile << '\n';
		}
		outFile<<"!";
		strBlock = "";
	}
	outFile << "/sakFile/\n";

	longValue = (1000*1000) - intBlock;
	for(int i=0;i < longValue ; i++){
		outFile <<'\0';
		if(i % 256 == 0)
			outFile << endl;
	}

	outFile.close();


	return;
}


int GTUOS::FileClose(const CPU8080 & cpu){
	writeToSystem();
	int fileHandler;
	int k;
	int temp;
	for(k=0;k<fileIdTable.size();k++)
		if(fileID == fileIdTable[k])
			fileHandler = fileID;

	if(cpu.state->b == fileHandler){
		//cout << "closed "<<fileTable[cpu.state->b]<<"-- "<<k<<endl;//<<endl<<fileTable[cpu.state->b+5] << endl;
		cpu.state->b = 1;
		fileID = -1;
		temp = fileIdTable[k];
		fileIdTable[k] = fileIdTable[fileIdTable.size()-1];
		fileIdTable[fileIdTable.size()-1] = temp;
//		if(fileIdTable.size() != 0)
			fileIdTable.pop_back();
		return 1;
	}

	cpu.state->b = 0;
	return 0;
}

int GTUOS::FileOpen(const CPU8080 & cpu){	
	int i;
	int j;
	seekVal = 0;
	bool checkName = false;
	string localData = "";
	for(int i=cpu.state->b*256+cpu.state->c; unsigned(cpu.memory->physicalAt(i)) != 10 ; i++)
		localData += cpu.memory->physicalAt(i);
	
	for(i=0;i<fileTable.size();i++){
		if(fileTable[i].compare(localData) == 0){
			checkName = true;
			j = i;
		}
	}
	
	
	if(checkName){
		fileTable[j+2] = gettime();
		fileTable[j+3] = gettime();
		cpu.state->b = j;
		fileID = j;
		fileIdTable.push_back(fileID);
		//cout << "opened "<<j<<" +++++++++++++++ "<<fileID<<endl;
	}else{
		cpu.state->b = -1;
		fileID = -1;		
		return 0;
	}
	return 1;
}

int GTUOS::FileRead(const CPU8080 & cpu){
	//cout<<"reading : "<<cpu.state->d<<endl;
	int i,j=0,k=0;
	int fileHandler;
	for(int k=0;k<fileIdTable.size();k++)
		if(fileID == fileIdTable[k])
			fileHandler = fileID;

	if(fileHandler == cpu.state->e){
		for(i =cpu.state->b*256+cpu.state->c; k < cpu.state->d;j++){
			if(fileTable[cpu.state->e+5][j] == ' '){
				continue;	
			}else
				k++;
			cpu.memory->physicalAt(i) = fileTable[cpu.state->e+5][j];
			seekVal++;
			i++;
		}
		cpu.memory->physicalAt(i) = '\0';
		cpu.state->b = i+seekVal;
	}
	
	fileTable[cpu.state->e+3] = gettime();
	return 0;
}


int GTUOS::FileWrite(const CPU8080 & cpu){
	//cout<<"write "<<(int)cpu.state->e<<endl;
	int j=0;
	int i= cpu.state->b*256+cpu.state->c;
	string x="";
	for(j = 0; j < cpu.state->d; j++){
		seekVal++;
		x += to_string((int)cpu.memory->physicalAt(i))+" ";
		i++;
	}
	x += '\0';
	fileTable[cpu.state->e+2] = gettime();
	fileTable[cpu.state->e+3] = gettime();
	fileTable[cpu.state->e+4] = to_string(int(i - cpu.state->b*256+cpu.state->c));
	fileTable[cpu.state->e+5] = x;
	cpu.state->b = seekVal;

	return 0;
}

int GTUOS::FileSeek(const CPU8080 & cpu){
	int i;
	int sizeOfContent;
	sizeOfContent = fileTable[cpu.state->d+5].size();

	for(i =cpu.state->b*256+cpu.state->c; unsigned(cpu.memory->physicalAt(i)) != '\0'; i++){
		if(i > sizeOfContent)
			cpu.state->b = 0;
			return 0;
	}
	sizeOfContent -= i;
	seekVal = sizeOfContent;
	//cout<<"fileSeek position: "<<seekVal<<endl;
	cpu.state->b = 1;
	return 1;
}

void GTUOS::mod0(){
	string content = "";
	for(int k=0;k+5 < fileTable.size();){
		for(int i=0;i<5;i++)
			content+=fileTable[i+k] + " ";
		content+="\n";
		k+=6;
	}

	cout<<endl<<content<<endl;

	return;
}

void GTUOS::mod1(){	
	for(int i=0;i<fileIdTable.size();i++){
		cout<<"contents of the file table :"<<fileTable[fileIdTable[i]]<<" "<<fileTable[fileIdTable[i]+1]<<" "<<fileTable[fileIdTable[i]+4]<<endl;
	}

	return;
}

int GTUOS::DirRead(const CPU8080 & cpu){
	int i = cpu.state->b*256+cpu.state->c;

	for(int k=0;k < fileTable.size();){
		for(int j=0; j < fileTable[k].size(); j++,i++){
			cpu.memory->physicalAt(i) = fileTable[k][j];
		}
		cpu.memory->physicalAt(i) = ' ';
		i++;
		for(int j=0; j < fileTable[k+1].size(); j++,i++){
			cpu.memory->physicalAt(i) = fileTable[k+1][j];
		}
		cpu.memory->physicalAt(i) = ' ';
		i++;
		for(int j=0; j < fileTable[k+2].size(); j++,i++){
			cpu.memory->physicalAt(i) = fileTable[k+2][j];
		}
		cpu.memory->physicalAt(i) = ' ';
		i++;
		for(int j=0; j < fileTable[k+3].size(); j++,i++){
			cpu.memory->physicalAt(i) = fileTable[k+3][j];
		}
		cpu.memory->physicalAt(i) = ' ';
		i++;
		for(int j=0; j < fileTable[k+4].size(); j++,i++){
			cpu.memory->physicalAt(i) = fileTable[k+4][j];
		}
		cpu.memory->physicalAt(i) = ' ';
		i++;

		cpu.memory->physicalAt(i) = '\n';
		i++;
		k+=6;
	
	}
	cpu.memory->physicalAt(i) = '\0';
	
	return 0;
}


int GTUOS::TExit(CPU8080 & cpu){
	//cout<<threadTable[threadindex].threadID<<endl;
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
    cout<<(unsigned)cpu.state->b<<endl;
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

    for(int i=cpu.state->b*256+cpu.state->c; cpu.memory->physicalAt(i) != '\0' ; i++)
        cout<<cpu.memory->physicalAt(i);
    cout<<endl;
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
