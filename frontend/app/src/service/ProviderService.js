class Service {

    constructor(){
        this.serverUrl = "http://localhost:8080"
    }

    getProviders() {
        return new Promise(resolve => {
            fetch(this.serverUrl + "/providers")
            .then(res => res.json())
            .then(res => resolve(res))
        })
    }

}

const ProviderService = new Service()

export default ProviderService