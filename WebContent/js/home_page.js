var home_ws=null;

var home=$("#home");
var my=$("#my");

function home_websocket() {
	var loca="/HH/WebSocketHomepage";	
    var target ="ws://" + window.location.host + loca;
    if (target =="") {
       alert('Please select server side connection implementation.');
       return;
    }
    if ('WebSocket' in window) {
    	home_ws = new WebSocket(target);
    } else if ('MozWebSocket' in window) {
    	home_ws = new MozWebSocket(target);
    } else {
       alert('WebSocket is not supported by this browser.');
       return;
    }
    home_ws.onopen = function () {
    	var back={"key":"sign_new","username_page":username_page};
    	home_message(JSON.stringify(back));
    };
    home_ws.onmessage = function (event) {
    	
var backmessage=event.data;  
alert(".backmessage"+backmessage);
var backjson=eval("("+backmessage+")");


if(backjson[0][0]=="chat_back"){
	for(var i=0;i<backjson.length;i++){
		new_chat(backjson[i][1],backjson[i][0],backjson[i][2]);
	}
	return;
}

if(backjson.length==10){
	alert(backjson[0][0]);
	backmessage_show(backjson);
}
if(backjson.length==9){
	alert("newpost:"+backjson[0]);
	new_message_show(backjson);
}
if(backjson.length==15){
	alert("newpost:"+backjson[0]);
	w_post_p(backjson);
}
if(backjson.length==2){
	if(backjson[0]=="good"){
		$("#w_post_good").innerHTML=backjson[1];
	}
	if(backjson[0]=="bad"){
		$("#w_post_bad").innerHTML=backjson[1];
	}
}

    };
	home_ws.onclose = function (event) {
    	alert('0Info: WebSocket connection closed, Code: ' + event.code + (event.reason == "" ? "" : ", Reason: " + event.reason));
    };
}


//new_message_show(["5","张三0000",null,"房产","卖房子了\"+abc+\"","2017-01-24 12:59:18.0","1","0","1"]);



function home_message(message) {
	/****/
	if (home_ws != null) {
			home_ws.send(message);
    } else {
       alert('WebSocket connection not established, please connect101.');
    }
}
window.onscroll=function() {
	var scrolltop=document.body.scrollTop;
	var scrollheight=document.documentElement.scrollHeight;
	var offsetheight=document.documentElement.offsetHeight;
	if (scrolltop>=scrollheight-offsetheight) {
		//网页到底
		
	}
}

