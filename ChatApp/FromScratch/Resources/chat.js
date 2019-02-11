
//<img src="boba.png" alt="boba">
//<img src="boba.png" alt="boba">

window.onload = function(){

let body = document.getElementById('body');

let homeForm = document.getElementById('form');

    homeForm.addEventListener("submit", function(e) {e.preventDefault()});

let joinButton = document.getElementById('joinButton');
    joinButton.addEventListener('click', joinChatRoom);

let uName;
let chatRoomName;
let sendButton
let message;
let mySocket;

function joinChatRoom(){

    let xhr = new XMLHttpRequest();
    xhr.open("GET", "chat.html");
    xhr.addEventListener('load', setBody);
    
    uName = document.getElementById("uName").value;
    chatRoomName = document.getElementById('room').value;

    mySocket = new WebSocket("ws://" + location.host);

    mySocket.onopen = function(){mySocket.send("join " + chatRoomName);};
    
    mySocket.onmessage = function(e){
        //let mess = JSON.parse(e.data);
        //let fMess = (mess.user + ": " + mess.message);
        //console.log(fMess);
        addToBody(e.data);
        console.log(e.data);
    };
    

    xhr.send();
    return false;
}

function setBody(){
    body.innerHTML = this.responseText;

    sendButton = document.getElementById('sendButton');
    exitButton = document.getElementById('exitButton');

    let form = document.getElementById('form');

    form.addEventListener("submit", function(e) {e.preventDefault()});

    let par = document.getElementsByTagName('p')[0];
        let h3 = document.createElement('h3');
            let text = document.createTextNode("Chat Room: " + chatRoomName);
        h3.appendChild(text);
    par.appendChild(h3);
    par.scrollIntoView();

    exitButton.addEventListener('click', function(){
        mySocket.send("exit room");
        location.reload(true);
    });

    sendButton.addEventListener('click', function(){
        message = document.getElementById('message');
        //var currentdate = new Date(); 
        //var datetime =   currentdate.getHours() + ":" + currentdate.getMinutes();
        mySocket.send(uName + ": " + message.value);
        document.getElementById('form').reset();
        window.scrollTo(0,document.body.scrollHeight);
    });
}

function addToBody(txt){
    let par = document.getElementsByTagName('p')[0];
        let p = document.createElement('p');
            let text = document.createTextNode(txt);
        p.appendChild(text);
    par.appendChild(p);
}

function sendMessage(){
    mySocket.send
}

function setRoomName(){
    let par = document.getElementsByTagName('p')[0];
        let h3 = document.createElement('h3');
            let text = document.createTextNode(chatRoomName);
        h3.appendChild(text);
    par.appendChild(h3);
}

}