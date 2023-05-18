import "./style.css"
import Page from "./Page";
import Loader from "../Loader"

export default ({page, record, reload}) => {
    const {isLoad, dramas} = page

    return (
        <div className="frame">
            { 
                isLoad ? <Page dramas={dramas} record={record} reload={reload}></Page> 
                        : <Loader></Loader>
            }
        </div>)
}