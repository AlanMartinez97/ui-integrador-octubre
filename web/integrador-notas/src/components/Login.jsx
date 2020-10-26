import React, { useEffect } from 'react';
import {Link, useHistory} from 'react-router-dom';
import "../css/Login.css"
import M from 'materialize-css/dist/js/materialize.min.js'

function Login(){

    useEffect(() => {
        M.AutoInit()
    }, [])

    return(
        <div className="card-panel login-panel pink">
            <div className="input-field col s12">
                <input id="email" type="text"/>
                <label for="email">E-mail</label>
            </div>
            <div className="input-field col s12">
                <input id="password" type="text" />
                <label for="password">Password</label>
            </div>
            <div className="col s12 center-align">
                <button className="btn waves-effect waves-light" >Loguearme</button>
            </div>
        </div>
    )
}

export default Login;