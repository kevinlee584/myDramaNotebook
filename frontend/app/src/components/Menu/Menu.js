import { useState } from 'react'

import './Menu.css'
import VideoesImage from "./videoes.svg"
import SearchImage from "./search.svg"

const SearchBox = function({url, setShowMenu, setPage}) {
    const [keyword, setKeyword] = useState('')

    console.log(`${url}|${keyword}`)

    var onSubmit = (event) => {
        event.preventDefault()
        setPage("search", url, keyword)
        setShowMenu(false)
    }

    
    return (<li className='search_blocker'>
                <form onSubmit={onSubmit}>
                    <input type='text' onChange={event => setKeyword(event.target.value)}/>
                    <button onClick={onSubmit}>
                        <SearchImage></SearchImage>
                    </button>
                </form>
            </li>)

}


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

    const record = <li className='provider-block' key={"recordProvider"} onClick={() => {setPage("get", "/record"); setShowMenu(false);}}>
                        <VideoesImage></VideoesImage>
                    </li>

    menu_blockers.push(record)

    var sorts_block = ""
    if (selectedProvider > -1) {
        let i = selectedProvider

        const sorts_blockers = Object.entries(providers[i].sorts)
            .map(e => (
                <li className='sort-blocker' key={`${providers[i].provider}/${e[0]}`} onClick={() => {setPage("get", e[1], true); setShowMenu(false); }}>
                    <p>{ e[0] }</p>
                </li>))

        sorts_block = (
        <ul className='sort-block'>
            <SearchBox url={providers[i].search}setShowMenu={setShowMenu} setPage={setPage}></SearchBox>
            { sorts_blockers }
        </ul>)
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