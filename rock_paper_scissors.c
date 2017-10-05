/*
    (rock paper scissors game)

    @author : confidentiality
    @version : V1.3
    @working team: MMD
    @creation time : 2017/10/1
    @creation site : SuZhou
    @programming language : C
*/
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#define GHS_SIZE 1000
struct gamehsave{
    char * us,*cs,*results;
/*
Structure variable annotation:
    @param(us)用户输入
    @param(cs)电脑输入
    @param(results)输赢结果
*/
};
unsigned char * prompts[11]=
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
    "\n游戏历史记录",
    "%%%%%警告:输入错误%%%%%"
};
//@prarm(prompts)游戏提示信息封装数组

void init(void);                                        //主菜单
void startGame(void);                                   //开始游戏菜单
void historicalRecord(void);                            //游戏查看记录(读取结构文件)
int coreProgram(int * u_num);                           //核心程序
void data(char ** s,int num);                           //数据转换
void saveHistory(char **us,char **cs,char **results);   //保存游戏记录


int m_num=0,back,c_num;
FILE * gameH;
struct gamehsave ghs[GHS_SIZE];
int save_size=sizeof(struct gamehsave);
int count=0;
/*
Variable annotation:
    @param(m_num)菜单编号
    @param(back)输入菜单返回编号
    @param(m_num)电脑的点数
    @param(gameH)保存文件指针
    @param(save_size)结构大小
    @param(count)结构文件计数
*/

void main(void)
{
    srand((unsigned int)time(0));//种子变成时间
    if((gameH=fopen("game1.dat","a+b"))==NULL){
        fputs(prompts[7],stderr);
    }
    rewind(gameH);//定位到文件开始处

    for(int i=0;i<50;i++){
        Sleep(30);//实现模拟游戏加载(通过windows.h里的Sleep())
        printf(">");
    }
    init();//启动游戏
    getchar();
}
void init(void)
{
    puts(prompts[0]);
    while(1){
        printf("%s\n",prompts[1]);
        fflush(stdin);//清空缓冲区(保证下次循环进入scanf())
        back=scanf("%d",&m_num);

        switch(m_num){
            case 1:startGame();break;
            case 2:historicalRecord();break;
            default:
                puts(prompts[10]);
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
        if(g_num!=4){puts(prompts[10]);}
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
    for(int i=0;i<GHS_SIZE;i++){
        ghs[i].us=NULL;
        ghs[i].cs=NULL;
        ghs[i].results=NULL;
    }
    init();
}
void saveHistory(char **us,char **cs,char **results)
{
    //保存游戏记录
    struct gamehsave g={*us,*cs,*results};
    fwrite(&g,save_size,1,gameH);
}
int coreProgram(int * u_num)
{
    char *us,*cs,*promptsi;
    //@(u_num)用户输入数字
    //@(c_num)电脑输入数字
    //@(us)用户转义字符串
    //@(cs)电脑转义字符串
    //@(promptsi)输赢判断结果

    c_num=rand()%3+1;
    data(&us,*u_num);
    data(&cs,c_num);
    printf(prompts[6],us,cs);
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
