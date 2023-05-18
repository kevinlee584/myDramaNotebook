import { useEffect, useState } from "react";

import Title from "../Title";
import Page from "../Page";
import RecordService from "../../service/RecordService";
import DramaService from "../../service/DramaService";

const App = function(){
	const [record, setRecord] = useState([])
	const [page, setPage] = useState({page: "", isLoad: false, dramas: []})
	
	// default page
	useEffect(() => {
		RecordService.getRecord()
		.then(res => {
			setPage({page: "record", isLoad: true, dramas: res})
			setRecord(res)
		})
	}, [])

	const loadPage = (cache) => function(p = page.page) {
        setPage({page: p, isLoad: false, dramas: []})
        var f = (p == "record") ? RecordService.getRecord() : DramaService.getDramas(p, cache)
        f.then(res => {
            setPage({page: p, isLoad: true, dramas: res})
        })
    }

	return (
		<>
			<Title setPage={loadPage(true)}></Title>
			<Page page={page} record={record} reload={loadPage(false)}></Page>
		</>
	)
};

export default App;