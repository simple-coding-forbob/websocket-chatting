import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import constraints from "@/utils/constrains";

const connect = () => {
  let socket = new SocketJS(constraints.SERVER_SOCKET_API);
  let stompClient = Stomp.over(socket);
  stompClient.connect({}, function () {
    stompClient.subscribe("/sub", function () {});
  });
};

const webSocket = {
  connect,
};

export default webSocket;
