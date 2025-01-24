// const GET_WALLET_ID_URL = (phoneNumber) => `http://localhost:3000/api/get_wallet_id${phoneNumber ? `?phoneNumber=${phoneNumber}` : ''}`;
// const GET_BALANCE_URL = (walletId) => `http://localhost:3000/api/${walletId}/get_balance`;
// const GET_TRANSACTIONS_URL = (walletId) => `http://localhost:3000/api/${walletId}/get_transactions_history`;

const GET_WALLET_ID_URL = (phoneNumber) => `http://web-alpha.digital.vn/ewallet3/ewallet-business-customer/api/v1/account/get-wallet-by-phone?phoneNumbers=${phoneNumber}`;
const GET_BALANCE_URL = (walletId) => `http://10.58.244.242:8380/accounting-service/api/v1/public/check-balance/${walletId}`;
const GET_TRANSACTIONS_URL = (walletId) => `http://10.58.244.242:8380/accounting-service/api/v1/public/accounting-history/getTransactionHistoryByWalletId/${walletId}`;
const GET_BALANCE_MASTER_URL = (walletId) => `http://10.58.244.242:8380/accounting-service/api/v1/public/check-balance-master/${walletId}`;


// const GET_WALLET_ID_URL = (phoneNumber) => `http://web-alpha.digital.vn/ewallet3/ewallet-business-customer/api/v1/account/get-wallet-by-phone?phoneNumbers=${phoneNumber}`;
// const GET_BALANCE_URL = (walletId) => `http://web-alpha.digital.vn/ewallet/ewallet-accounting-service/api/v1/public/check-balance/${walletId}`;
// const GET_TRANSACTIONS_URL = (walletId) => `http://web-alpha.digital.vn/ewallet/ewallet-accounting-service/api/v1/public/accounting-history/getTransactionHistoryByWalletId/${walletId}`;
// const GET_BALANCE_MASTER_URL = (walletId) => `http://web-alpha.digital.vn/ewallet/ewallet-accounting-service/api/v1/public/check-balance-master/${walletId}`;


// const GET_BALANCE_URL = (walletId) => `http://10.58.244.242:8382/accounting-service/api/v1/public/check-balance/${walletId}`;
// const GET_BALANCE_MASTER_URL = (walletId) => `http://10.58.244.242:8382/accounting-service/api/v1/public/check-balance-master/${walletId}`;
// const GET_TRANSACTIONS_URL = (walletId) => `http://10.58.244.242:8382/accounting-service/api/v1/public/accounting-history/getTransactionHistoryByWalletId/${walletId}`;




//const GET_WALLET_ID_URL = (phoneNumber) => `json/get_wallet_id.json${phoneNumber ? `?phoneNumber=${phoneNumber}` : ''}`;
// const GET_BALANCE_URL = (walletId) => `json/get_balance.json`;
// const GET_TRANSACTIONS_URL = (walletId) => `json/get_trans_history.json`;

let a = null;

