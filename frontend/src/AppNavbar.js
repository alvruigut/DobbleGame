import React, { useState, useEffect } from 'react';
import { Navbar, NavbarBrand, NavLink, NavItem, Nav, NavbarText, NavbarToggler, Collapse } from 'reactstrap';
import { Link } from 'react-router-dom';
import tokenService from './services/token.service';
import jwt_decode from "jwt-decode";

function AppNavbar() {
    const [roles, setRoles] = useState([]);
    const [username, setUsername] = useState("");
    const jwt = tokenService.getLocalAccessToken();
    const [collapsed, setCollapsed] = useState(true);
    const toggleNavbar = () => setCollapsed(!collapsed);

    useEffect(() => {
        if (jwt) {
            setRoles(jwt_decode(jwt).authorities);
            setUsername(jwt_decode(jwt).sub);
        }
    }, [jwt])

    let adminLinks = <></>;
    let playerLinks = <></>;
    let ownerLinks = <></>;
    let userLinks = <></>;
    let userLogout = <></>;
    let publicLinks = <></>;

    roles.forEach((role) => {
        if (role === "ADMIN") {
            adminLinks = (
                <>
                   
                   
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/players">Jugadores</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/games">Partidas</NavLink>
                    </NavItem>
                </>
            )
        }
        if (role === "OWNER") {
            ownerLinks = (
                <>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/myPets">My Pets</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/consultations">Consultations</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/plan">Plan</NavLink>
                    </NavItem>
                </>
            )
        }
        if (role === "VET") {
            ownerLinks = (
                <>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/consultations">Consultations</NavLink>
                    </NavItem>
                </>
            )
        }
        if (role === "PLAYER") {
            playerLinks = (
                <>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/profile">Mi Perfil</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/stadistics">Estadísticas</NavLink>

                    </NavItem>
                </>
            )
        }

        if (role === "CLINIC_OWNER") {
            ownerLinks = (
                <>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/clinics">Clinics</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/owners">Owners</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/consultations">Consultations</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "khaki" }} tag={Link} to="/vets">Vets</NavLink>
                    </NavItem>
                </>
            )
        }
    })

    if (!jwt) {
        publicLinks = (
            <>
                <NavItem>
                    <NavLink style={{ color: "khaki" }} id="register" tag={Link} to="/register">Registrarse</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink style={{ color: "khaki" }} id="login" tag={Link} to="/login">Inicio de Sesión</NavLink>
                </NavItem>
            </>
        )
    } else {
        userLogout = (
            <>
                <NavbarText style={{ color: "khaki" }} className="justify-content-end">{username}</NavbarText>
                <NavItem className="d-flex">
                    <NavLink style={{ color: "khaki" }} id="logout" tag={Link} to="/logout">Cerrar Sesión</NavLink>
                </NavItem>
            </>
        )

    }


    return (
        <div>
            <Navbar expand="md" dark color="black">
                { jwt ? (
                    roles.includes("ADMIN") ?   <NavbarBrand href="/admin">
                    <img alt="logo" src="/dobble-logo2.png" style={{ height: 40, width: 40 }} />
                    <NavbarText style={{ color: "khaki" }}> Dobble</NavbarText>
                </NavbarBrand>:  <NavbarBrand href="/player">
                    <img alt="logo" src="/dobble-logo2.png" style={{ height: 40, width: 40 }} />
                    <NavbarText style={{ color: "khaki" }}> Dobble</NavbarText>
                </NavbarBrand>
                ): 
                <NavbarBrand href="/">
                    <img alt="logo" src="/dobble-logo2.png" style={{ height: 40, width: 40 }} />
                    <NavbarText style={{ color: "khaki" }}> Dobble</NavbarText>
                </NavbarBrand>
                }
              
                <NavbarToggler onClick={toggleNavbar} className="ms-2" />
                <Collapse isOpen={!collapsed} navbar>
                    <Nav className="me-auto mb-2 mb-lg-0" navbar>
                        {userLinks}
                        {adminLinks}
                        {playerLinks}
                        {ownerLinks}
                    </Nav>
                    <Nav className="ms-auto mb-2 mb-lg-0" navbar>
                        {publicLinks}
                        {userLogout}
                    </Nav>
                </Collapse>
            </Navbar>
        </div>
    );
}

export default AppNavbar;