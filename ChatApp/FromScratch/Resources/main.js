//<img src="boba.png" alt="boba">
//<img src="boba.png" alt="boba">

window.onload = function(){

    mySocket = new WebSocket("ws://" + location.host);

    mySocket.onopen = function(){
        console.log("CONGRATULATIONS THE HANDSHAKE WAS SUCCESSFUL");
        //mySocket.send("Hello Server");
        //mySocket.send("Oh real funny, just copying everything");
    };
   
    mySocket.onmessage = function(e){
        //let mess = JSON.parse(e.data);
        console.log(e.data);
        
    }

    let button = document.getElementById('button');
    button.addEventListener("click", function(){mySocket.send("hello server");})
}