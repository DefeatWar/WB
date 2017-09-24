pd();//调用对设备是电脑还是手机的函数	
function pd(){//这是一个对设备是电脑还是手机的函数	
	var userAgentInfo=navigator.userAgent;
	var Agents=["Android","iPhone"];
	var flag=true;
	for(var v=0;v<Agents.length;v++){
		if(userAgentInfo.indexOf(Agents[v])>0){
			flag=false;
			break;
		}
	}
	if(flag==true){
		//电脑
		document.getElementById("bb").innerHTML="电脑网页版";
		document.getElementById("yxq").style.width="80%";
	}
	if(flag==false){
		//手机
		document.getElementById("body").width="100%";
		document.getElementById("yxq").style.width="90%";
		document.getElementById("yxq").style.marginLeft="5%";
		document.getElementById("bb").innerHTML="手机网页版(建议手机全屏)";
	}
}

//初始化游戏参数
var fk_number=50;//方块个数
var first_array=[1,0,0,0];
var second_array=[0,1,0,0];
var third_array=[0,0,1,0];
var fourth_array=[0,0,0,1];
var hh1=document.getElementById("hh1");
var hh2=document.getElementById("hh2");
var hh3=document.getElementById("hh3");
var hh4=document.getElementById("hh4");
var hh5=document.getElementById("hh5");
var hh6=document.getElementById("hh6");
//初始化游戏音乐
var music=document.getElementById("music");
//初始化游戏计时器参数
var timeud=0;
var time_math;
var times=0;
var timeh=0;
function time(){        //游戏计时器函数
	time_math=window.setInterval(function(){
		++timeh;
		times=timeh/100;
		document.getElementById("timeheader").innerHTML=times+"s";
	},10);	
}

function start(){           //游戏方块部署函数
 game_array=[[]];//游戏二维数组
//用二维数组来决定黑色方块位置
	for(var i=0;i<fk_number;++i){
		var random_number=(Math.round(Math.random()*3));//生成随机数
		//往二维数组加数
		if(random_number==0){
			game_array[i]=first_array;
		}
		if(random_number==1){
			game_array[i]=second_array;
		}
		if(random_number==2){
			game_array[i]=third_array;
		}
		if(random_number==3){
			game_array[i]=fourth_array;
		}
	}	
	//设置开始区为蓝色
	for(var i=0;i<4;++i){
		var byid="h5d"+(i+1);
		document.getElementById(byid).style.backgroundColor="blue";
	}
	//游戏开始前的5个方块显示
	for(var x=0;x<5;++x){
	for(var i=0;i<game_array[0].length;++i){
			if(game_array[x][i]==1){
				var h=0;
				if(x==0){h=4;}
				if(x==1){h=3;}
				if(x==2){h=2;}
				if(x==3){h=1;}
				if(x==4){h=6;}
				var bw="h"+(h)+"d"+(i+1);
				document.getElementById(bw).style.backgroundColor="black";//当数字为1时显示黑色
			}
	}
	}	
}
function gamebug(id){ //游戏bug
	var loset=0;
	var hs=id.substring(1,2);
	var xb=id.charAt(id.length-1);
	
if(hs==4){
	for(var i=0;i<4;++i){
		var bjc=document.getElementById("h5d"+(i+1)).style.backgroundColor;
		if(bjc=="black"){
				loset=1;
		}
	}
}
if(hs==3){
	for(var i=0;i<4;++i){
		var bjc=document.getElementById("h4d"+(i+1)).style.backgroundColor;
		if(bjc=="black"){
				loset=1;
		}
	}
}
if(hs==2){
	for(var i=0;i<4;++i){
		var bjc=document.getElementById("h3d"+(i+1)).style.backgroundColor;
		if(bjc=="black"){
				loset=1;
		}
	}
}
if(hs==1){
	for(var i=0;i<4;++i){
		var bjc=document.getElementById("h2d"+(i+1)).style.backgroundColor;
		if(bjc=="black"){
				loset=1;
		}
	}
}
if(hs==5){
	for(var i=0;i<4;++i){
		var bjc=document.getElementById("h6d"+(i+1)).style.backgroundColor;
		if(bjc=="black"){
				loset=1;
		}
	}
}
if(hs==6){
	for(var i=0;i<4;++i){
		var bjc=document.getElementById("h1d"+(i+1)).style.backgroundColor;
		if(bjc=="black"){
				loset=1;
		}
	}
}
	if(loset==1){
		return loset;
	}
}

















