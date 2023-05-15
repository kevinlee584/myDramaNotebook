import RecordService from "./RecordService"

class Service {

    constructor(){
        this.dramaCache = new Map()
        this.serverUrl = "http://localhost:8080"
    }

    getDramas(page) {
        if (page == "record") return RecordService.getRecord()


        var dramas = this.dramaCache.get(page)

        if (dramas) return new Promise(resolve => resolve(dramas))

        return new Promise(resolve => {
            fetch(this.serverUrl + page)
                .then(res => res.json())
                .then(res => {
                    this.dramaCache.set(page, res)
                    resolve(res)
                })
        })
    }

    updateDramas(page) {
        if (page == "record") return RecordService.getRecord()

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