
const URL = "http://localhost:7070/api/";
const AUTHENTICATION_ROUTE = "auth/login";

function apiFacade() {
  const setToken = (token) => {
    localStorage.setItem("jwtToken", token);
  };

  const getToken = () => {
    return localStorage.getItem("jwtToken");
  };

  const logout = (callback) => {
    localStorage.removeItem("jwtToken");
    callback(false)
  }

  const handleHttpErrors = (res) => {
    if (!res.ok) {
      return Promise.reject({ status: res.status, fullError: res.json() });
    }
    return res.json();
  };

  const login = (user, password,callback) => {
    console.log("Jeg er fanget inde i login funktionen", user, password);

    const payload = { username: user, password: password };

    const options = makeOptions("POST", payload);

    return fetch(URL + AUTHENTICATION_ROUTE, options)
      .then(handleHttpErrors)
      .then((json) =>{
        callback(true)
        setToken(json.token)})
      .catch((error) => {
        if (error.status) {
          error.fullError.then((e) => console.log(JSON.stringify(e)));
        } else {
          console.log("seriøs fejl", error);
        }
      });
  };
  const fetchData = (endpoint, method, payload) =>
    {
        const options = makeOptions(method, payload,true); //True adds the token
        return fetch(URL + endpoint, options).then(handleHttpErrors);
    }

  const makeOptions = (method, payload,addToken) => {
    const opts = {
      method: method,
      headers: {
        "Content-type": "application/json",
        Accept: "application/json",
      },
    };
    if(addToken){
        opts.headers["Authorization"] = `Bearer ${getToken()}`;
    }
    if (payload) {
      opts.body = JSON.stringify(payload);
    }

    return opts;
  };

  const getUserRoles = () => {
    const token = getToken();
    if (token != null) {
      const payloadBase64 = getToken().split(".")[1];
      const decodedClaims = JSON.parse(window.atob(payloadBase64));
      const roles = decodedClaims.roles;
      return roles;
    } else return "";
  };

  const hasUserAccess = (neededRole, loggedIn) => {
    const roles = getUserRoles().split(",");
    return loggedIn && roles.includes(neededRole);
  };

  return {
    makeOptions,
    getToken,
    setToken,
    login,
    getUserRoles,
    hasUserAccess,
    logout,
    fetchData,
    
  };
}

const facade = apiFacade();
export default facade;