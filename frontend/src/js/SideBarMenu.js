import React, { useEffect, useState } from 'react'

import '../css/sidebarmenu.css'
import actionMap from './actionMap'
import { serverUrl } from './AppProperties'


const SidebarMenu = function() {
    const [show, setShow] = useState(false)
    const [selectedBlocker, setSelectedBlocker] = useState(0)
    const [error, setError] = useState(null);
    const [providers, setProviders] = useState([])
    const [isLoaded, setIsLoaded] = useState(false);

    // register clickBarBtn function to actionMap
    useEffect(() => {
        var count = 0;
        actionMap.set("clickBarsBtn", ()=>{
            count++;
            if ((count % 2) == 0) {
                setShow(false)
                setSelectedBlocker(0)
            }else {
                setShow(true)
            }
            
        })
    }, [])

    useEffect(() => {
        fetch(`${serverUrl}/providers`)
        .then(res => res.json())
        .then(result => {
            setProviders(result)
            setIsLoaded(true)
        }, error => {
            setError(error)
            setIsLoaded(true)
        })
    }, [])

    if (error) return <div>Error: { error.message }</div>

    if (!show) return 



    if (isLoaded && show) {
        var selecteBlocker = (n) => () => {
            setSelectedBlocker((n & selectedBlocker) == n ? 0 : n)
        }

        var clickSort = (sortUrl) => () => {
            actionMap.get("setPage")(`${serverUrl}${sortUrl}`)
        }

        var list = []

        for(const i in providers) {

            if (selectedBlocker == 2**i) 
                list.push((
                    <div className='menu-block' key={providers[i].provider}>
                        <div className='provider-block ' onClick={selecteBlocker(2**i)}>
                            <p>{ providers[i].provider }</p>
                        </div>
                        { Object.entries(providers[i].sorts).map(e => (
                        <div className='sort-block' key={providers[i].provider + e[0]} onClick={clickSort(e[1])}>
                            <p>{ e[0] }</p>
                        </div>)) }
                    </div>
                ))
            else 
                list.push((
                    <div className='menu-block' key={providers[i].provider}>
                        <div className='provider-block ' onClick={selecteBlocker(2**i)}>
                            <p>{ providers[i].provider }</p>
                        </div>
                    </div>
                ))
        }
        
        return (
            <>
                <div id='menu-bar'>
                    { list }
                </div>
                <div className='background-blur-layer'></div>
            </>
        )
    }

    
}

export default SidebarMenu;