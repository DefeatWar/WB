/*
	(Optical Cube+Digital Tube+Button)
	@autgor : confidentiality
	@version : V1.3
	@working team : MMD
	@creation time : 2017/12/9
	@creation site : SuZhou
	@programming language : C
*/

#include <reg52.h>
#define uchar unsigned char
#define uint unsigned int 

sbit hc595_sh=P2^5;
sbit hc595_ds=P2^7;
sbit hc595_st=P2^6;

sbit button1=P3^5;
sbit buzzer=P3^6;

sbit P2_3=P2^3;

int digital_num=0;
int pwm=0;
uchar code table[]={0xc0,0xf9,0xa4,0xb0,0x99,0x92,0x82,0xf8,0x80,0x90};
//-------------------------------------------------------------
unsigned char modecode1[][2]={
							{0x00,0xff},
							{0xff,0x00},
							{0x00,0x00},
							{0xff,0xff},
							{0x0f,0xff},
							{0xff,0xff}
						};
int modedelay1[]={1000,1000,1000,1000,500,500};
unsigned char modeh1[]={0x0f,0x0f,0x0f,0x0f,0x0f,0x0f};
//--------------------------------------------------------------
unsigned char modecode2[][2]={
							{0x00,0x00},
							{0x00,0xff},
							{0xff,0x00},
							{0xff,0xff},
							{0x0f,0xff},
							{0xff,0xff}
						};
int modedelay2[]={100,100,100,100,100,100};
unsigned char modeh2[]={0x0f,0x0f,0x0f,0x0f,0x0f,0x0f};
//----------------------------------------------------------------
unsigned char modecode3[][2]={
							{0x00,0x00},
							{0x00,0xff},
							{0xff,0x00},
							{0xff,0xff},
							{0x0f,0xff},
							{0xff,0xff}
						};
int modedelay3[]={100,100,100,100,100,100};
unsigned char modeh3[]={0xfa,0xfa,0xfa,0xfa,0xfa,0xfa};
//---------------------------------------------------------------







void Delay(uint z)
{
	uint x;
	for(;z>0;z--)
	for(x=142;x>0;x--);
}
void TimerInterruptInit()
{
	TMOD=0x01;	//定时器模式
	TH0 = 0x0FC;
    TL0 = 0x66;
	ET0=1;		//定时器中断
	TR0=1;		//设置定时器开
	EA=1;		//开总中断

	EX0=1;      //外部中断0开
	IT0=0;      //电平触发
}
void hc595OutputControl()
{
	//输出存储器锁存
	hc595_st=0;
	hc595_st=1;
	//锁抗干扰防止意外修改
	hc595_st=0;					
	hc595_sh=0;
	hc595_ds=0;
}
void hc595BitLatch()
{
 	hc595_sh=0;
	Delay(1);
	hc595_sh=1;
}
void hc595Init()
{
	int i=0;
 	for(i=0;i<16;i++){
		hc595_ds = 1;        
		hc595BitLatch();
 	}
	hc595OutputControl();
}
void hc595SendData(unsigned char hp,unsigned char lp,int i)
{	
	/*
		hc595数据发送函数
		@param(hp) 高位595值
		@param(lp) 低位595值
		@param(i)  数组变量
	*/ 
	for(i=0;i<16;i++){
		if(i<8){				//高位数据送值
			hc595_ds = hp>>7;        
        	hp = hp<<1;
 	    }else{					//低位数据送值
			hc595_ds = lp>>7;        
        	lp = lp<<1;
		}
		hc595BitLatch();
 	}
	hc595OutputControl();
}

void Array_Data_Parsing(unsigned char (*bitdata)[2],int * delay,unsigned char * line,int size)
{
	/*
	 	数组数据解析函数
		@param(bitdata) 位数据
		@param(delay)   延时数据
		@param(line)	行数据
	*/
	int i=0,j=0;
	while(1){

		P1=line[i];
		hc595SendData(*(*(bitdata+i)),*(*(bitdata+i)+1),j);
		Delay(delay[i]);
		i++;
		if(i==size)
		{
			i=0;
		}
		if(button1==0)
		{
			Delay(1);
			if(button1==0)break;
		}
	}
}
 

void main()
{
	TimerInterruptInit();
	while(1)
	{
		hc595Init();
		digital_num=1;
		Array_Data_Parsing(&modecode1,&modedelay1,&modeh1,sizeof(modecode1)/sizeof(modecode1[0]));
		hc595Init();
		Delay(1000);
		digital_num=2;
		Array_Data_Parsing(&modecode2,&modedelay2,&modeh2,sizeof(modecode2)/sizeof(modecode2[0]));
		hc595Init();
		Delay(1000);
		digital_num=3;
		Array_Data_Parsing(&modecode3,&modedelay3,&modeh3,sizeof(modecode3)/sizeof(modecode3[0]));
	 }
}
void time0d(void) interrupt 1
{
	if(pwm==6){
		TH0 = 0x0FC;
    	TL0 = 0x66;
		P2_3=1;
		P0=table[digital_num];
		P2_3=0;
		pwm=0;
	}
	pwm++;	
}