function startplay(){//page1点击开始调用函数，游戏总函数
	//music.play();//播放音乐
	
	timeud=0;
	 times=0;
	timeh=0;
	document.getElementById("timeheader").innerHTML=times+"s";
	
	for(var v=0;v<6;++v){
	for(var i=0;i<4;++i){
	var byid="h"+(v+1)+"d"+(i+1);
		document.getElementById(byid).style.backgroundColor="white";
	}
}

hh1.style.top="-10%";
hh2.style.top="12%";
hh3.style.top="34%";
hh4.style.top="56%";
hh5.style.top="78%";
hh6.style.top="-32%";
	
start();//调用游戏方块部署函数

}


var hh4cs=0;
function z(t){
	
}



//开始游戏

function a(t){
	var id=t.id;
	var xb=id.charAt(id.length-1);
	
	var hh1h=document.getElementById("hh1").offsetTop;
	var hh2h=document.getElementById("hh2").offsetTop;
	var hh3h=document.getElementById("hh3").offsetTop;
	var hh4h=document.getElementById("hh4").offsetTop;
	var hh5h=document.getElementById("hh5").offsetTop;
	var hh6h=document.getElementById("hh6").offsetTop;
	var bodyh=document.getElementById("page2").offsetHeight;
	
	var hh1hb=Math.round((hh1h/bodyh)*100);
	var hh2hb=Math.round((hh2h/bodyh)*100);
	var hh3hb=Math.round((hh3h/bodyh)*100);
	var hh4hb=Math.round((hh4h/bodyh)*100);
	var hh5hb=Math.round((hh5h/bodyh)*100);
	var hh6hb=Math.round((hh6h/bodyh)*100);
	
   

	if(game_array.length==1){
		clearTimeout(time_math);							
//alert("成功了");
document.getElementById("page2").style.display="none";	
document.getElementById("page3").style.display="block";		
document.getElementById("page3").style.backgroundColor="rgba(0,230,0,1)";		
document.getElementById("cgsb").innerHTML="成功";
document.getElementById("time").innerHTML=times+"s";
}	
var whitep=document.getElementById(id).style.backgroundColor;
//&&whitep!="white"
	if(game_array[0][xb-1]==1){
		 var loset=gamebug(id);
	if(loset==1){
		return;
	}
		
++timeud;
if(timeud==1){
	time();
}		

//***********************************************************6行
		if(hh6hb==-32){
			var bh=0;
			for(var x=0;x<4;++x){
				if(game_array[0][x]==1){
					bh=x;
				}
			}
			game_array.splice(0,1);
			for(var i=0;i<4;++i){
				var h6id="h6d"+(i+1);
				document.getElementById(h6id).style.backgroundColor="white";
			}
			for(var j=0;j<4;++j){
						//alert(game_array.length);
						if(game_array.length<5){
							for(var i=0;i<4;++i){
								var h6id="h6d"+(i+1);
								document.getElementById(h6id).style.backgroundColor="rgba(0,230,0,1)";
							}	
							break;
						}else	if(game_array[3][j]==1){
							var h6id="h6d"+(j+1);
							document.getElementById(h6id).style.backgroundColor="black";
						}
					}	
					
					var colornumber=0;
					var hh6top=-32;var hh1top=-10;var hh2top=12;var hh3top=34;var hh4top=56;
					var hh5top=78;
					var yd=window.setInterval(function(){
						hh6top++;hh1top++;hh2top++;hh3top++;hh4top++;hh5top++;colornumber++;
						if(hh6top>=-10){
							clearTimeout(yd);	
						}
					hh6.style.top=hh6top+"%";//1
					hh5.style.top=hh5top+"%";//2
					hh1.style.top=hh1top+"%";//3
					hh2.style.top=hh2top+"%";//4
					hh3.style.top=hh3top+"%";//5
					hh4.style.top=hh4top+"%";//6
colornumber=colornumber+7;
					document.getElementById("h4d"+(bh+1)).style.backgroundColor=
				"rgb("+colornumber+","+colornumber+","+colornumber+")";
					},1);
					   
		}
//***********************************************************5行
		if(hh6hb==-10){

			var bh=0;
			for(var x=0;x<4;++x){
				if(game_array[0][x]==1){
					bh=x;
				}
			}
			game_array.splice(0,1);
			for(var i=0;i<4;++i){
				var h5id="h5d"+(i+1);
				document.getElementById(h5id).style.backgroundColor="white";
			}
			for(var j=0;j<4;++j){
						//alert(game_array.length);
						if(game_array.length<4){
							for(var i=0;i<4;++i){
								var h5id="h5d"+(i+1);
								document.getElementById(h5id).style.backgroundColor="rgba(0,230,0,1)";
							}	
							break;
						}else	if(game_array[3][j]==1){
							var h5id="h5d"+(j+1);
							document.getElementById(h5id).style.backgroundColor="black";
						}
					}	
					
						var colornumber=0;
					
					var hh6top=-10;var hh1top=12;var hh2top=34;var hh3top=56;var hh4top=78;
					var hh5top=-32;
					
					var yd=window.setInterval(function(){
						hh6top++;hh1top++;hh2top++;hh3top++;hh4top++;hh5top++;colornumber++;
						if(hh6top>=12){
							clearTimeout(yd);	
						}
						hh6.style.top=hh6top+"%";//1
					hh5.style.top=hh5top+"%";//2
					hh1.style.top=hh1top+"%";//3
					hh2.style.top=hh2top+"%";//4
					hh3.style.top=hh3top+"%";//5
					hh4.style.top=hh4top+"%";//6
					
					colornumber=colornumber+7;
					document.getElementById("h3d"+(bh+1)).style.backgroundColor=
				"rgb("+colornumber+","+colornumber+","+colornumber+")";
					},1);	
		}	
//***********************************************************4行
		if(hh6hb==12){
			var bh=0;
				for(var x=0;x<4;++x){
					if(game_array[0][x]==1){
						bh=x;
				}
			}
			game_array.splice(0,1);
			for(var i=0;i<4;++i){
				var h4id="h4d"+(i+1);
				document.getElementById(h4id).style.backgroundColor="white";
			}
			for(var j=0;j<4;++j){
						//alert(game_array.length);
						if(game_array.length<3){
							for(var i=0;i<4;++i){
								var h4id="h4d"+(i+1);
								document.getElementById(h4id).style.backgroundColor="rgba(0,230,0,1)";
							}
							break;
						}else	if(game_array[3][j]==1){
							var h4id="h4d"+(j+1);
							document.getElementById(h4id).style.backgroundColor="black";
						}
					}	
					
					var colornumber=0;
					var hh6top=12;var hh1top=34;var hh2top=56;var hh3top=78;var hh4top=-32;
					var hh5top=-10;
					
					var yd=window.setInterval(function(){
						hh6top++;hh1top++;hh2top++;hh3top++;hh4top++;hh5top++;colornumber++;
						if(hh6top>=34){
							clearTimeout(yd);	
						}
						hh6.style.top=hh6top+"%";//1
					hh5.style.top=hh5top+"%";//2
					hh1.style.top=hh1top+"%";//3
					hh2.style.top=hh2top+"%";//4
					hh3.style.top=hh3top+"%";//5
					hh4.style.top=hh4top+"%";//6
					colornumber=colornumber+7;
					document.getElementById("h2d"+(bh+1)).style.backgroundColor=
				"rgb("+colornumber+","+colornumber+","+colornumber+")";
					},1);	
		}	
//***********************************************************3行
		if(hh6hb==34){
			var bh=0;
				for(var x=0;x<4;++x){
					if(game_array[0][x]==1){
						bh=x;
				}
			}
			game_array.splice(0,1);
			for(var i=0;i<4;++i){
				var h3id="h3d"+(i+1);
				document.getElementById(h3id).style.backgroundColor="white";
			}
			for(var j=0;j<4;++j){
						//alert(game_array.length);
						if(game_array.length<2){
							for(var i=0;i<4;++i){
								var h3id="h3d"+(i+1);
								document.getElementById(h3id).style.backgroundColor="rgba(0,230,0,1)";
							}
							break;
						}else	if(game_array[3][j]==1){
							var h3id="h3d"+(j+1);
							document.getElementById(h3id).style.backgroundColor="black";
						}
					}	
					var colornumber=0;
					var hh6top=34;var hh1top=56;var hh2top=78;var hh3top=-32;var hh4top=-10;
					var hh5top=12;
					var yd=window.setInterval(function(){
						hh6top++;hh1top++;hh2top++;hh3top++;hh4top++;hh5top++;colornumber++;
						if(hh6top>=56){
							clearTimeout(yd);	
						}
						hh6.style.top=hh6top+"%";//1
					hh5.style.top=hh5top+"%";//2
					hh1.style.top=hh1top+"%";//3
					hh2.style.top=hh2top+"%";//4
					hh3.style.top=hh3top+"%";//5
					hh4.style.top=hh4top+"%";//6
					colornumber=colornumber+7;
					document.getElementById("h1d"+(bh+1)).style.backgroundColor=
				"rgb("+colornumber+","+colornumber+","+colornumber+")";
					},1);		
		}
//***********************************************************2行		
	if(hh6hb==56){
		var bh=0;
				for(var x=0;x<4;++x){
					if(game_array[0][x]==1){
						bh=x;
				}
			}
			game_array.splice(0,1);
			for(var i=0;i<4;++i){
				var h2id="h2d"+(i+1);
				document.getElementById(h2id).style.backgroundColor="white";
			}
			for(var j=0;j<4;++j){
						//ialert(game_array.length);
						if(game_array.length<5){
							for(var i=0;i<4;++i){
								var h2id="h2d"+(i+1);
								document.getElementById(h2id).style.backgroundColor="rgba(0,230,0,1)";
							}	
							break;
						}else	if(game_array[3][j]==1){
							var h2id="h2d"+(j+1);
							document.getElementById(h2id).style.backgroundColor="black";
						}
					}	
					var colornumber=0;
					var hh6top=56;var hh1top=78;var hh2top=-32;var hh3top=-10;var hh4top=12;
					var hh5top=34;
					var yd=window.setInterval(function(){
						hh6top++;hh1top++;hh2top++;hh3top++;hh4top++;hh5top++;colornumber++;
						if(hh6top>=78){
							clearTimeout(yd);	
						}
						hh6.style.top=hh6top+"%";//1
					hh5.style.top=hh5top+"%";//2
					hh1.style.top=hh1top+"%";//3
					hh2.style.top=hh2top+"%";//4
					hh3.style.top=hh3top+"%";//5
					hh4.style.top=hh4top+"%";//6
					colornumber=colornumber+7;
					document.getElementById("h6d"+(bh+1)).style.backgroundColor=
				"rgb("+colornumber+","+colornumber+","+colornumber+")";
					},1);		
		}
//***********************************************************1行		
	if(hh6hb==78){
				var bh=0;
				for(var x=0;x<4;++x){
					if(game_array[0][x]==1){
						bh=x;
				}
			}
			game_array.splice(0,1);
			for(var i=0;i<4;++i){
				var h1id="h1d"+(i+1);
				document.getElementById(h1id).style.backgroundColor="white";
			}
			for(var j=0;j<4;++j){
						//alert(game_array.length);
						if(game_array.length<4){
							for(var i=0;i<4;++i){
								var h1id="h1d"+(i+1);
								document.getElementById(h1id).style.backgroundColor="rgba(0,230,0,1)";
							}
							break;
						}else	if(game_array[3][j]==1){
							var h1id="h1d"+(j+1);
							document.getElementById(h1id).style.backgroundColor="black";
						}
					}	
					var colornumber=0;
				var hh6top=78;var hh1top=-32;var hh2top=-10;var hh3top=12;var hh4top=34;
					var hh5top=56;	
					var yd=window.setInterval(function(){
						hh6top++;hh1top++;hh2top++;hh3top++;hh4top++;hh5top++;colornumber++;
						if(hh6top>=100){
			document.getElementById("hh6").style.top="-32%";
			hh6top=-32;
							clearTimeout(yd);	
						}
					hh6.style.top=hh6top+"%";//1
					hh5.style.top=hh5top+"%";//2
					hh1.style.top=hh1top+"%";//3
					hh2.style.top=hh2top+"%";//4
					hh3.style.top=hh3top+"%";//5
					hh4.style.top=hh4top+"%";//6
					
					colornumber=colornumber+7;
					document.getElementById("h5d"+(bh+1)).style.backgroundColor=
				"rgb("+colornumber+","+colornumber+","+colornumber+")";
					},1);	
		}
	}else{
		
				//按的错误
				clearTimeout(time_math);

	setTimeout(function(){
						document.getElementById(id).style.backgroundColor="red";
	},100);
	setTimeout(function(){
							document.getElementById(id).style.backgroundColor="white";	
	},200);
	setTimeout(function(){
						document.getElementById(id).style.backgroundColor="red";
	},300);
	setTimeout(function(){
							document.getElementById(id).style.backgroundColor="white";	
	},400);
	setTimeout(function(){
						document.getElementById(id).style.backgroundColor="red";
	},500);
	setTimeout(function(){
							document.getElementById(id).style.backgroundColor="white";	
	},600);
		setTimeout(function(){
							document.getElementById("page2").style.display="none";	
							document.getElementById("page3").style.display="block";		
							document.getElementById("page3").style.backgroundColor="red";	
							document.getElementById("cgsb").innerHTML="失败";
							document.getElementById("time").innerHTML=times+"s";
		},600);			
	}
}

function cl(t){
	var id=t.id;
	if(id=="cll"){
		document.getElementById("page2").style.display="block";	
		startplay();
	}
	if(id=="fh"){
		document.getElementById("page3").style.display="none";
		document.getElementById("page1").style.display="block";	
		document.getElementById("page1").style.top="0%";	
		document.getElementById("page2").style.display="block";	
	}
}