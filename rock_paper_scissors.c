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
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#define GHS_SIZE 1000
struct gamehsave{
    //const char * us,*cs,*results;
    char us[3],cs[3],results[3];
/*
Structure variable annotation:
    @param(us)�û�����
    @param(cs)��������
    @param(results)��Ӯ���
*/
};
unsigned char prompts[15][100]=
{
    "\n~~~~~~��ӭ����ʯͷ��������ϷMMD~~~~~~\n\0",
    "----������˵��������:1.��ʼ��Ϸ 2.��Ϸ��ʷ��¼----",
    "====ʯͷ��������Ϸ��ʼ,���������====\n 1.ʯͷ\n 2.����\n 3.��\n 4.���ز˵�",
    "���:ƽ��",
    "���:�û�Ӯ",
    "���:����Ӯ",
    "^^^^^�û�:%s,����: %s ^^^^^\n\0",
    "���ܴ��ļ�game.dat",
    "��Ϸ��ʷ��¼",
    "\n��Ϸ��ʷ��¼",
    "%%%%%����:�������%%%%%\a",
    "ʯͷ\0",
    "����\0",
    "��\0"
};
//@prarm(prompts)��Ϸ��ʾ��Ϣ��װ����

void init(void);                                        //���˵�
void startGame(void);                                   //��ʼ��Ϸ�˵�
void historicalRecord(void);                            //��Ϸ�鿴��¼(��ȡ�ṹ�ļ�)
void coreProgram(int u_num);                           //���ĳ���
char * date(int num);                                  //����ת��
void saveHistory(char * us,char * cs,char * results);   //������Ϸ��¼


int m_num=0,back;
FILE * gameH;
struct gamehsave ghs[GHS_SIZE];
int save_size=sizeof(struct gamehsave);
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
        coreProgram(g_num);
    }else{
        if(g_num!=4){puts(prompts[10]);}
        init();
    }
}

void historicalRecord(void)
{
    //�鿴��¼
    int count=0;
    rewind(gameH);
    fread(&ghs,save_size,1000,gameH);
    puts(prompts[8]);
    while(ghs[count].us!=NULL){
        printf("\n user:%s,computer:%s,%s\n",ghs[count].us,ghs[count].cs,ghs[count].results);
        count++;
    }
    puts(prompts[9]);
    count=0;
    for(int i=0;i<GHS_SIZE;i++){
        memset(ghs[i].us,0,3);//�ڴ��ʼ��
        memset(ghs[i].cs,0,3);//�ڴ��ʼ��
        memset(ghs[i].results,0,4);//�ڴ��ʼ��
    }
    init();
}


void saveHistory(char * us,char * cs,char * results)
{
    //������Ϸ��¼
    struct gamehsave g={*us,*cs,*results};

    fwrite(&g,save_size,1,gameH);
}

void coreProgram(int u_num)
{
    unsigned char promptsi[3],us[4],cs[4];
    //@(u_num)�û���������
    //@(c_num)������������
    //@(us)�û�ת���ַ���
    //@(cs)����ת���ַ���
    //@(promptsi)��Ӯ�жϽ��

    int c_num=rand()%3+1;
    strcpy(us,date(u_num));
    strcpy(cs,date(c_num));

    printf("^^^^^�û�:%s,����: %s ^^^^^\n",us,cs);

    if(u_num==c_num){
        puts(prompts[3]);
        memcpy(promptsi,prompts[3],strlen(prompts[3]));
    }else if((u_num==1&&c_num==2)||(u_num==2&&c_num==3)||(u_num==3&&c_num==1)){
        puts(prompts[4]);
        memcpy(promptsi,prompts[4],strlen(prompts[4]));
    }else{
        puts(prompts[5]);
        memcpy(promptsi,prompts[5],strlen(prompts[5]));
    }
    puts("");

    //struct gamehsave g={us,cs,results};

    //fwrite(&g,save_size,1,gameH);

    startGame();
}
char * date(int num)
{
    switch(num){
        case 1:return prompts[11];
        case 2:return prompts[12];
        case 3:return prompts[13];
    }
}
