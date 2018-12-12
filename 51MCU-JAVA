#include<reg52.h>
 
#define uint unsigned int
#define uchar unsigned char
uchar LEDOFF = 0xff;
uchar LEDON = 0x00;
 
uchar MYSBUF;

bit MYT1 = 0;
bit MYR1 = 0;

sbit one=P1^0;
 
void my_interrupt() interrupt 4 {
	if(RI == 1) {
		RI = 0;
		MYR1 = 1;
		MYSBUF = SBUF;
	} 
	if (TI == 1) {
		TI = 0;
		MYT1 = 1;
	}
}
 
uchar computerdata[32]="                                ";

sbit rs=P0^7;    //1602的数据/指令选择控制线 
sbit rw=P0^6;        //1602的读写控制线 
sbit en=P0^5;        //1602的使能控制线 

void delay(uint n)       //延时函数                       
{ 
    uint x,y;  
    for(x=n;x>0;x--) 
        for(y=110;y>0;y--); 
} 
void lcd_wcom(uchar com)  //1602写命令函数                 
{ 
    rs=0;            //选择指令寄存器 
    rw=0;            //选择写 
    P2=com;            //把命令字送入P2 
    delay(5);            //延时一小会儿，让1602准备接收数据 
    en=1;           //使能线电平变化，命令送入1602的8位数据口 
    en=0; 
} 
void lcd_wdat(uchar dat)        //1602写数据函数       
{ 
    rs=1;        //选择数据寄存器 
    rw=0;        //选择写 
    P2=dat;        //把要显示的数据送入P2 
    delay(5);        //延时一小会儿，让1602准备接收数据 
    en=1;        //使能线电平变化，数据送入1602的8位数据口 
    en=0; 
} 
void lcd_init()              //1602初始化函数       
{ 
    lcd_wcom(0x38);       //8位数据，双列，5*7字形       
    lcd_wcom(0x0c);      //开启显示屏，关光标，光标不闪烁 
    lcd_wcom(0x06);    //显示地址递增，即写一个数据后，显示位置右移一位 
    lcd_wcom(0x01);    //清屏 
}
 int s=0;
	int time =0;
void main()            //主函数 
{  
	uchar n,m=0; 
    lcd_init();                 //液晶初始化 

	SCON = 0x50;   //串口方式1, 8-n-1, 允许接收.
	TMOD = 0x20;   //T1方式2
	TH1 = 0xFD;    //9600bps@11.0592MHz
	TL1 = 0xFD;
	TR1 = 1;
	ES  = 1;       //开中断.
	EA  = 1;

	

	while(1){
		if (MYR1 == 1)  {
			SBUF = MYSBUF;
			//P1 = MYSBUF;
			if(MYSBUF=='$'){
			   for(n=0;n<32;n++)   //将table1[]中的数据依次写入1602显示 
		    	{     
		         computerdata[n]=' ';
		    	}
			}
			if(MYSBUF==' '){
			 	//停止发送
				time=0;
			}else{
				//记录数据
				computerdata[time]=MYSBUF;
				time++;
			}
			
			MYR1 = 0;
		}
		if(time==0){
			if(computerdata[1]=='%' && computerdata[2]=='&'){
			   if(computerdata[3]=='0'){
			   		one=0;
			   }else{
					one=1;
			   }
			}else{			
			    lcd_wcom(0x80);   //显示地址设为80H（即00H，）上排第一位       
			    for(m=1;m<17;m++)     //将table[]中的数据依次写入1602显示 
			    { 
			            lcd_wdat(computerdata[m]);           
			    } 
			    lcd_wcom(0x80+0x44); //重新设定显示地址为0xc4,即下排第5位 
			    for(n=16;n<32;n++)   //将table1[]中的数据依次写入1602显示 
			    {     
			            lcd_wdat(computerdata[n]); 
			    } 
			}
		}
	}
} 
 

