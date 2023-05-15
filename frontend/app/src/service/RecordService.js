class Service {

    constructor(){
        this.serverUrl = "http://localhost:8080"
        this.headers = {
            "Content-Type": "application/json",
            "Accept": "application/json, text/plain;charset=UTF-8"
        }
        
    }

    getRecord() {
        return new Promise(resolve => {
            fetch(`${this.serverUrl}/user/record`)
                .then(res => res.json())
                .then(res => {
                    resolve(res)
                })
        })
    }

    removeDrama(providerName, dramaName) {
        return new Promise(resolve => {
            fetch(`${this.serverUrl}/user/remove`, 
                {method: "DELETE", 
                headers: this.headers, 
                body: JSON.stringify({provider: providerName, name: dramaName})})
            .then(res => resolve(res));
        })
    }

    addDrama(providerName, dramaName) {
        return new Promise(resolve => {
            fetch(`${this.serverUrl}/user/save`, 
                {method: "POST", 
                headers: this.headers, 
                body: JSON.stringify({provider: providerName, name: dramaName})})
            .then(res => resolve(res))
        })
    }
    

}

const RecordService = new Service()

export default RecordService