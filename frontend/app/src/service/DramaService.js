import RecordService from "./RecordService"

class Service {

    constructor(){
        this.dramaCache = new Map()
        this.serverUrl = "http://localhost:8080"
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
}

const DramaService = new Service()

export default DramaService