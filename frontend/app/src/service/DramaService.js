import RecordService from "./RecordService"

class Service {

    constructor(){
        this.dramaCache = new Map()
        this.serverUrl = "http://localhost:8080"
        this.headers = {
            "Content-Type": "application/json",
            "Accept": "application/json, text/plain;charset=UTF-8"
        }
    }

    getDramas(page, cache=true) {
        if (cache) {
            var dramas = this.dramaCache.get(page)
            if (dramas) return Promise.resolve(dramas)
        }

        return new Promise(resolve => {
                fetch(this.serverUrl + page)
                    .then(res => res.json())
                    .then(res => {
                        this.dramaCache.set(page, res)
                        resolve(res)
                    })
                })
    }

    searchDramas(page, keyword) {
        return new Promise(resolve => {
            fetch(this.serverUrl + page, {
                method: "POST", 
                headers: this.headers, 
                body: JSON.stringify(keyword)
            })
            .then(res => res.json())
            .then(res => resolve(res))
        })
    }
}

const DramaService = new Service()

export default DramaService