#include <stdio.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <time.h>

#define MAX 30

int gPidList[50];
int gPidSize;
int fCheck = 0;
int clientPid;
char* mainpipe;
pid_t showResultPid;
FILE* timeServerLog;
	
void signalINT(int sigNo);
void signalUSR1(int sigNo);
void signalUSR2(int sigNo);
void reallocate(int **array,int size);
void signalHandlerChild(int sigNo);
int childStuff(int matrix[MAX][MAX],int n,char* path);
float determinant(int a[MAX][MAX], int k);
void myWait(int);
void yaz(int matrix[MAX][MAX],int n,char *path);

int main(int argc,char* argv[]){
	int check = 1; /* ana dongu degiskeni*/
	int matris[MAX][MAX];
	FILE* fptrRead; 
	char stringClientPid[sizeof(pid_t)]; 
	int i=0;
	int j=0;
	int mainFifoRead;
	pid_t pid=1;;
	int myPid = getpid();
	int fcw; /*client icin yazdirma fifo*/
	int kontrolClient = 1;
	FILE* fShowResult;
	int timeTrick;
	int mSize;
	struct timeval timeStart,timeEnd;
	long difTime;
	double det=0;
	
	if(argc != 4){
		printf("Usage : ./xx  <int>timeTrick <int>n mainPipeName<char*>");
	}

	
	mkdir("log",0777);
	sleep(0);	
	
	timeServerLog = fopen("log/0timeServer.log","w+");
	timeTrick = atoi(argv[1]);
	mSize= atoi(argv[2]);;
	mainpipe = argv[3];
	
	fprintf(timeServerLog,"Pid\tTime\tDeterminant\n");
	fclose(timeServerLog);
	timeServerLog = fopen("log/0timeServer.log","a+");
	

					
	gPidSize = 0;

	sigset_t sinyalSeti;
	sigemptyset(&sinyalSeti);
	sigaddset(&sinyalSeti,SIGUSR1);
	sigaddset(&sinyalSeti,SIGUSR2);
		
	fShowResult = fopen("sResult","a+");
	fwrite(&myPid,sizeof(int),1,fShowResult);
	fclose(fShowResult);
	
	signal(SIGINT,signalINT);
	signal(SIGUSR1,signalUSR1);
	signal(SIGUSR2,signalUSR2);
	
	sleep(0);	
	
	mkfifo(mainpipe,0666);
	mainFifoRead = open(mainpipe, O_RDONLY);

	while(check){
		myWait(timeTrick);
		if(fCheck == 1){
			sigprocmask(SIG_BLOCK,&sinyalSeti,NULL);
			read(mainFifoRead,&clientPid,sizeof(int));
			for(j =0; j < gPidSize ; j++){
				if(gPidList[j] == clientPid){
					kontrolClient = 0;
				}
			}
			if(kontrolClient == 1){
				gPidList[gPidSize] = clientPid;
				
				gPidSize++;
			}
			kontrolClient = 1;

			sprintf(stringClientPid,"%d",clientPid);
			pid = fork();

			if(pid == 0){
				signal(SIGINT,signalHandlerChild);
				
				gettimeofday(&timeStart,NULL);
				childStuff(matris,mSize,stringClientPid);
				gettimeofday(&timeEnd,NULL);
					  /* milisaniyeye donusturur */
				difTime = 1000000 *(timeEnd.tv_sec - timeStart.tv_sec) + (timeEnd.tv_usec - timeStart.tv_usec);
				
				det = determinant(matris,mSize);

				fprintf(timeServerLog,"%d\t%ld\t%.3lf\n",clientPid,difTime,det);
				
				kill(clientPid,SIGUSR1);
				fcw = open(stringClientPid,O_WRONLY);
				write(fcw,&mSize,sizeof(int));

				close(fcw);
				check = 0;
				exit(0);
			}else{
				fCheck= 0;
				wait(NULL);
				sigprocmask(SIG_UNBLOCK,&sinyalSeti,NULL);
			}
		}else if(fCheck == 2){
			sigprocmask(SIG_BLOCK,&sinyalSeti,NULL);
			fptrRead = fopen("pid_showResult","r");
			fread(&showResultPid,sizeof(pid_t),1,fptrRead);
			fclose(fptrRead);

			fCheck = 0;
			sigprocmask(SIG_UNBLOCK,&sinyalSeti,NULL);
		}
	}

	return 0;
}

int childStuff(int matrix[MAX][MAX],int n,char *data){
	int i,i2,j,j2,g=0,ip=0,jp=0,lcv=1;
	int temp[MAX][MAX];
	char str[30];

	srand(clock());
	strcpy(str,data);
	strcat(str,"_");
	

	while(g != 4){
	
		for(i = 0 ; i < n ; i++){
			for(j=0 ; j < n ; j++){
				temp[i][j] = (rand()%250)-125; 
			}
		}
		if(determinant(temp,n) != 0){
			for(i2 = 0 ; i2 < n ; i2++){
				for(j2=0 ; j2 < n ; j2++){
					matrix[i2+ip][j2+jp] = temp[i2][j2]; 
				}
			}
			g++;
			if(g == 1){
				ip = n;
				jp = 0;
			}else if(g == 2){
				ip = 0;
				jp = n;
			}else if(g == 3){
				ip = n;
				jp = n;
			}
		}
	}
	yaz(matrix,n,str);

	
	return 0;
}

void yaz(int matrix[MAX][MAX],int n,char *path){
	int i,j;
	FILE* fptr = fopen(path,"w");
	for(i = 0 ; i < n*2 ; i++){
		for(j=0 ; j < n*2 ; j++){
			fwrite(&matrix[i][j],sizeof(int),1,fptr);
		}
	}
	fclose(fptr);

}

void myWait(int miliSec){
	usleep(miliSec*1000);
}

void signalHandlerChild(int sigNo){
	if(sigNo == SIGINT){
		kill(clientPid,SIGINT);
		exit(0);
	}
}

void signalUSR2(int sigNo){
	fCheck = 2;
}

void signalUSR1(int sigNo){
	fCheck = 1;
}

void signalINT(int sigNo){
	int j;
	fclose(timeServerLog);
	//unlink("");
	unlink(mainpipe);
	unlink("sResult");
	unlink("fifodosya");
	unlink("pid_showResult");
	if(gPidSize != 0){
		for(j=0; j < gPidSize ; j++){
			usleep(10000);
			kill(gPidList[j],SIGINT);
		}
	}
	if(showResultPid != 0){
		kill(showResultPid,SIGINT);
	}
	exit(0);
}


void reallocate(int **array,int size){
	int newSize,i;
	newSize = size*2;
	int* tempArray = (int*)malloc(newSize*sizeof(int));

	for(i = 0 ; i<size ; i++){
		tempArray[i] = *(*(array)+i);
	}
	free(*array);
	*array = tempArray;
	
	return;
}



float determinant(int a[MAX][MAX], int k){
	int s = 1, b[MAX][MAX];
	float det = 0;
	int i, j, m, n, c;
	if (k == 1){
		return (a[0][0]);
	}else{
		det = 0;
		for(c = 0; c < k; c++){
			m = 0;
			n = 0;
			for(i = 0;i < k; i++){
				for (j = 0 ;j < k; j++){
					b[i][j] = 0;
					if (i != 0 && j != c){
						b[m][n] = a[i][j];
						if (n < (k - 2))
							n++;
						else{
							n = 0;
							m++;
						}
					}
				}
			}
		det = det + s * (a[0][c] * determinant(b, k - 1));
		s = -1 * s;
		}
	}
	return (det);
}
