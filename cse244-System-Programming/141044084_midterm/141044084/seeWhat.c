#include <stdio.h>
#include <signal.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/types.h>
#include <stdlib.h>
#include <string.h>

#define MAX 30

pid_t serverPid;
pid_t showResultPid;
int fCheck;
int matrisOku;

void sigintHandle(int sigNo);
void sigUSR1(int sigNo);
char stringMyPid[sizeof(pid_t)];
void readFromFile(int matrix[MAX][MAX],FILE*,int);
void sigUSR2(int sigNo);
void transpose(int num[MAX][MAX], float fac[MAX][MAX], int r,double output[MAX][MAX]);
void cofactor(int num[MAX][MAX],double output[MAX][MAX], int f);
float determinant(int a[MAX][MAX], int k);
int myPow(int x,int y);
float determinantDouble(double a[MAX][MAX], int k);
void multidimensionalConv(int in[MAX][MAX],int xx,int kernel[][3],int out[][MAX]);
double shiftedInverse(int matrix[MAX][MAX],double output[MAX][MAX],int n);
void twoD(int matrix[MAX][MAX],int n,int kernel[3][3],int output[MAX][MAX]);
void fillKernel(int kernel[3][3],char secim);
void toLog(int matrix[][MAX],double inverse[][MAX],int twoDMatrix[][MAX],int n,char *path);

int main(int argc, char* argv[]){
	int fd[2];
	int fr;
	double result1,result2;
	int val= getpid();
	int mainFifoWrite;
	char pidMatrix[30];
	int kernelMatrix[3][3];
	int fsr,nxn;
	int clientFifoRead;
	struct timeval timeStart,timeEnd;
	long timeDif2d,timeDifInv;
	pid_t pid=1;
	int loopControl=1;
	int count=0;
	char logName[40];
	int matrix[MAX][MAX];
	double inverseMatris[MAX][MAX];
	int multiMatrix[MAX][MAX];
	pid_t pidForCalc=1;
	FILE* fServer;
	FILE* readMatrix =NULL;
	int kernelSec;
	char* kernelChar;
	FILE* fptrRead;
	char* mainpipe;
	if(argc == 3){
		kernelSec = atoi(argv[2]);
		kernelChar = argv[2];
		if(kernelSec >= 0 && kernelSec <= 2){
			fillKernel(kernelMatrix,kernelChar[0]);
		}else{
			fprintf(stderr,"Usage:   ./seeWhat <mainPipeName>");
			return 0;
		}
	}else if(argc != 2){
		fprintf(stderr,"Usage:   ./seeWhat <mainPipeName>");
		return 0;
	}
	if(argc == 2){
		fillKernel(kernelMatrix,'0');
	}
	
	strcpy(logName,"log/sw");
	mainpipe = argv[1];
	matrisOku = 0;
	
	/*sinyal seti*/
	sigset_t sinyalSeti;
	sigemptyset(&sinyalSeti);
	sigaddset(&sinyalSeti,SIGUSR1);

	
	sprintf(stringMyPid,"%d",getpid());
	

	
	signal(SIGINT,sigintHandle);
	signal(SIGUSR1,sigUSR1);
	signal(SIGUSR2,sigUSR2);
	sleep(0);

	
	
	if((fServer = fopen("sResult","r"))  == NULL){
		fprintf(stderr,"Server acik degil.\n");
		return 0;
	}else{
		mkfifo(stringMyPid,0666);
		mainFifoWrite = open(mainpipe,O_WRONLY);
		fread(&serverPid,sizeof(int),1,fServer);
		fclose(fServer);
		while(loopControl){
			
			write(mainFifoWrite,&val,sizeof(int));
			kill(serverPid,SIGUSR1);
			
			if(matrisOku == 1){
				sigprocmask(SIG_BLOCK,&sinyalSeti,NULL);
				count++;
				pid = fork();
				if(pid == 0){
					int i,j;
					clientFifoRead = open(stringMyPid,O_RDONLY);
					read(clientFifoRead,&nxn,sizeof(int));
					
					strcpy(pidMatrix,stringMyPid);
					strcat(pidMatrix,"_");

					while(readMatrix == NULL){
						readMatrix = fopen(pidMatrix,"r+");
					}
					readFromFile(matrix,readMatrix,nxn*2);
					
					/*+++++++++++ FORK VAKTİ  +++++++++++++*/
					pipe(fd); 
					pidForCalc = fork();
					if(pidForCalc == 0){
						
						gettimeofday(&timeStart,NULL);
						shiftedInverse(matrix,inverseMatris,nxn*2);
						gettimeofday(&timeEnd,NULL);						
						timeDifInv = 1000000 *(timeEnd.tv_sec - timeStart.tv_sec) + (timeEnd.tv_usec - timeStart.tv_usec);
						
						result2 = determinant(matrix,nxn*2) - determinantDouble(inverseMatris,nxn*2);
						close(fd[0]);
						write(fd[1],&result2,sizeof(double));
						write(fd[1],&timeDifInv,sizeof(long));
						for(i=0 ; i < nxn*2 ; i++){
							for(j = 0 ; j< nxn*2 ; j++){
								write(fd[1],&inverseMatris[i][j],sizeof(double));
							}
						}
						close(fd[1]);
						exit(0);
					}else{
						/* 2dconvolution */
						gettimeofday(&timeStart,NULL);
						twoD(matrix,nxn*2,kernelMatrix,multiMatrix);
						gettimeofday(&timeEnd,NULL);
						timeDif2d = 1000000 *(timeEnd.tv_sec - timeStart.tv_sec) + (timeEnd.tv_usec - timeStart.tv_usec);
						close(fd[1]);
						read(fd[0],&result2,sizeof(double));
						read(fd[0],&timeDifInv,sizeof(long));
						for(i=0 ; i < nxn*2 ; i++){
							for(j = 0 ; j< nxn*2 ; j++){
								read(fd[0],&inverseMatris[i][j],sizeof(double));
							}
						}
						close(fd[0]);
						result1 =determinant(matrix,nxn*2) -  determinant(multiMatrix,nxn*2);
						
						
						fptrRead = fopen("pid_showResult","a+");
						if(fptrRead != NULL){
							fread(&showResultPid,sizeof(pid_t),1,fptrRead);
							fclose(fptrRead);
						}
						
						if((fr = open("fifodosya",O_WRONLY)) != -1 ){
							write(fr,&val,sizeof(pid_t));
							write(fr,&result1,sizeof(double));
							write(fr,&result2,sizeof(double));
							write(fr,&timeDif2d,sizeof(long));
							write(fr,&timeDifInv,sizeof(long));
							kill(showResultPid,SIGUSR1);
						}
						/*	log kodlari    */
						sprintf(logName,"%s%d_%d",logName,val,count);
						
						toLog(matrix,inverseMatris,multiMatrix,nxn*2,logName);
						wait(NULL);
					}
					
					
					matrisOku = 0;
					loopControl = 0;
					exit(0);
					
				}else{
					wait(NULL);
				}
				sigprocmask(SIG_UNBLOCK,&sinyalSeti,NULL);
			}
			usleep(1000);
		}
	}
	return 0;
}				
				

