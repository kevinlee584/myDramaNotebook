import React, { useState } from "react";

import Title from "../Title";
import Page from "../Page";

const App = () => {
	const [page, setPage] = useState(null)
	
	if (!page) {
		return  (
			<Title setPage={setPage}></Title>
		)
	}else {
		return (
			<>
			<Title setPage={setPage}></Title>
			<Page page={page}></Page>
			</>
		)
	}
};

export default App;