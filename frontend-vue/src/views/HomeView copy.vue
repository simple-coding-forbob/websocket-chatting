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
        v-model.lazy="chatMember.userId"
      />
    </div>

    <div class="col-3 mt-5">
      <label for="chatMessage" class="col-form-label"> chatMessage </label>
    </div>

    <div class="col-9">
      <input
        type="text"
        required
        class="form-control"
        placeholder="chatMessage"
        v-model.lazy="chatMember.chatMessage"
      />
    </div>

    <div class="phone-wrap mt-5">
      <div>
        <img :src="require('@/assets/img/iphone.png')" alt="아이폰" />
      </div>
      <div class="text-wrap">
        <div>
          <h3>유저이름: {{ chatMember.userId }}</h3>
          <h3>내용: {{ chatMember.chatMessage }}</h3>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { debounce } from "lodash";
import constraints from "@/utils/constrains";

export default {
  data() {
    return {
      chatMember: {
        userId: "",
        chatMessage: "",
      },
      connected: false,
    };
  },
  methods: {
    connect() {
      let socket = new SockJS(constraints.SERVER_URL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 주소를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/sub", (res) => {
            console.log("구독으로 받은 메시지 입니다.", res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.chatMember = JSON.parse(res.body);
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
    send() {
      if (this.stompClient && this.connected) {
        this.stompClient.send("/pub", JSON.stringify(this.chatMember), {});
      }
    },
  },
  // 0.5초동안 키입력이 없으면 send() 실행 : 성능 향상(debouncing)
  watch: {
    "chatMember.chatMessage": debounce(function () {
      this.send();
    }, 500),
  },
  mounted() {
    // App.vue가 생성되면 소켓 연결을 시도합니다.
    this.connect();
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
