import "./loader.css"

const Loader = () => {
    return (<div className="loader">
        { [...Array(10).keys()].map((number) =><div className="wave" key={`wave-${number}`}></div>)  }
    </div>)
}

export default Loader