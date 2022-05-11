import axios from "axios";

export default {
  serverData: null,
  data1: {
    test() {
      axios
        .get("url")
        .then((res) => {
          serverData = res.data;
        })
        .catch((error) => {
          console.log(error);
        });

      console.log(serverData);
    },
  },
};
