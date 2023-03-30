/*const eventSource = new EventSource("http://localhost:8085/test/message/send/1")

eventSource.onmessage = (event) => {
   console.log(1, event);
   const data = JSON.parse(event.data);
   console.log(2, data);
}*/
function getMessageBox(msgInput) {
   return `<div class="sent_msg">
       <p> ${msgInput} </p>
       <span class="time_date"> 11:19 | Today</span>
       </div>`;
}
function appendNewMessage() {
   let chatBox = document.querySelector("#chat-box");
   let msgInput = document.querySelector("#chat-outgoing-msg");
   let chatOutgoingBox = document.createElement("div");
   chatOutgoingBox.className = "outgoing_msg";
   chatOutgoingBox.innerHTML = getMessageBox(msgInput.value);
   chatBox.append(chatOutgoingBox);
   msgInput.value = '';
}
document.querySelector("#chat-send").addEventListener("click", () => {
   appendNewMessage();
});

document.querySelector("#chat-outgoing-msg").addEventListener("keydown", (event) => {
   if(event.keyCode === 13) {
      appendNewMessage();
   }
});


/*
<div className="outgoing_msg">
   <div className="sent_msg">
      <p>Lorem Ipsum refers to text that the DTP (Desktop Publishing) industry use as replacement text when
         the real text is not </p>
      <span className="time_date"> 11:19 | Today</span>
   </div>
</div>*/