function backmessage_show(message) {
	for (var i =0; i<message.length; i++) {
		if (message[i][0]!=null) {
	var text_id=message[i][0];		
	var sender_name=message[i][1];
	var user_head=message[i][2];
	var add_time=message[i][5];
	var theme_content=message[i][4];
	var good=message[i][6];
	var bad=message[i][7];
	var interest=message[i][3];
	var look=message[i][8];
page_join_div(text_id,sender_name,user_head,add_time,theme_content,good,bad,interest,look,"backmessage");
		}
	}
}
function new_message_show(message) {
	if (message[0]!=null) {
	var text_id=message[0];		
	var sender_name=message[1];
	var user_head=message[2];
	var add_time=message[5];
	var theme_content=message[4];
	var good=message[6];
	var bad=message[7];
	var interest=message[3];
	var look=message[8];
page_join_div(text_id,sender_name,user_head,add_time,theme_content,good,bad,interest,look,"newmessage");
	}
}
function page_join_div(text_id,sender_name,user_head,add_time,theme_content,good,bad,interest,look,messagetype) {
	var postdiv=document.createElement("div");
	var home=document.getElementById("home");
postdiv.name=text_id;
postdiv.setAttribute("onclick","onclick_name(this.name)");
if(messagetype=="backmessage"){
	home.appendChild(postdiv);
}
if(messagetype=="newmessage"){
	home.insertBefore(postdiv,home.childNodes[0]);
}
	//document.getElementById("home").appendChild(postdiv);

	var post_header=document.createElement("header");
	postdiv.appendChild(post_header);
	var post_section=document.createElement("section");
	postdiv.appendChild(post_section);
	var post_footer=document.createElement("footer");
	postdiv.appendChild(post_footer);
	var head_portrait=document.createElement("div");
	head_portrait.id="head_portrait";
	if (user_head!=null) {
		head_portrait.style.backgroundImage="url(header_picture/"+user_head+")";		
	}
	post_header.appendChild(head_portrait);
	var post_name=document.createElement("div");
	post_name.id="post_name";
	post_name.innerHTML+=sender_name;
	post_header.appendChild(post_name);
	var post_time=document.createElement("div");
	post_time.id="post_time";
	post_time.innerHTML+=add_time;
	post_header.appendChild(post_time);
	var post_content=document.createElement("div");
	post_content.innerHTML+=theme_content;
	post_section.appendChild(post_content);
	var post_good=document.createElement("div");
var img_good=document.createElement("img");
img_good.src="img/good.png";
img_good.width="15";
img_good.hight="15";
img_good.style.marginTop="2.5px";
post_good.appendChild(img_good);
	post_good.innerHTML+=good;
	post_footer.appendChild(post_good);
	var post_bad=document.createElement("div");
var img_bad=document.createElement("img");
img_bad.src="img/bad.png";
img_bad.width="15";
img_bad.hight="15";
img_bad.style.marginTop="2px";
post_bad.appendChild(img_bad);	
	post_bad.innerHTML+=bad;
	post_footer.appendChild(post_bad);
var post_look=document.createElement("div");
post_look.innerHTML+="浏览  "+look;
post_footer.appendChild(post_look);	
	var post_bad=document.createElement("div");
	post_bad.innerHTML+=interest;
	post_footer.appendChild(post_bad);
}
var look_le=new Array();
function onclick_name(name){
	var conback=contains(look_le,name);
	if(conback==false){
		alert("post_c"+name)
		look_le[look_le.length]=name;
		var back={"key":"post_content","post_text_ie":name};
		home_message(JSON.stringify(back));
		$("#home").style.display="none";
		$("#post_content_page").style.display="block";
		$("#chat_page").name=name;
	}else{
		alert("post_c"+name);
	}
}
function w_post_p(backmessage) {
		var text_id=backmessage[0];	
$("#post_content_page").name=text_id;
		var sender_name=backmessage[1];
$("#w_post_usename").innerHTML=sender_name;
		var sender_head=backmessage[2];
		alert(sender_head)
$("#post_content_header").style.backgroundImage="url(header_picture/"+sender_head+")";		
		var interest=backmessage[3];
$("#w_post_interest").innerHTML=interest;
		var theme_title=backmessage[4];
$("#w_post_top").innerHTML=theme_title;	
		var add_time=backmessage[5];
$("#w_post_time").innerHTML=add_time;
		var good=backmessage[6];
$("#w_post_good").innerHTML=good;				
		var bad=backmessage[7];
$("#w_post_bad").innerHTML=bad;	
		var look=backmessage[8];
		
		var theme_content=backmessage[9];
$("#w_post_content").innerHTML=theme_content;			

		var photo_src1=backmessage[10];
if(photo_src1!==null){
	$("#photo_src1").src="user_picture/"+photo_src1;
}
		var photo_src2=backmessage[11];
if(photo_src2!==null){
	$("#photo_src2").src="user_picture/"+photo_src2;	
}
		var photo_src3=backmessage[12];
if(photo_src3!==null){
	$("#photo_src3").src="user_picture/"+photo_src3;
}
		var photo_src4=backmessage[13];
if(photo_src4!==null){	
	$("#photo_src4").src="user_picture/"+photo_src4;
}
		var photo_src5=backmessage[14];
if(photo_src5!==null){		
	$("#photo_src5").src="user_picture/"+photo_src5;	
}
}

var good_le=new Array();
var bad_le=new Array();
function w_post_goodbad(id){
	if(id=="w_post_good"){
		//alert(good_le)
		var text_id=document.getElementById("post_content_page").name;
		var conback=contains(good_le,text_id);
		if(conback==false){
			good_le[good_le.length]=text_id;
			var back={"key":"new_good","text_id":text_id};
	    	home_message(JSON.stringify(back));
		}else{
			//alert(0)
		}
	}
	if(id=="w_post_bad"){
		var text_id=document.getElementById("post_content_page").name;
		var conback=contains(bad_le,text_id);
		//alert(bad_le)
		if(conback==false){
			bad_le[bad_le.length]=text_id;
			var back={"key":"new_bad","text_id":text_id};
	    	home_message(JSON.stringify(back));
		}else{
			//alert(1)
		}
	}
}




