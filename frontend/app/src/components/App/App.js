import { useEffect, useState } from "react";

import Title from "../Title";
import Page from "../Page";
import RecordService from "../../service/RecordService";
import DramaService from "../../service/DramaService";

const App = function(){
	const [record, setRecord] = useState([])
	const [page, setPage] = useState({action: "", url: "", args: [], status: "loading", dramas: []})
	

	/*
		action = {get, search, reload, error}
	*/
	const loadPage = function(action, url, args) {
        setPage({status: "loading", dramas: []})

		let f

		if (action == "get") {
			if (url == "/record") f = RecordService.getRecord()
			else f = f = DramaService.getDramas(url, args)
		} else if(action == "search") {
			f = DramaService.searchDramas(url, args)
		}

        f.then(res => {
            setPage({action, url, args, status: "loaded", dramas: res})
        })
    }

	// default page
	useEffect(() => loadPage("get", "/record"), [])

	// set record
	useEffect(() => {RecordService.getRecord().then(res => setRecord(res))}, [])


	return (
		<>
			<Title setPage={loadPage}></Title>
			<Page page={page} record={record} reload={() => loadPage(page.action, page.url, false)}></Page>
		</>
	)
};

export default App;