void toLog(int matrix[][MAX],double inverse[][MAX],int twoDMatrix[][MAX],int n,char *path){
	int i,j=0;
	FILE* seeWhatLog;
	seeWhatLog = fopen(path,"a+");
	fprintf(seeWhatLog,"Orj = [");
	
	for(i=0; i < n ; i++){
		for(j=0; j < n ; j++){
			fprintf(seeWhatLog,"%d",matrix[i][j]);
			if(j !=n -1){
				fprintf(seeWhatLog,",");
			}
		}
		if(i !=n -1){
			fprintf(seeWhatLog,";\n");
		}
		
	}
	fprintf(seeWhatLog,"];\n");
	
	fprintf(seeWhatLog,"ShiftedInv = [");
	
	for(i=0; i < n ; i++){
		for(j=0; j < n ; j++){
			fprintf(seeWhatLog,"%.7lf",inverse[i][j]);
			if(j !=n -1){
				fprintf(seeWhatLog,",");
			}
		}
		if(i !=n -1){
			fprintf(seeWhatLog,";\n");
		}
		
	}
	fprintf(seeWhatLog,"];\n");
	
	fprintf(seeWhatLog,"Shifted2dConv = [");	
	for(i=0; i < n ; i++){
		for(j=0; j < n ; j++){
			fprintf(seeWhatLog,"%d",twoDMatrix[i][j]);
			if(j !=n -1){
				fprintf(seeWhatLog,",");
			}
		}
		if(i !=n -1){
			fprintf(seeWhatLog,";\n");
		}
		
	}
	fprintf(seeWhatLog,"];\n");
	
	fclose(seeWhatLog);
}


void fillKernel(int kernel[3][3],char secim){
	if(secim == '0'){
		kernel[0][0] = 0; kernel[0][1] = 0; kernel[0][2] = 0;
		kernel[1][0] = 0; kernel[1][1] = 1; kernel[1][2] = 0;
		kernel[2][0] = 0; kernel[2][1] = 0; kernel[2][2] = 0;
	}else if(secim == '1'){
		kernel[0][0] = 1; kernel[0][1] = 1; kernel[0][2] = -1;
		kernel[1][0] = -1; kernel[1][1] = 1; kernel[1][2] = -1;
		kernel[2][0] = 1; kernel[2][1] = 1; kernel[2][2] = 1;			
	}else if(secim == '2'){
		kernel[0][0] = 0; kernel[0][1] = 1; kernel[0][2] = -1;
		kernel[1][0] = -1; kernel[1][1] = 0; kernel[1][2] = -1;
		kernel[2][0] = 1; kernel[2][1] = 1; kernel[2][2] = 0;
	}
}

