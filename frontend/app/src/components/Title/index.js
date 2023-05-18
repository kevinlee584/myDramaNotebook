import { useEffect, useState } from "react";
import Title from "./Title";
import Menu from "../Menu"
import ProviderService from "../../service/ProviderService";

export default function({setPage}) {
    const [providers, setProviders] = useState(null)
    const [showMenu, setShowMenu] = useState(false)

    useEffect(() => {
        ProviderService.getProviders()
            .then(res => setProviders(res))
    }, [])

    if (!providers) return (<Title showMenu={showMenu} setShowMenu={(_ignore) => {}}></Title>)
    else return (
        <>
        <Title showMenu={showMenu} setShowMenu={setShowMenu}></Title>
        <Menu providers={providers} showMenu={showMenu} setShowMenu={setShowMenu} setPage={setPage}></Menu>
        </>
    )
}