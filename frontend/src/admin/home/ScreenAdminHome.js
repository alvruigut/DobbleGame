import {
    Button,
    ButtonGroup,
    Col,
    Container,
    Input,
    Row,
    Table,
  } from "reactstrap";

  import tokenService from '../../services/token.service';

  const user = tokenService.getUser();
export default function ScreenAdminHome(){

  const KhakiBox = ({ children }) => (
    <div style={{ backgroundColor: "khaki", padding: "15px", borderRadius: "8px",marginTop: "20vh" }}>
      {children}
    </div>
  );





    return(
      <div
      className="create-game-container"
      style={{
        backgroundColor: "rgb(174, 97, 213)",
        height: "99vh",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >

<KhakiBox>    
      <h1 style={{ color: "black" }}>Bienvenido {user.username}</h1>

      </KhakiBox>

    </div>
);
}