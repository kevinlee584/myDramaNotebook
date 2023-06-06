import "./style.css"
import Page from "./Page";
import Loader from "../Loader"

export default ({page, record, reload}) => {
    const {status, dramas} = page

    const statusPage = {
        "loaded" : () => <Page dramas={dramas} record={record} reload={reload}></Page>, 
        "loading": () => <Loader></Loader>
    }

    return (
        <div className="frame">
            { statusPage[status]() }
        </div>)
}