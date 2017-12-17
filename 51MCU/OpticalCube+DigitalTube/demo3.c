/*
	(Optical Cube+Digital Tube+Button)
	@autgor : confidentiality
	@version : V3.0
	@working team : MMD
	@creation time : 2017/12/16
	@creation site : SuZhou
	@programming language : C
*/
int pwm=0,digital_num=0,model=0;

#include <demo3_1.h>


void main()
{
	TimerInterruptInit();
	hc595Init();
	while(1)
	{
		model=Button_num();
	}
}
void time0d(void) interrupt 1
{
	TH0 = 0x0FC;
    TL0 = 0x66;
	if(pwm==6){			//ÊýÂë¹Ü¿ØÖÆ
		P2_3=1;
		P0=table[model];                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
		P2_3=0;
		pwm=0;
	}
	switch(model)
	{
	 	case 1:Sequential_Light(&model1_sl);break;
		case 2:Spelling();break;
		case 3:break;
		case 4:break;
	}
	pwm++;
	pwm_1++;
	spell_pwm++;	
}