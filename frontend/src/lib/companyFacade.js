import facade from "@/utils/apiFacade.js"

const URL = "http://localhost:7070/api/";
const SIGNUP_ROUTE = "create";

function companyFacade() {
 const signup = (companyName, admin, callback) => {
    const payload = { companyName: companyName, companyAdmin: admin };

    const options = facade.makeOptions("POST", payload);

    return fetch(URL + SIGNUP_ROUTE, options)
      .then(facade.handleHttpErrors)
      .then((json) => {
        callback(true);
      })
      .catch((error) => {
        if (error.status) {
          error.fullError.then((e) => console.log(JSON.stringify(e)));
        } else {
          console.log("seriøs fejl", error);
        }
      });
  };

  return {
    signup,
  };
}



const compFacade = companyFacade();
export default compFacade;