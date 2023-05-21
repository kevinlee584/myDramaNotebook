import { useState } from 'react'

import './Menu.css'
import VideoesImage from "./videoes.svg"


const Menu = function({providers, showMenu, setShowMenu, setPage}) {

    const [selectedProvider, setSelectedProvider] = useState(-1)

    if (!showMenu) {
        return
    }

    const menu_blockers = []
    for(const i in providers) {
        var e = <li className='provider-block' key={providers[i].provider} onClick={() => setSelectedProvider(selectedProvider == i ? -1 : i)}>
                    <img src={providers[i].favicon} className='provider-icon'></img>
                </li>

        menu_blockers.push(e)
    }

    const record = <li className='provider-block' key={"recordProvider"} onClick={() => {setPage("record"); setShowMenu(false);}}>
                        <VideoesImage></VideoesImage>
                    </li>

    menu_blockers.push(record)

    var sorts_block = ""
    if (selectedProvider > -1) {
        let i = selectedProvider

        const sorts_blockers = Object.entries(providers[i].sorts)
            .map(e => (
                <li className='sort-blocker' key={`${providers[i].provider}/${e[0]}`} onClick={() => {setPage(e[1]); setShowMenu(false); }}>
                    <p>{ e[0] }</p>
                </li>))

        sorts_block = <ul className='sort-block'>{ sorts_blockers }</ul>
    }

    return (
        <div id='menu'>
            <ul className='menu-block'>{ menu_blockers }</ul>
            { sorts_block  }
            <div className='background-blur-layer' onClick={() => {setShowMenu(false)}}></div>
        </div>
    )

}

export default Menu;