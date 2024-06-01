<template>
  <div class="mt-2">
    <div class="col-3">
      <label for="userId" class="col-form-label"> userId </label>
    </div>

    <div class="col-3">
      <input
        type="text"
        required
        class="form-control"
        placeholder="userId"
        v-model="userId"
      />
    </div>

    <div class="col-3 mt-5">
      <label for="message" class="col-form-label"> message </label>
    </div>

    <div class="col-9">
      <input
        type="text"
        required
        class="form-control"
        placeholder="message"
        v-model="message"
        @keyup="sendMessage"
      />
    </div>

    <div class="phone-wrap mt-5">
      <div>
        <img :src="require('@/assets/img/iphone.png')" alt="아이폰" />
      </div>
      <div class="text-wrap">
        <div v-for="(item, idx) in recvList" :key="idx">
          <h3>유저이름: {{ userId }}</h3>
          <h3>내용: {{ item.content }}</h3>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  data() {
    return {
      userId: "",
      message: "",
      recvList: [],
    };
  },
  created() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect();
  },
  methods: {
    sendMessage(e) {
      if (e.keyCode === 13 && this.userName !== "" && this.message !== "") {
        this.send();
        this.message = "";
      }
    },
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          userName: this.userName,
          content: this.message,
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = "http://localhost:8000";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/send", (res) => {
            console.log("구독으로 받은 메시지 입니다.", res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body));
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
  },
};
</script>

<style scoped>
.phone-wrap {
  width: 20vw;
  
  position: relative;
}

.phone-wrap img {
  width: 100%;
}

.text-wrap {
  position: absolute;
  top: 51%;
  left: 51%;
  width: 80%;
  height: 71%;
  transform: translate(-50%, -50%);

  background-color: black;

  font-size: 20px;
  color: wheat;
  font-family: Arial, Helvetica, sans-serif;

  padding: 10px;
  overflow: scroll;
}
</style>
