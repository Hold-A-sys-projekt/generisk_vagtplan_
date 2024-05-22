import facade from "@/utils/apiFacade.js"

const URL = "http://localhost:7070/api/companies/";
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




async function postCompany(companyName, companyAdmin, data){
  const url = `${URL}${companyName}/${companyAdmin}`;

  try {
      const response = await fetch(url, {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(data)
      });

      if (!response.ok) {
          throw new Error(`Error: ${response.status} ${response.statusText}`);
      }

      const result = await response.json();
      console.log('Success:', result);
      return result;
  } catch (error) {
      console.error('Error:', error);
      throw error;
  }
}

  return {
    signup,
    postCompany,
  };
}



const compFacade = companyFacade();
export default compFacade;