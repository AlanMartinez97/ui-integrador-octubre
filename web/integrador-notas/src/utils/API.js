import axios from "axios";

const apiUrl = "localhost:7000"

const apiAxios = axios.create({
  baseURL: apiUrl,
  responseType: "json"
});

apiAxios.defaults.headers.post["Content-Type"] = "application/json"
apiAxios.defaults.headers.put["Content-Type"] = "application/json"

apiAxios.interceptors.request.use(function (config) {
  const token = localStorage.getItem("tokenApi")
  config.headers.Authorization =  token;

  return config;
});

export default apiAxios;