void twoD(int matrix[MAX][MAX],int n,int kernel[3][3],int output[MAX][MAX]){
	int i,j,g=0,ip=0,jp=0,q,w;
	int temp[MAX][MAX];
	int tempOutput[MAX][MAX];
	while(g != 4){
		for(i=0; i < n/2 ; i++){
			for(j=0; j < n/2 ; j++){
				temp[i][j] = matrix[i+ip][j+jp];
			}
		}

		multidimensionalConv(temp,n/2,kernel,tempOutput);
		
		for(i=0; i < n; i++){
			for(j=0;j< n ; j++){
				output[i+ip][j+jp] = tempOutput[i][j];
			}
		}

		g++;
		if(g == 1){
			ip = n/2;
			jp = 0;
		}else if(g == 2){
			ip = 0;	
			jp = n/2;
		}else if(g == 3){
			ip = n/2;
			jp = n/2;
		}
	}
}

void multidimensionalConv(int in[MAX][MAX],int xx,int kernel[][3],int out[][MAX]){
	int i,kCenterX,kCenterY,j,k,mm,nn,n,m,ii,jj;
	int cols,rows;
	int kCols,kRows;
	kRows = kCols = 3;
	cols = rows = xx;
	
	kCenterX = kCols / 2;
	kCenterY = kRows / 2;

	for(i=0; i < rows; ++i){
		for(j=0; j < cols; ++j){
			for(m=0; m < kRows; ++m){
				mm = kRows - 1 - m;
				for(n=0; n < kCols; ++n){
					nn = kCols - 1 - n;
					ii = i + (m - kCenterY);
					jj = j + (n - kCenterX);
					if( ii >= 0 && ii < rows && jj >= 0 && jj < cols )
						out[i][j] += in[ii][jj] * kernel[mm][nn];
				}
			}
		}
	}
}

void readFromFile(int matrix[MAX][MAX],FILE* matrisoku,int n){
	int i,j;
	int a;
	for(i=0;i < n; i++){
		for(j=0; j<n; j++){
			fread(&matrix[i][j],sizeof(int),1,matrisoku);
		}
	}
	fclose(matrisoku);	
}
void sigUSR2(int sigNo){
	
}

void sigUSR1(int sigNo){

	matrisOku = 1;
}

void sigintHandle(int sigNo){
	char UnPidMatrix[30];
	strcpy(UnPidMatrix,stringMyPid);
	strcat(UnPidMatrix,"_");
	if(sigNo == SIGINT){
		unlink("pid_showResult");
		unlink(UnPidMatrix);
		unlink(stringMyPid);
		kill(serverPid,SIGINT);

		exit(0);
	}
}

float determinantDouble(double a[MAX][MAX], int k){
	int s = 1,b[MAX][MAX];
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


float determinant(int a[MAX][MAX], int k){
	int s = 1,b[MAX][MAX];
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

double shiftedInverse(int matrix[MAX][MAX],double output[MAX][MAX],int n){
	int i,j,g=0,ip=0,jp=0,q,w;
	int temp[MAX][MAX];
	double tempOutput[MAX][MAX];
	while(g != 4){
		for(i=0; i < n/2 ; i++){
			for(j=0; j < n/2 ; j++){
				temp[i][j] = matrix[i+ip][j+jp];
			}
		}
	
		cofactor(temp,tempOutput,n/2);
			
		for(i=0; i < n/2 ; i++){
			for(j=0; j < n/2 ; j++){
				output[i+ip][j+jp] = tempOutput[i][j];
			}
		}
		g++;
		if(g == 1){
			ip = n/2;
			jp = 0;
		}else if(g == 2){
			ip = 0;	
			jp = n/2;
		}else if(g == 3){
			ip = n/2;
			jp = n/2;
		}
	}
}


void cofactor(int num[MAX][MAX],double output[MAX][MAX], int f){
	int b[MAX][MAX];
	float fac[MAX][MAX];
	int p, q, m, n, i, j;
	
	for (q = 0;q < f; q++){
		for (p = 0;p < f; p++){
			m = 0;
			n = 0;
			for (i = 0;i < f; i++){
				for (j = 0;j < f; j++){
					if (i != q && j != p){
						b[m][n] = num[i][j];
						if (n < (f - 2))
							n++;
						else{
							n = 0;
							m++;
						}
					}
				}
			}
		fac[q][p] = myPow(-1, q + p) * determinant(b, f - 1);
		}
	}
	transpose(num, fac, f,output);
}

int myPow(int x,int y){
	int i,result = 1;
	if(x != 0 && y == 0)
		return 1;
	
	for(i=0;i < y ; i++)
		result *= x;

	return result;
}

void transpose(int num[MAX][MAX], float fac[MAX][MAX], int r,double output[MAX][MAX]){
	int i, j;
	float b[MAX][MAX], inverse[MAX][MAX], d;
	
	for (i = 0;i < r; i++){
	for (j = 0;j < r; j++){
		b[i][j] = fac[j][i];
		}
	}

	d = determinant(num, r);

	for (i = 0;i < r; i++){
		for (j = 0;j < r; j++){
			inverse[i][j] = b[i][j] / d;
			output[i][j] = inverse[i][j];
		}
	}
}
