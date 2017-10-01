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
    "\n~~~~~~��ӭ����ʯͷ��������ϷMMD~~~~~~\n",
    "----������˵��������:1.��ʼ��Ϸ 2.��Ϸ��ʷ��¼----",
    "====ʯͷ��������Ϸ��ʼ,���������====\n 1.ʯͷ\n 2.����\n 3.��\n 4.���ز˵�",
    "���:ƽ��",
    "���:�û�Ӯ",
    "���:����Ӯ",
    "^^^^^�û�:%s,����:%s^^^^^\n",
    "���ܴ��ļ�game.dat",
    "��Ϸ��ʷ��¼",
    "\n��Ϸ��ʷ��¼"
};//��Ϸ��ʾ��Ϣ��װ����

void init(void);
void startGame(void);
void historicalRecord(void);
int coreProgram(int * u_num);
void data(char ** s,int num);
void saveHistory(char **us,char **cs,char **results);

int m_num=0,back,c_num;
//�˵����,����˵����ر��,���Եĵ���
FILE * gameH;
struct gamehsave ghs[1000];
int save_size=sizeof(struct gamehsave);
int count=0;

void main(void)
{
    srand((unsigned int)time(0));//���ӱ��ʱ��
    if((gameH=fopen("game1.dat","a+b"))==NULL){
        fputs(prompts[7],stderr);
    }
    rewind(gameH);//��λ���ļ���ʼ��

    for(int i=0;i<50;i++){
        Sleep(30);//ģ����Ϸ����
        printf(">");
    }
    init();//������Ϸ
}
void init(void)
{
    puts(prompts[0]);
    while(1){
        printf("%s\n",prompts[1]);
        fflush(stdin);//��ջ�����(�������գ���������������һ��)
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
    //�鿴��¼
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
    //������Ϸ��¼
    struct gamehsave g={*us,*cs,*results};
    fwrite(&g,save_size,1,gameH);
}
int coreProgram(int * u_num)
{
    c_num=rand()%3+1;//��������0,1,2
    char *us,*cs,*promptsi;
    data(&us,*u_num);
    data(&cs,c_num);
    printf(prompts[6],us,cs);
    //�ж���Ӯtrue,false;1ʯͷ��2������3��
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
        *s="ʯͷ";
    }else if(num==2){
        *s="����";
    }else{
        *s="��";
    }
}
