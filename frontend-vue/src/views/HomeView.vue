<template>
<<<<<<< Updated upstream
  <div id="app">
    유저이름: 
    <input
      v-model="userName"
      type="text"
    >
    내용: <input
      v-model="message"
      type="text"
      @keyup="sendMessage"
    >
    <div
      v-for="(item, idx) in recvList"
      :key="idx"
    >
      <h3>유저이름: {{ item.userName }}</h3>
      <h3>내용: {{ item.content }}</h3>
=======
  <div class="content" id="websocket">
    <div>&nbsp;</div>
    <div class="row">
      <div class="col">
        <button class="btn btn-sm btn-info" @click="connect">
          Create connection
        </button>
        <button class="btn btn-sm btn-success" @click="startTask">
          Start Task
        </button>
        <button class="btn btn-sm btn-danger" @click="stopTask">
          Stop Task
        </button>
        <button class="btn btn-sm btn-primary" @click="disconnect">
          Close connection
        </button>
      </div>
    </div>
    <div>&nbsp;</div>
    <div class="row">
      <div class="col">
        <ul class="list-group" style="height: 500px; overflow: scroll">
          <li
            class="list-group-item d-flex justify-content-between align-items-center"
            v-for="(m, idx) in messages"
            :key="'m-' + idx"
          >
            {{ m }}
          </li>
        </ul>
      </div>
>>>>>>> Stashed changes
    </div>
  </div>
</template>

<script>
<<<<<<< Updated upstream
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
=======
import { Client } from "@stomp/stompjs"
>>>>>>> Stashed changes

export default {
  data() {
    return {
<<<<<<< Updated upstream
      userName: "",
      message: "",
      recvList: []
    }
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect()
  },
  methods: {
    sendMessage (e) {
      if(e.keyCode === 13 && this.userName !== '' && this.message !== ''){
        this.send()
        this.message = ''
      }
    },    
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = { 
          userName: this.userName,
          content: this.message 
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },    
    connect() {
      const serverURL = "http://localhost:8000"
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send", res => {
            console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body))
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );        
    }
  }
}
=======
      stompClient: null,
      messages: [],
    };
  },
  methods: {
    connect() {
      let socket = new SockJS("/connect");
      stompClient = Stomp.over(socket);
      stompClient.connect({}, function (frame) {
        this.handleMessageReceipt("Connected");

        // 구독
        // 사용법 : stompClient.subscribe(채팅방이름, 함수)
        stompClient.subscribe("/topic/messages", function (messageOutput) {
          this.handleMessageReceipt(messageOutput.body);
        });
      });
    },
    disconnect() {
      if (stompClient != null) {
        stompClient.disconnect();
      }
      this.handleMessageReceipt("Disconnected");
    },
    startTask() {
      if (stompClient != null) {
        stompClient.send("/ws/start");
      } else {
        alert("Please connect first");
      }
    },
    stopTask() {
      if (stompClient != null) {
        stompClient.send("/ws/stop");
      } else {
        alert("Please connect first");
      }
    },
    handleMessageReceipt(messageOutput) {
      this.messages.push(messageOutput);
    },
  },
};
>>>>>>> Stashed changes
</script>
