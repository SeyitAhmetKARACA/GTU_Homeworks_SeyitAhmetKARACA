#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/types.h>

pid_t pidOfServer;
int fCheck;

void signalint(int sigNo);
void signalUSR1(int sigNo);
FILE* showResultLog;

int main(int argc, char* argv[]){
	int qq;
	FILE* fServer,*fServer2;
	pid_t myPid = getpid();
	pid_t cpid;
	double r1,r2;
	int count=0;
	long timeInv,time2d;
	sigset_t sinyalSeti;
	sigemptyset(&sinyalSeti);
	sigaddset(&sinyalSeti,SIGUSR1);
	
	if(argc != 1){
		fprintf(stderr,"Usage :  ./sResult");
	}
	
	signal(SIGINT,signalint);
	signal(SIGUSR1,signalUSR1);
	fServer2 = fopen("sResult","r");
	fCheck = 0;

	
	if(fServer2 == NULL){
		printf("Server acik degil.\n");
		return 0;
	}else{
		fread(&pidOfServer,sizeof(pid_t),1,fServer2);
		fclose(fServer2);

		mkfifo("fifodosya",0666);
		qq = open("fifodosya",O_RDONLY);
		
		fServer = fopen("pid_showResult","w");
		fwrite(&myPid,sizeof(pid_t),1,fServer);
		fclose(fServer);
		
		showResultLog = fopen("log/showResultLog","w");
		fprintf(stdout,"pid\tResult1\tResult2\n");
		fclose(showResultLog);
		showResultLog = fopen("log/showResultLog","a+");
		
		fflush(stdout);
		kill(pidOfServer,SIGUSR2);
	}
	
	while(1){
		if(fCheck == 1){
			sigprocmask(SIG_BLOCK,&sinyalSeti,NULL);
			count++;
			read(qq,&cpid,sizeof(pid_t));
			read(qq,&r1,sizeof(double));
			read(qq,&r2,sizeof(double));
			read(qq,&time2d,sizeof(long));
			read(qq,&timeInv,sizeof(long));			
			fprintf(stdout,"%d\t%.3lf\t%.3lf\n",cpid,r1,r2);
			fflush(stdout);

			fprintf(showResultLog,"m%d\t%d\nResult1\t%ld\nResult2\t%ld\n\n",count,cpid,timeInv,time2d);
			fCheck = 0;
			usleep(1000);
			sigprocmask(SIG_UNBLOCK,&sinyalSeti,NULL);
		}
	}

	return 0;
}

void signalUSR1(int sigNo){
	fCheck = 1;
}

void signalint(int sigNo){
	fclose(showResultLog);
	unlink("pid_showResult");
	kill(pidOfServer,SIGINT);
	exit(0);
}
