import React, { useState } from "react";
import { Alert } from "reactstrap";
import FormGenerator from "../../components/formGenerator/formGenerator";
import tokenService from "../../services/token.service";
import "../../static/css/auth/authButton.css";
import { loginFormInputs } from "./form/loginFormInputs";

export default function Login() {
  const [message, setMessage] = useState(null)
  const loginFormRef = React.createRef();      
  

  async function handleSubmit({ values }) {

    const reqBody = values;
    setMessage(null);
    await fetch("/api/v1/auth/signin", {
      headers: { "Content-Type": "application/json" },
      method: "POST",
      body: JSON.stringify(reqBody),
    })
      .then(function (response) {
        if (response.status === 200) return response.json();
        else return Promise.reject("Invalid login attempt");
      })
      .then(function (data) {
        tokenService.setUser(data);
        tokenService.updateLocalAccessToken(data.token);
        if(data.roles.includes("ADMIN")){
          window.location.href = "/admin";
        } 
        else{
          window.location.href = "/player";

        }

      })
      .catch((error) => {         
        setMessage(error);
      });            
  }

  
    return (
      <div className="fond">
        <div className="auth-page-container">
          {message ? (
            <Alert color="primary">{message}</Alert>
          ) : (
            <></>
          )}
          <h1 style={{color: "khaki"}}>Iniciar Sesión</h1>

          <div className="auth-form-container-login">
            <FormGenerator
              ref={loginFormRef}
              inputs={loginFormInputs}
              onSubmit={handleSubmit}
              numberOfColumns={1}
              listenEnterKey
              buttonText="Iniciar Sesión"
              buttonClassName="auth-button"
            />
          </div>
        </div>
      </div>
    );  

    
}