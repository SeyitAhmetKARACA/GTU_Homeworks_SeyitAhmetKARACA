//package bil241Hw8;
//import bil241Hw8.Bigram;
//import bil241Hw8.pair;
import java.util.*;
import java.io.*;

public class BigramMap<T> implements Bigram<T>{
	private Map<Integer,pair<T> > bgMap;
	private int size = 0;
	private pair<T> []pDyn;
	int dt;
	public BigramMap(int a){
		dt=a;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void readFile(String fileName) throws NoSuchElementException, FileNotFoundException {
		
		bgMap = new HashMap();
		Scanner ifs = new Scanner(new File(fileName));
		int i=0;

		while(ifs.hasNext()){
			ifs.next();
			i++;
		}
		ifs.close();
		
		ifs = new Scanner(new File(fileName));
		Object[] tmp = new Object[i];
		pDyn = new pair[i-1];
		
		int j=0;
		while( j < i){
			tmp[j] = ifs.next();
			j++;
		}
		ifs.close();
		
	    for(int k=0;k<i-1;k++){
	    	pDyn[k]= new pair<T>();
	    }
	    
	    for(int k=0;k<i-1; k++){
	    	pDyn[k].first =(T)tmp[k];
	    	pDyn[k].second = (T)tmp[k+1];
	    	bgMap.put(k,pDyn[k]);
	    }
		
	    size = i-1;
	    return;
	}
	

	@Override
	public int numGrams() {
		return size;
	}

	@Override
	public int numOfGrams(T a, T b) {
	    int count=0;
		for(int i=0;i<size ; i++){
	    	if(pDyn[i].first.toString().equals(a.toString()) == true  && pDyn[i].second.toString().equals(b.toString()) == true){
	    		count++;
	    	}
	    }

	    return count;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String toString() {
		pair<T>[] tempPair ;
		int tsize=1;
		boolean checkT = false;
		tempPair= new pair[1];
		tempPair[0] = new pair();

		tempPair[0].first = pDyn[0].first;
		tempPair[0].second = pDyn[0].second;    

		for(int i=1; i < size ; i++){
		    checkT=true;
		    for(int j=0 ; j < tsize ;j++){
		        if(tempPair[j].first.toString().equals(pDyn[i].first.toString())  &&
		        		tempPair[j].second.toString().equals(pDyn[i].second.toString()) ){
		            checkT = false;
		        }
		    }
		    
		    if(checkT){
		        pair<T>[] p2;
		        p2 = new pair[tsize];

		        for(int q=0; q < tsize ; q++){
		        	p2[q] = new pair();
		        }

		        for(int m=0; m < tsize; m++){
		            p2[m].first = tempPair[m].first;
		            p2[m].second = tempPair[m].second;
		        }
		        
		        tsize++;
		        tempPair = new pair[tsize];
		        for(int q=0; q < tsize ; q++){
		        	tempPair[q] = new pair();
		        }
		        tempPair[tsize-1] = pDyn[i];
		        
		        for(int j =0; j <= tsize-2 ; j++){
		            tempPair[j].first = p2[j].first;
		            tempPair[j].second = p2[j].second;
		        }

		        p2 = null;
		    }
		    
		}
		int []iarr = new int[tsize];
		for(int i=0;i < tsize ; i++){
		    iarr[i] = numOfGrams(tempPair[i].first,tempPair[i].second);
		}

		for(int i=0;i < tsize ; i++){
		    for(int j=0;j< tsize;j++){
		        if(iarr[i] > iarr[j]){
		            int t= iarr[i];
		            iarr[i] = iarr[j];
		            iarr[j] = t;
		            
		            pair<T> tp = tempPair[i];
		            tempPair[i] = tempPair[j];
		            tempPair[j] = tp;
		            
		        }
		    }
		}
		String ret = new String();
		for(int i=0;i<tsize ; i++){
		    ret +=tempPair[i].first +" "+tempPair[i].second+"\n";
		}
		return ret;
	}
}
