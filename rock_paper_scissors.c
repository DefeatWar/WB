//To achieve the stone scissors step game
//This progarm implements game history
//Author:guess what
//Edition:v1.0
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
struct gamehsave{
    char * us,*cs,*results;
};
char * prompts[10]=
{
    "\n~~~~~~欢迎进入石头剪刀布游戏MMD~~~~~~\n",
    "----请输入菜单功能序号:1.开始游戏 2.游戏历史记录----",
    "====石头剪刀布游戏开始,请输入序号====\n 1.石头\n 2.剪刀\n 3.布\n 4.返回菜单",
    "结果:平局",
    "结果:用户赢",
    "结果:电脑赢",
    "^^^^^用户:%s,电脑:%s^^^^^\n",
    "不能打开文件game.dat",
    "游戏历史记录",
    "\n游戏历史记录"
};//游戏提示信息封装数组

void init(void);
void startGame(void);
void historicalRecord(void);
int coreProgram(int * u_num);
void data(char ** s,int num);
void saveHistory(char **us,char **cs,char **results);

int m_num=0,back,c_num;
//菜单编号,输入菜单返回编号,电脑的点数
FILE * gameH;
struct gamehsave ghs[1000];
int save_size=sizeof(struct gamehsave);
int count=0;

void main(void)
{
    srand((unsigned int)time(0));//种子变成时间
    if((gameH=fopen("game1.dat","a+b"))==NULL){
        fputs(prompts[7],stderr);
    }
    rewind(gameH);//定位到文件开始处

    for(int i=0;i<50;i++){
        Sleep(30);//模拟游戏加载
        printf(">");
    }
    init();//启动游戏
}
void init(void)
{
    puts(prompts[0]);
    while(1){
        printf("%s\n",prompts[1]);
        fflush(stdin);//清空缓冲区(如果不清空，缓冲器保留了上一个)
        back=scanf("%d",&m_num);
        if(back!=0 && m_num==1){
            startGame();
            break;
        }else if(back!=0 && m_num==2){
            historicalRecord();
            break;
        }else{
            continue;
        }
    }
}
void startGame(void)
{
    fflush(stdin);
    int g_num=0;
    puts(prompts[2]);
    scanf("%d",&g_num);
    if(g_num==1 || g_num==2 || g_num==3){
        coreProgram(&g_num);
    }else{
        init();
    }
}
void historicalRecord(void)
{
    //查看记录
    rewind(gameH);
    fread(&ghs,save_size,1000,gameH);
    puts(prompts[8]);
    do{
        printf("\n user:%s,computer:%s,%s\n",ghs[count].us,ghs[count].cs,ghs[count].results);
        count++;
    }while(ghs[count].us!=NULL);
    puts(prompts[9]);
    count=0;
    startGame();
}
void saveHistory(char **us,char **cs,char **results)
{
    //保存游戏记录
    struct gamehsave g={*us,*cs,*results};
    fwrite(&g,save_size,1,gameH);
}
int coreProgram(int * u_num)
{
    c_num=rand()%3+1;//电脑输入0,1,2
    char *us,*cs,*promptsi;
    data(&us,*u_num);
    data(&cs,c_num);
    printf(prompts[6],us,cs);
    //判断输赢true,false;1石头，2剪刀，3布
    if(*u_num==c_num){
        puts(prompts[3]);
        promptsi=prompts[3];
    }else if((*u_num==1&&c_num==2)||(*u_num==2&&c_num==3)||(*u_num==3&&c_num==1)){
        puts(prompts[4]);
        promptsi=prompts[4];
    }else{
        puts(prompts[5]);
        promptsi=prompts[5];
    }
    puts("");
    saveHistory(&us,&cs,&promptsi);
    startGame();
    return -1;
}
void data(char ** s,int num)
{
    if(num==1){
        *s="石头";
    }else if(num==2){
        *s="剪刀";
    }else{
        *s="布";
    }
}
