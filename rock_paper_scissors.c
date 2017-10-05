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
    @param(us)�û�����
    @param(cs)��������
    @param(results)��Ӯ���
*/
};
unsigned char * prompts[11]=
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
    "\n��Ϸ��ʷ��¼",
    "%%%%%����:�������%%%%%"
};
//@prarm(prompts)��Ϸ��ʾ��Ϣ��װ����

void init(void);                                        //���˵�
void startGame(void);                                   //��ʼ��Ϸ�˵�
void historicalRecord(void);                            //��Ϸ�鿴��¼(��ȡ�ṹ�ļ�)
int coreProgram(int * u_num);                           //���ĳ���
void data(char ** s,int num);                           //����ת��
void saveHistory(char **us,char **cs,char **results);   //������Ϸ��¼


int m_num=0,back,c_num;
FILE * gameH;
struct gamehsave ghs[GHS_SIZE];
int save_size=sizeof(struct gamehsave);
int count=0;
/*
Variable annotation:
    @param(m_num)�˵����
    @param(back)����˵����ر��
    @param(m_num)���Եĵ���
    @param(gameH)�����ļ�ָ��
    @param(save_size)�ṹ��С
    @param(count)�ṹ�ļ�����
*/

void main(void)
{
    srand((unsigned int)time(0));//���ӱ��ʱ��
    if((gameH=fopen("game1.dat","a+b"))==NULL){
        fputs(prompts[7],stderr);
    }
    rewind(gameH);//��λ���ļ���ʼ��

    for(int i=0;i<50;i++){
        Sleep(30);//ʵ��ģ����Ϸ����(ͨ��windows.h���Sleep())
        printf(">");
    }
    init();//������Ϸ
    getchar();
}
void init(void)
{
    puts(prompts[0]);
    while(1){
        printf("%s\n",prompts[1]);
        fflush(stdin);//��ջ�����(��֤�´�ѭ������scanf())
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
    for(int i=0;i<GHS_SIZE;i++){
        ghs[i].us=NULL;
        ghs[i].cs=NULL;
        ghs[i].results=NULL;
    }
    init();
}
void saveHistory(char **us,char **cs,char **results)
{
    //������Ϸ��¼
    struct gamehsave g={*us,*cs,*results};
    fwrite(&g,save_size,1,gameH);
}
int coreProgram(int * u_num)
{
    char *us,*cs,*promptsi;
    //@(u_num)�û���������
    //@(c_num)������������
    //@(us)�û�ת���ַ���
    //@(cs)����ת���ַ���
    //@(promptsi)��Ӯ�жϽ��

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
        *s="ʯͷ";
    }else if(num==2){
        *s="����";
    }else{
        *s="��";
    }
}