function home_click() {
	if (document.getElementById("home").style.display=="block") {
		//回到顶部执行
		var scrolltop=document.body.scrollTop;
		if(scrolltop!=0){
			var back_top=window.setInterval(function(){
				if(scrolltop<0){
					clearTimeout(back_top);	
				}
				document.body.scrollTop=scrolltop--*6;
			},1);	
		}
	}

	if ($("#post_content_page").style.display=="block") {
		$("#post_content_page").style.display="none";
		$("#w_post_usename").innerHTML="";
		$("#w_post_interest").innerHTML="";
		$("#w_post_top").innerHTML="";	
		$("#w_post_time").innerHTML="";
		$("#w_post_content").innerHTML="";	
		$("#photo_src1").src=null;
		$("#photo_src2").src=null;
		$("#photo_src3").src=null;
		$("#photo_src4").src=null;
		$("#photo_src5").src=null;
		$("#home").style.display="block";
	}
	if ($("#chat_page").style.display=="block") {
		alert(0)
		$("#chat_page").style.display="none";
		$("#chat_section").style.display="none";
		$("#post_content_page").style.display="block";
		$("#post_content_nav").style.display="block";
	}
}


var photoarray=new Array;

var reader = null;  //读取操作对象
var step = 8192;  //每次读取文件大小 ,字节数
var enableRead = true;//标识是否可以读取文件
var cuLoaded = 0; //当前已经读取总数
var file = null; //当前读取的文件对象
var total = 0;        //记录当前文件总字节数
var startTime = null; //标识开始上传时间

function post_photo_click(files){
	alert(files)
	if(photoarray.length<6){
		for(var i=0;i<files.length;i++){
			var oFile = files[i];
			var showimg=document.createElement("img");
			showimg.src=window.webkitURL.createObjectURL(oFile);
			showimg.id="new_photo_post";
			document.getElementById('photo_show').appendChild(showimg);
			photoarray[photoarray.length]=oFile;
		}
	}
}
function sendphoto(){
	enableRead = true;//可以读取文件
		file = photoarray[0];
		alert("00..00"+typeof file)
		 total = file.size;
		 cuLoaded = 0;
		 startTime = new Date();
		 reader = new FileReader();
		 alert(total)
	reader.onload = function (e) {
		 alert('读取总数：' + e.loaded);
        if (enableRead == false)
            return false;
        //根据当前缓冲区来控制客户端读取速度
        if (home_ws.bufferedAmount > step * 5) {
           setTimeout(function () {
                //继续读取
               alert('--------------》进入等待');
                loadSuccess(e.loaded);
            }, 1);
        } else {
            //继续读取
            loadSuccess(e.loaded);
        }
    }
		//开始读取
		readBlob();
}


function loadSuccess(loaded) {
    //将分段数据上传到服务器
    var blob = reader.result;
    //使用WebSocket 服务器发送数据
    //alert("cuLoaded"+cuLoaded)
    //alert("loaded"+loaded)
    if (cuLoaded == 0) {//发送文件名
    	
    }
    home_ws.send(blob);
    //如果没有读完，继续
    cuLoaded += loaded;
    if(cuLoaded==total){
    	alert("ok")
    	photoarray.splice(0, 1);
    	alert(photoarray.length)
    	if(photoarray.length!==0){	
    		sendphoto();
    	}
    	if(photoarray.length==0){	
    		post_new_null();
    	}
    }
    if (cuLoaded < total) {
        readBlob();
    } else {
       //alert('总共上传：' + cuLoaded );
    }
}
function readBlob() {
    //指定开始位置和结束位置读取文件
    var blob = file.slice(cuLoaded, cuLoaded + step);
    reader.readAsArrayBuffer(blob);
}




var time1;
function home_down() {
	time1=setTimeout(function() {
		//鼠标长按执行
	if (document.getElementById("home").style.display=="block") {
		write_post();
	}else{
		photo_num=0;
		document.getElementById("write_post_page").style.display="none";
		document.getElementById("home").style.display="block";
		document.body.style.overflowY="scroll";
		$("#chat_page").style.display="none";
		$("#chat_section").style.display="none";
		$("#post_content_page").style.display="block";
		$("#post_content_nav").style.display="block";
		$("#post_content_page").style.display="none";
		$("#w_post_usename").innerHTML="";
		$("#w_post_interest").innerHTML="";
		$("#w_post_top").innerHTML="";	
		$("#w_post_time").innerHTML="";
		$("#w_post_content").innerHTML="";	
		$("#photo_src1").src=null;
		$("#photo_src2").src=null;
		$("#photo_src3").src=null;
		$("#photo_src4").src=null;
		$("#photo_src5").src=null;
		$("#home").style.display="block";
	}
	},400);
}
function home_up() {
	clearTimeout(time1);	
}
function write_post() {
	document.getElementById("home").style.display="none";
	document.getElementById("write_post_page").style.display="block";
	document.body.style.overflowY="hidden";
}
function write_post_photo_click(files){
	$("#file_button").click();
}

