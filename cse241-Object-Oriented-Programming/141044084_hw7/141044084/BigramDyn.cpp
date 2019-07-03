#include "BigramDyn.h"
#include <iostream>
#include <fstream>


using namespace std;

template <class T>
void BigramDyn<T>::readFile(const char* fileName)throw(Exception){
	ifstream ifs;
	ifs.open(fileName,ifstream::in);
	T *dData;

	if(ifs.is_open()){
		int count=0;
		T input;
		bool checkError;
		while(!ifs.eof()){
			checkError = ifs>>input;
			if(!checkError)
				throw(Exception(1));

			count++;
		}
		ifs.close();
		ifs.open(fileName,ifstream::in);
		
		dData = new T[count];
		int i=0;

		while(!ifs.eof()){
			ifs>>dData[i];			
			i++;
		}
		ifs.close();
		pDyn = new pair<T,T >[count-1];
		
		for(int j = 0; j < count-1 ; j++){
			pDyn[j].first = dData[j];
			pDyn[j].second = dData[j+1];
		}
		
		size = count-2;
	}else{
		throw(Exception(0));
	}
	
	return;
}

template <class T>
int BigramDyn<T>::numGrams()const{
	return size;
}
template <class T>
pair<T,T> BigramDyn<T>::maxGrams()const {
	int max=0;
	int j=0;
	for(int i=0;i < size; i++){
		if( numOfGrams(pDyn[i].first , pDyn[i].second) > max){
			max = numOfGrams(pDyn[i].first , pDyn[i].second);
			j = i;
		}
	}
	
	return pDyn[j];
}

template <class T>
int BigramDyn<T>::numOfGrams(const T f,const T s)const{
	int count=0;
	for(int i=0; i < size ; i++){
		if(pDyn[i].first == f && pDyn[i].second == s)
			count++;
	}
	return count;
}

template <class T>
void BigramDyn<T>::fcout()const{
	pair<T,T> *tempPair = nullptr;
	int tsize=1;
	bool checkT = false;
	tempPair= new pair< T,T >[1];
	tempPair[0].first = pDyn[0].first;
	tempPair[0].second = pDyn[0].second;
	
	for(int i=1; i < size ; i++){
		checkT=true;
		for(int j=0 ; j < i ;j++){
			if(tempPair[j].first == pDyn[i].first && tempPair[j].second == pDyn[i].second){
				checkT = false;
			}
		}
		if(checkT){
			pair<T,T> *p2;
			p2 = new pair<T,T>[tsize];
			for(int m=0; m < tsize; m++){
				p2[m].first = tempPair[m].first;
				p2[m].second = tempPair[m].second;
			}
			
			if(tempPair != nullptr)
				delete [] tempPair;

			tsize++;
			tempPair = new pair<T,T>[tsize];
			tempPair[tsize-1] = pDyn[i];
			for(int j =0; j <= tsize-2 ; j++){
				tempPair[j].first = p2[j].first;
				tempPair[j].second = p2[j].second;
			}
	
			delete [] p2;
		}
	}

	int *iarr = new int[tsize];
	for(int i=0;i < tsize ; i++){
		iarr[i] = numOfGrams(tempPair[i].first,tempPair[i].second);
	}
	
	for(int i=0;i < tsize ; i++){
		for(int j=0;j< tsize;j++){
			if(iarr[i] > iarr[j]){
				int t= iarr[i];
				iarr[i] = iarr[j];
				iarr[j] = t;
				
				pair<T,T> tp = tempPair[i];
				tempPair[i] = tempPair[j];
				tempPair[j] = tp;
				
			}
		}
	}
	
	for(int i=0;i<tsize ; i++){
		cout<<tempPair[i].first<<" "<<tempPair[i].second<<endl;
	}
	
	return;
}
