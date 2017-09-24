var ws=null;
var my_prompt_box=document.getElementById("my_prompt_box");
var username_page;
judgment_equipment();
websocket();
function judgment_equipment(){
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
	}
}
function login_button() {
	var username=document.getElementById('username').value;
	var password=document.getElementById('password').value;
	var usepassn=username+password;

		if(username!="" && password!=""){
			var sxvup=/[||\u3002]/i;
			if(usepassn.match(sxvup)==null){
				message(username,password);
			}else{
				
			}
		}else{
			//注册
	document.getElementById('signin_page').style.display = 'none';
	document.getElementById('register_page').style.display = 'block';
		}
}

function register_button() {
	var username=document.getElementById('register_username').value;
	var password=document.getElementById('register_password').value;
	alert(username+password);
}

function go_login_button() {
	document.getElementById('signin_page').style.display = 'block';
	document.getElementById('register_page').style.display = 'none';
}
function message(username,password) {
	 if (ws != null) {
	 	var usepass=[username+"|"+password];
        ws.send(usepass);
     } else {
        alert('WebSocket connection not established, please connect.');
     }
}
function websocket() {
	var loca="/HH/WebSocketsignin";	
    var target ="ws://" + window.location.host + loca;
    if (target =="") {
       alert('Please select server side connection implementation.');
       return;
    }
    if ('WebSocket' in window) {
       ws = new WebSocket(target);
    } else if ('MozWebSocket' in window) {
       ws = new MozWebSocket(target);
    } else {
       alert('WebSocket is not supported by this browser.');
       return;
    }
    ws.onopen = function () {
         
    };
    ws.onmessage = function (event) {
       var backmessage=event.data;
       if(backmessage!=""){
    	   my_alert(backmessage);
username_page=document.getElementById('username').value;
    	      
document.getElementById("login_page").style.display="none";
document.getElementById("home_page").style.display="block";
ws=null;

home_websocket();
       }else{
    	  alert("输入错误");
       }
    };
    ws.onclose = function (event) {
        alert("服务器连接有问题");
    };
}
function my_alert(input) {//my alert box
	my_prompt_box.style.display="block";
   	my_prompt_box.innerHTML=input;
   	setTimeout(function(){
   		my_prompt_box.style.display="none";
	},2000);				
}