
const URL = 'http://localhost:7070/api/'

function apiFacade()
{

    const setToken = (token) =>
    {
        localStorage.setItem('jwtToken', token)
    }

    const getToken = () =>
    {
        return localStorage.getItem('jwtToken')
    }

    const handleHttpErrors = (res) =>
    {

        if (!res.ok)
        {
            return Promise.reject({ status: res.status, fullError: res.json() })
        }
        return res.json()
    }

    const makeOptions = (method, payload, addToken) =>
    {

        const opts = {
            method: method,
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json"
            }
        }

        if (addToken)
        {
            opts.headers["Authorization"] = `Bearer ${getToken()}`
        }

        if (payload)
        {
            opts.body = JSON.stringify(payload)
        }

        return opts;
    }

    const fetchData = (endpoint, method, payload) =>
        {
            const options = makeOptions(method, payload, false); //True add's the token
            return fetch(URL + endpoint, options)
            .then(handleHttpErrors)
            .catch((error) => {
                if (error.status) {
                  error.fullError.then((e) => console.log(JSON.stringify(e)));
                } else {
                  console.log("error", error);
                }
            });
        }

    return {
        makeOptions,
        setToken,
        getToken,
        fetchData
    }
}

const facade = apiFacade();
export default facade;