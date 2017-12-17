#include <reg52.h>
#include <intrins.h>
#define uchar unsigned char
#define uint unsigned int
sbit hc595_sh=P2^5;
sbit hc595_ds=P2^7;
sbit hc595_st=P2^6;

sbit but1=P3^5;
sbit but2=P3^4;
sbit but3=P3^3;
sbit but4=P3^2;

sbit buzzer=P3^6;

sbit P2_3=P2^3;


unsigned char code table[]={0xc0,0xf9,0xa4,0xb0,
							0x99,0x92,0x82,0xf8,
							0x80,0x90,0xff};	//数码管数组
/**************************************************************
					定时器初始化函数
**************************************************************/
void TimerInterruptInit()
{
	TMOD=0x01;	//定时器模式
	TH0 = 0x0FC;
    TL0 = 0x66;
	ET0=1;		//定时器中断
	TR0=1;		//设置定时器开
	EA=1;		//开总中断
}
/**************************************************************
					延时函数
**************************************************************/
void Delay(uint z)
{
	uint x;
	for(;z>0;z--)
	for(x=142;x>0;x--);
}
/**************************************************************
					hc595位锁存函数
**************************************************************/
void hc595BitLatch()
{
 	hc595_sh=0;
	Delay(1);
	hc595_sh=1;
}
/**************************************************************
					输出存储器锁存函数
**************************************************************/
void hc595OutputControl()
{
	hc595_st=0;
	hc595_st=1;
	//锁抗干扰防止意外修改
	hc595_st=0;					
	hc595_sh=0;
	hc595_ds=0;
}
/**************************************************************
					hc595初始化函数
**************************************************************/
void hc595Init()
{
	int i=0;
 	for(i=0;i<16;i++){
		hc595_ds = 1;        
		hc595BitLatch();
 	}
	hc595OutputControl();
}
/**************************************************************
					按钮解析函数
**************************************************************/
int Button_num()
{
	if(but1==0){if(but1==0)return 1;}
	else if(but2==0){if(but2==0)return 2;}
	else if(but3==0){if(but3==0)return 3;}
	else if(but4==0){if(but4==0)return 4;}
	else{}
}
/**************************************************************
					hc595数据发送函数
**************************************************************/
void hc595SendData(unsigned char hp,unsigned char lp)
{
	int i=0;	
	/*
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
/**************************************************************
				一盏一盏灯接着顺序亮函数
**************************************************************/
int model1_sl[]={0x08,0x04,0x02,0x1};
int aw=0xfe;
int l_num=0;
unsigned int tsi=0;
int pwm_1=0;
int model1_spend=1;
void Sequential_Light(unsigned char * line)
{
	
	if(pwm_1>model1_spend*2)
	{	
		pwm_1=0;
		tsi++;
		if(tsi>=1 && tsi<=8)
		{
		 	aw=_cror_(aw,1),hc595SendData(aw,0xff),l_num=0;
		}else if(tsi>=9 && tsi<=16)
		{
		 	aw=_cror_(aw,1),hc595SendData(0xff,aw),l_num=0;
		}else if(tsi>=17 && tsi<=24)
		{
			if(tsi==17)aw=0x7f;
		 	aw=_crol_(aw,1),hc595SendData(0xff,aw),l_num=1;
		}else if(tsi>=25 && tsi<=32)
		{
		 	aw=_crol_(aw,1),hc595SendData(aw,0xff),l_num=1;
		}else if(tsi>=33 && tsi<=40)
		{
			if(tsi==33)aw=0xfe;
		 	aw=_cror_(aw,1),hc595SendData(aw,0xff),l_num=2;
		}else if(tsi>=41 && tsi<=48)
		{
		 	aw=_cror_(aw,1),hc595SendData(0xff,aw),l_num=2;
		}else if(tsi>=49 && tsi<=56)
		{
			if(tsi==49)aw=0x7f;
		 	aw=_crol_(aw,1),hc595SendData(0xff,aw),l_num=3;
		}else if(tsi>=57 && tsi<=64)
		{
		 	aw=_crol_(aw,1),hc595SendData(aw,0xff),l_num=3;
		}else{
			aw=0xfe;
			tsi=0;
			model1_spend++;
		}						
		P1=model1_sl[l_num];		
	}
}
/**************************************************************
				拼字CRVS函数
**************************************************************/
int spell_pwm=0;
int spell_i=0;
code int spell_data[][2]={
	{0xef,0xff},{0xcf,0xff},{0x8f,0xff},{0xf,0xff},{0xe,0xff},
	{0xe,0x7f},{0xe,0x7e},{0xe,0x7c},{0xe,0x78},{0xe,0x70},
	{0xe,0x70},{0xe,0x70},{0xe,0x70},{0xe,0x70},{0xe,0x70},
	{0xff,0xfe},{0xff,0x7e},{0xfe,0x7e},{0xbe,0x7e},{0x9e,0x7e},{0x96,0x7e},
	{0x96,0x7e},{0x96,0x7e},{0x96,0x7e},{0x96,0x7e},{0x96,0x7e},{0x96,0x7e},
	
	{0xf7,0xff},{0xf7,0xdf},{0xf7,0xdd},{0xf7,0x5d},{0xf6,0x5d},
	{0xf6,0x5d},{0xf6,0x5d},{0xf6,0x5d},{0xf6,0x5d},{0xf6,0x5d},
	
	{0xef,0xff},{0xcf,0xff},{0x8f,0xff},{0xf,0xff},{0xe,0xff},
	{0xc,0xff},{0x8,0xff},{0x0,0xff},{0x0,0xef},{0x0,0xcf},{0x0,0x8f},{0x0,0xf},
	{0x0,0xf},{0x0,0xf},{0x0,0xf},{0x0,0xf},{0x0,0xf},

	{0XFF,0XFF}, {0XFF,0XFF},{0XFF,0XFF},{0XFF,0XFF}

			};
void Spelling()
{
	if(spell_pwm>30)
	{
		spell_pwm=0;
 		hc595SendData(spell_data[spell_i][0],spell_data[spell_i][1]);
		P1=0x0c;
		spell_i++;
		if(spell_i>sizeof(spell_data)/sizeof(spell_data[0]))
		{
		 	spell_i=0;
		}
	}
}