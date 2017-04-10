var BaseUrl = 'http://localhost:8080';

const Config = {
    createMerchant : BaseUrl +'/storecast/api/admin/merchant',
    editMerchant : BaseUrl +'/storecast/api/admin/merchant/',
    login : BaseUrl +'/storecast/api/admin/login',
    getMerchantList: BaseUrl + '/storecast/api/admin/merchant',
    getStoreInfo:  BaseUrl + '/storecast/api/admin/store/',
    addStore: BaseUrl + '/storecast/api/admin/store/',
    getMerchant: BaseUrl + '/storecast/api/admin/merchant/',
	editStore: BaseUrl + '/storecast/api/admin/store/',
	getStoresInfo: BaseUrl + '/storecast/api/admin/store/list/',
	getStoreInfo: BaseUrl + '/storecast/api/admin/store/',	

};

export default Config;
