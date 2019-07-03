#include "BigramMap.h"
#include "Bigram.h"
#include <iostream>
#include <string>
#include <fstream>
#include <set>

using namespace std;
template <class T>
void BigramMap<T>::readFile(const char* fileName)throw (Exception){
	ifstream ifs;
	T tdata;
	ifs.open(fileName,ifstream::in);
	vector<T> st;
	pair<T,T> pd;
	bool q;
	if(ifs.is_open()){
		while(!ifs.eof()){
			q = ifs>>tdata;
			if(q == false)
				throw(Exception(1));
			st.push_back(tdata);
		}
		st.pop_back();
	}else{
		throw(Exception(0));
	}

	for(int i = 0 ; i < st.size()-1 ; i++){
		pd.first = st[i];
		pd.second = st[i+1];
		
		bgMap[i] = pd;
	}
	return;
}

template <class T>
int BigramMap<T>::numOfGrams(const T key,const T value)const{
	int count=0;
	for(auto p= bgMap.begin() ; p != bgMap.end() ; p++ ){
		if(p->second.first == key && p->second.second == value)
			count++;
	}

	return count;
}
template <class T>
void BigramMap<T>::fcout()const{
	set<pair<T,T> > pairSet;	
	set<int> numOfGram;
	
	for(auto p = bgMap.begin() ; p != bgMap.end(); p++){
		pairSet.insert(p->second);
	}
	
	for(auto s = pairSet.begin(); s != pairSet.end(); s++){
		numOfGram.insert(numOfGrams(s->first , s->second));
	}
	
	for(auto p = numOfGram.rbegin() ; p != numOfGram.rend(); p++){
		for(auto p2 = pairSet.begin(); p2 != pairSet.end(); p2++){
			if(numOfGrams(p2->first,p2->second) == *p)
				cout<< p2->first << " "<< p2->second<<endl;
		}
	}

}

template <class T>
int BigramMap<T>::numGrams()const{
	return bgMap.size();
}

template <class T>
pair<T,T> BigramMap<T>::maxGrams()const{
	set<pair<T,T> > pairSet;	
	set<int> numOfGram;
	
	for(auto p = bgMap.begin() ; p != bgMap.end(); p++){
		pairSet.insert(p->second);
	}
	
	for(auto s = pairSet.begin(); s != pairSet.end(); s++){
		numOfGram.insert(numOfGrams(s->first , s->second));
	}
	
	auto p = numOfGram.rbegin();
	for(auto p2 = pairSet.begin() ; p2 != pairSet.end() ; p2++){
		if(numOfGrams(p2->first , p2->second) == *p)
			return *p2;
	}
}
