// Initialize Firebase
var config = {
    apiKey: "AIzaSyB7chZF-ccNkR3ldpPD-GyYmkKFwo7OYXo",
    authDomain: "battlesoultry.firebaseapp.com",
    databaseURL: "https://battlesoultry.firebaseio.com",
    storageBucket: "battlesoultry.appspot.com",
    messagingSenderId: "26194385362"
};

firebase.initializeApp(config);

var dbRef = firebase.database().ref().child("message");
dbRef.on('value', snap => setMessages(snap.val()));

function setMessages(messages){
  var res = messages.replace("\n", "<br>");
  $("#messages").text("");
  $("#messages").append(res);

}
