
window.onload = function(){

    mySocket = new WebSocket("ws://" + location.host + "/chat");

    mySocket.onopen = function(){
        console.log("CONGRATULATIONS THE HANDSHAKE WAS SUCCESSFUL");
    };
   
    mySocket.onmessage = function(e){
        //let mess = JSON.parse(e.data);
        console.log(e.data);
        
    }

    let button = document.getElementById('joinButton');
    button.addEventListener("click", function(){
        let room = document.getElementById('room');
        mySocket.send("join " + room.value);
    });
}