function write_post_ok_click() {
	//发送微博
	var write_title=document.getElementById('write_post_title').value;
	var write_content=document.getElementById('write_post_content').innerHTML;
	var interest=document.getElementById('interest_ok').name;
	
	if (write_title.length >=5 && write_title.length <=30 ) {
		if (write_content.length >=10 && write_content.length <=300 ) {
			//alert(interest)
			if (interest!=null && interest!="") {

alert(username_page+"."+write_title+"."+write_content+"."+interest);

var new_post_n={"key":"newpost","username_page":username_page,"write_title":write_title,"write_content":write_content,"interest":interest,"photo":"n"};

//alert(new_post)
if(photoarray.length!==0){	
	var photo_length=[];
	for(var i=0;i<photoarray.length;i++){
		photo_length[photo_length.length]=photoarray[i].size;
	}
//var photo_length_array={"key":"photo_length_array"};|

var new_post_y={"key":"newpost","username_page":username_page,
		"write_title":write_title,"write_content":write_content,
		"interest":interest,"photo":"y","size":photo_length};
//home_message(JSON.stringify(photo_length_array));
	home_message(JSON.stringify(new_post_y));
	sendphoto();
	//post_new_null();
}else{
	home_message(JSON.stringify(new_post_n));
	 post_new_null()
}
			} else {
alert("没有兴趣")
			}
		} else {
alert("没有内容")
		}
	} else {
alert("没有标题")
	}
}

function post_new_null(){
	//微博发送后的页面消除工作
	var write_title=document.getElementById('write_post_title');
	var write_content=document.getElementById('write_post_content');
	var interest=document.getElementById('interest_ok');
	
	write_title.value="";
	alert(write_title);
	write_content.innerHTML="";
	interest.name=null;
	
	var photo_show=document.getElementById('photo_show');
	alert("photoarray.length;"+photoarray.length)
	if(photoarray.length==0){
		for(var i=0;i<photo_show.childNodes.length;i++){
			photo_show.removeChild($("#new_photo_post"));
			photoarray.splice(0,photoarray.length);
		}
	}
}
function chat_back() {
	//聊天返回
	$("#chat_page").style.display="none";
	$("#chat_section").style.display="none";
	$("#post_content_page").style.display="block";
	$("#post_content_nav").style.display="block";
}
function chat_in(){
	//按了评论按钮之后
	$("#post_content_page").style.display="none";
	$("#post_content_nav").style.display="none";
	$("#chat_page").style.display="block";
	alert($("#chat_page").name)
	var new_post_y={"key":"sigin_chat","post_id":$("#chat_page").name};
	home_message(JSON.stringify(new_post_y));
}
function chat_send() {
	//发送聊天
	var post_id=$("#chat_page").name;
	var post_connct=$("#chat_text").value;
	var new_post_y={"key":"new_chat_connect","post_id":post_id,"username_page":username_page,"post_connct":post_connct};
	home_message(JSON.stringify(new_post_y));
	new_chat(null,username_page,post_connct)
}
function new_chat(header_url,chat_name,chat_content) {
	//新聊天插入
	var chat_page=$("#chat_section");
	
	var chat_div=document.createElement("div");
	chat_page.appendChild(chat_div);
	
	var chat_header=document.createElement("header");
	chat_div.appendChild(chat_header);
	var chat_sjx=document.createElement("div");
	chat_div.appendChild(chat_sjx);
	var chat_setion=document.createElement("section");
	chat_setion.innerHTML=chat_content;
	chat_div.appendChild(chat_setion);
	
}





function contains(arr,obj){
	var i=arr.length;
	while(i--){
		if(arr[i]===obj){
			return true;
		}
	}
	return false;
}
function $(variable){
	var firststring=variable.substr(0,1);
	if(firststring=="#"){
		//id选择器
		return document.getElementById(variable.substr(1,variable.length-1));
	}
}

//$("#home")