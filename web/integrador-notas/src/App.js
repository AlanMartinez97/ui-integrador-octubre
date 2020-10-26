import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import 'materialize-css/dist/css/materialize.min.css';
import Login from './components/Login'
import Register from './components/Register'
import Home from './components/Home'

function App() {
  return (
    <BrowserRouter>
      <div className="container">
        <Switch>
          <Route exact path="/" component={ Home } />
          <Route exact path="/login" component={ Login }/>
          <Route exact path="/register" component={ Register }/>
          <Route path="*" render= {() => <h1>Not Found</h1>} />
        </Switch>
      </div>
    </BrowserRouter>
  );
}

export default App;
