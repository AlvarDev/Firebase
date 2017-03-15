var dbRef;
var data;

// Initialize Firebase
var config = {
    apiKey: "AIzaSyB7chZF-ccNkR3ldpPD-GyYmkKFwo7OYXo",
    authDomain: "battlesoultry.firebaseapp.com",
    databaseURL: "https://battlesoultry.firebaseio.com",
    storageBucket: "battlesoultry.appspot.com",
    messagingSenderId: "26194385362"
};

firebase.initializeApp(config);

dbRef = firebase.database().ref().child("message");
dbRef.on('value', snap => setMessages(snap.val()));

document.getElementById("messages") .addEventListener("keydown", function(event) { 
    event.preventDefault(); 
    if (event.keyCode == 13) { 
        send(); 
    } 
});

function setMessages(messages) {
    data = messages;
    var mes = messages.split("\n");
    $("#messages").text("");
    $(".remove").remove();

    var html = "";

    $.each(mes, function(i, item) {
        html += '<div class="remove">' + item + '</div>';
    });

    $("#messages").append(html);
}

function send(){
  var message = $('#typed-message').val();
  if(message.length > 0){
    $('#typed-message').val("");
    dbRef.set((data.length == 0 ? "" : data + "\n") + "Web: " + message);
  }else{
    console.log("Message is empty");
  }

}
