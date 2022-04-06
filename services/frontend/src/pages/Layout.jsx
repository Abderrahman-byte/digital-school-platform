import { Outlet, Link } from "react-router-dom";
import   '../styles/Layout.css';

const Layout = () => {
  return (
    <>
      <nav>
        <ul>
          <li>
            <Link to="/auth/login">  <button> Sign-in</button> </Link>
            </li>
           <li>
            <Link to="/auth/sign-up"> <button>Sign-up</button>  </Link>
            </li>
            
          
        
    
        </ul>
      </nav>

      <Outlet />
    </>
  )
};

export default Layout;