const run = (id) => {
    const typePhoneNumber = document.querySelector(`#${id} input[name="type-phone-number"]`);
    const typeWalletId = document.querySelector(`#${id} input[name="type-wallet-id"]`);
    const input = document.querySelector(`#${id} input[name="input-text"]`);
    const button = document.querySelector(`#${id} button`);
    const balance = document.querySelector(`#${id} p[data-name="balance"]`);
    const table = document.querySelector(`#${id} table`);

    const getBalance = async (walletId) => {
        const res = await fetch(GET_BALANCE_URL(walletId));
        const data = (await res.json()).data;

        balance.classList.remove('d-none');
		const nFormat = new Intl.NumberFormat();
        balance.querySelector('span').innerHTML = nFormat.format(data.balance);
    }
	
	const getBalanceMaster = async (walletId) => {
        const res = await fetch(GET_BALANCE_MASTER_URL(walletId));
        const data = (await res.json()).data;

        balance.classList.remove('d-none');
		const nFormat = new Intl.NumberFormat();
		
        balance.querySelector('span').innerHTML = nFormat.format(data.balance);
    }

    const getTransactionsHistory = async (walletId) => {
        const res = await fetch(GET_TRANSACTIONS_URL(walletId));
        const data = (await res.json()).data;

        let html = data.map((item, index) => {
			//const today = new Date(item.dateCreated);
			//const yyyy = today.getFullYear();
			//let mm = today.getMonth() + 1; // Months start at 0!
			//let dd = today.getDate();
		
			//if (dd < 10) dd = '0' + dd;
			//if (mm < 10) mm = '0' + mm;

			//const formattedToday = dd + '/' + mm + '/' + yyyy;

			
			const t = generateDatabaseDateTime2(item.dateCreated);
			

			
			let transType = item.transType;
			if(transType === null) transType ="";
			switch(transType){
				case "021000" : transType = "Transfer"; break;
				case "571010" : transType = "Buy Airtime"; break;
				case "572001" : transType = "Payment"; break;
				case "571020" : transType = "Sell Airtime"; break;
				case "001006" : transType = "Reset PIN"; break;
				case "003000" : transType = "Active Account"; break;
				case "003212" : transType = "Auto unlock account"; break;
				case "311002" : transType = "Check history"; break;
				case "311001" : transType = "Check balance"; break;
				case "311301" : transType = "Check bank balance"; break;
				case "032100" : transType = "Stock In"; break;
				case "032101" : transType = "Stock Out"; break;
				case "571099" : transType = "Disbursement"; break;
				case "571101" : transType = "Disbursement"; break;
				default: ; break;
			}
			
		
			const nFormat = new Intl.NumberFormat('en-US', {
				style: 'currency',
				currency: 'USD',
				maximumFractionDigits: 4,
				minimumFractionDigits: 0
			});
			//const nFormat = new Intl.NumberFormat();
			let amount = 0;
			if((transType == "Disbursement") && walletIdNumber === item.walletToId) {
			
				amount = nFormat.format(item.amount);
			
			} else {
				amount = nFormat.format(item.amount + item.fee - item.discount);
			}
			
			
			var walletIdNumber = Number(walletId);
			
            return ` 
                <tr>
                    <td>${t}</td>
                    <td>${item.transactionId}</td>
                    <td>${transType}</td>
                    <td class="${walletIdNumber === item.fromWalletId ? 'text-danger' : 'text-success'}">
                        ${walletIdNumber === item.fromWalletId ? '-' : '+'}
                        ${amount}
                    </td>
                </tr>
            `
        });

        table.classList.remove('d-none');
        table.querySelector('tbody').innerHTML = html.join('');
    }
	
	function generateDatabaseDateTime(date) {
		return date.toISOString().replace("T"," ").substring(0, 19);
	}
	
	function generateDatabaseDateTime2(date) {
		return date.replace("T"," ").substring(0, 19);
	}
	
//	const formattedToday(date) {
//		
//		const today = new Date(date);
//		const yyyy = today.getFullYear();
//		let mm = today.getMonth() + 1; // Months start at 0!
//		let dd = today.getDate();
//
//		if (dd < 10) dd = '0' + dd;
//		if (mm < 10) mm = '0' + mm;
//
//		const formattedToday = dd + '/' + mm + '/' + yyyy;
//		return formattedToday;
//	};


    typePhoneNumber.addEventListener('click', () => {
        typeWalletId.checked = false;
    });

    typeWalletId.addEventListener('click', () => {
        typePhoneNumber.checked = false;
    });

    button.addEventListener('click', async () => {
        let walletId = 0;

        if (typeWalletId.checked || typePhoneNumber.checked) {
            if (typePhoneNumber.checked) {
                const res = await fetch(GET_WALLET_ID_URL(input.value));
                const data = (await res.json()).data;

                walletId = data[0]?.walletId;
            } else if (typeWalletId.checked) {
                walletId = input.value;
            } else {
				const res = await fetch(GET_WALLET_ID_URL(input.value));
                const data = (await res.json()).data;

                walletId = data[0]?.walletId;
			}
			
			if(a !== null){
				clearInterval(a);
			}
			
            a = setInterval( () => {
				
				if(walletId < 1000000){
					getBalanceMaster(walletId)
				} else {
					getBalance(walletId)
				}
                getTransactionsHistory(walletId)
            }, 10000);
			
			
			
			if(walletId < 1000000){
				getBalanceMaster(walletId)
			} else {
				getBalance(walletId)
			}
            getTransactionsHistory(walletId)
        }

    });
}

run('section1');
run('section2');