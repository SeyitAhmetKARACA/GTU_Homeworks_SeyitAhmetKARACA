#ifndef MEMORY_H__
#define MEMORY_H__

#define NUMBER_OF_MEMORY 50

using namespace std;

class Memory{
public:
	Memory();
	Memory(int cOption);
	const void setMem(const int index,const unsigned int value);
	const unsigned int getMem(const int index)const{ return M[index];}
	const void printAll()const;
	int getOption(){return option;};
private:
	unsigned int M[NUMBER_OF_MEMORY];
	int option;
};

#endif
